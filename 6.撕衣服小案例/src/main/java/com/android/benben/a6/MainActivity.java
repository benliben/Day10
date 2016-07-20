package com.android.benben.a6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*1.找到iv 显示我们操作的图片*/
        iv = (ImageView) findViewById(R.id.iv);
        /*2.把我们要操作的图片转换成bitmap*/
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pre19);
        /*3.创建原图的副本*/
        /*3.1创建模板*/
        final Bitmap copyBitmap = Bitmap.createBitmap(
                srcBitmap.getWidth(),
                srcBitmap.getHeight(),
                srcBitmap.getConfig());
        /*3.2以copyBitmap bitmap为模板创建一个画布*/
        Canvas canvas = new Canvas(copyBitmap);
        /*3.3创建画笔*/
        Paint paint = new Paint();
        /*3.4开始作画*/
        canvas.drawBitmap(srcBitmap, new Matrix(), paint);
        /*4.把copyBitmap显示出来*/
        iv.setImageBitmap(copyBitmap);


        /*5.给iv设置一个触摸事件*/
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    /*移动事件*/
                    case MotionEvent.ACTION_MOVE:
                        for (int i = -4; i < 4; i++) {//改变x
                            for (int j = -4; j < 4; j++) {//改变y
                                /*划圆*/
                                if (Math.sqrt(i * i + j * j) < 4) {
                                    try {
                                        /*如果不设置外面的两个for循环和if语句一次修改一个像素*/
                                        copyBitmap.setPixel((int) event.getX()+i,
                                                (int) event.getY()+j,
                                                Color.TRANSPARENT);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                        /*更新UI*/
                        iv.setImageBitmap(copyBitmap);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });


    }
}
