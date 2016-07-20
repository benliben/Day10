package com.android.benben.a3api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by LiYuanxiong on 2016/7/19 17:09.
 * Desribe:
 */
public class ZoomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



             /*1.找到控件*/
        ImageView src = (ImageView) findViewById(R.id.main_src);
        final ImageView copy = (ImageView) findViewById(R.id.main_copy);
        /*2.把tomcat.png  转换成bitmap 然后显示到src上*/
        final Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tomcat);
        src.setImageBitmap(srcBitmap);
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
        /*3.5对图片进行缩放*/
        matrix.setScale(1.0f,-1.0f);
        /*3.6让图片进行移动*/
        matrix.postTranslate(0, srcBitmap.getHeight());
        canvas.drawBitmap(srcBitmap, matrix, paint);
        /*4.吧copyBitmap 显示到copy上*/
        copy.setImageBitmap(copyBitmap);


    }
}



