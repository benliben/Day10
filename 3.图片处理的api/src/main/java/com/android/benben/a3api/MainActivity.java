package com.android.benben.a3api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * 旋转
 */

public class MainActivity extends AppCompatActivity {

    private float degrees;//图片旋转的角度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


             /*1.找到控件*/
        ImageView src = (ImageView) findViewById(R.id.main_src);
        final ImageView copy = (ImageView) findViewById(R.id.main_copy);

        /*2.把tomcat.png  转换成bitmap 然后显示到src上*/
        final Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tomcat);
        src.setImageBitmap(srcBitmap);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    degrees += 5;
                    SystemClock.sleep(1000);
                            /*3.拷贝原图*/
                     /*3.1 创建模板*/
                    final Bitmap copyBitmap = Bitmap.createBitmap(
                            srcBitmap.getWidth(),
                            srcBitmap.getHeight(),
                            srcBitmap.getConfig());
                    /*3.2想作画就需要一个画布   以copyBitmap为模板*/
                    Canvas canvas = new Canvas(copyBitmap);
                     /*3.3创建一个画笔*/
                    Paint paint = new Paint();
                    /*3.4开始作画   srcBitma：参考原图去画*/
                    Matrix matrix = new Matrix();
                    /*3.5对图片进行旋转*/
                    matrix.setRotate(degrees, srcBitmap.getWidth() / 2, srcBitmap.getHeight() / 2);
                    canvas.drawBitmap(srcBitmap, matrix, paint);

                    /*注意不能在子线程中更新ui*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*这个方法里面的逻辑一点在主线程中执行*/
                                    /*4.吧copyBitmap 显示到copy上*/
                            copy.setImageBitmap(copyBitmap);
                        }
                    });
                }
            }
        }).start();

    }
}
