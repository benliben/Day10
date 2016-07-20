package com.android.benben.a2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class RotateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*1.找到控件*/
        ImageView src = (ImageView) findViewById(R.id.main_src);
        ImageView copy = (ImageView) findViewById(R.id.main_copy);

        /*2.把tomcat.png  转换成bitmap 然后显示到src上*/
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tomcat);
        src.setImageBitmap(srcBitmap);

        /*3.拷贝原图*/
        /*3.1 创建模板*/
        Bitmap copyBitmap = Bitmap.createBitmap(
                srcBitmap.getWidth(),
                srcBitmap.getHeight(),
                srcBitmap.getConfig());
        /*3.2想作画就需要一个画布   以copyBitmap为模板*/
        Canvas canvas = new Canvas(copyBitmap);
        /*3.3创建一个画笔*/
        Paint paint = new Paint();
        /*3.4开始作画   srcBitma：参考原图去画*/
        canvas.drawBitmap(srcBitmap, new Matrix(), paint);
        /*3.5修改原图   (setPixel 一次只能修改一个相熟点)*/
        for (int i = 0; i < 10; i++) {
            copyBitmap.setPixel(20+i,30+i, Color.RED);
        }
        /*4.吧copyBitmap 显示到copy上*/
        copy.setImageBitmap(copyBitmap);

    }
}
