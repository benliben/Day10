package com.android.benben.a5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.Format;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "lyx";
    private int startX, startY;
    private Paint paint;
    private Canvas canvas;
    private ImageView iv;
    private Bitmap copyBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.main_iv);

        /*1.把背景图片转换成bitmap*/
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        /*2.1创建模板*/
        copyBitmap = Bitmap.createBitmap(
                srcBitmap.getWidth(),
                srcBitmap.getHeight(),
                srcBitmap.getConfig());
        /*2.2以copyBitmap为模板 创建一个画布*/
        canvas = new Canvas(copyBitmap);
        /*2.3创建一个画笔*/
        paint = new Paint();
        /*2.4开始作画*/
        canvas.drawBitmap(srcBitmap, new Matrix(), paint);

        /*3.把copyBitmap显示在iv上面*/
        iv.setImageBitmap(copyBitmap);


        /*4.给iv设置一个触摸事件*/
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*5.获取手指触摸的类型*/
                int action = event.getAction();
                /*6.具体判断一下是什么类型的事件*/
                switch (action) {
                    case MotionEvent.ACTION_DOWN://按下
                        /*7.获取手指按下的坐标*/
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE://滑动
                        /*8.获取停止的坐标*/
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        /*9.开始划线*/
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        /*9.1更新一下起点坐标*/
                        startX = stopX;
                        startY = stopY;
                        /*10.更新UI*/
                        iv.setImageBitmap(copyBitmap);
                        break;
                    case MotionEvent.ACTION_UP://抬起

                        break;
                }
                return true;//当返回为true时 移动和抬起的方法才会执行  当为false时则不执行
            }
        });
    }

    /*点击按钮让颜色变为红色*/
    public void click1(View view) {
        paint.setColor(Color.RED);
    }

    /*点击按钮让画笔变粗*/
    public void click2(View view) {
        paint.setStrokeWidth(15);
    }

    /*点击按钮保存图片*/
    public void click3(View view) {
        /**
         * format  保存图片的格式
         * quality  保存照片的质量  0——100
         * stream  保存的位置
         */
        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath(), "dazuo.png");
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            copyBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            /*发送一条sd卡挂上来的广播信息 欺骗广播系统图库应用s*/
            Intent intent = new Intent();
            /*设置action*/
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            /*设置data*/
            intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            /*发送无序广播*/
            sendBroadcast(intent);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
