package com.android.benben.day10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "lyx";
    private ImageView iv;
    private int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.main_image);
        initSize();
    }

    /*1.获取手机的分辨率   获取windwoManager 实例*/
    private void initSize() {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        width = point.x;
        height = point.y;
    }

    /*点击按钮*/
    public void click(View view) {
        /*2.把dog.jpg转换成bitmap*/

        /*创建bitmap工厂配置参数*/
        BitmapFactory.Options options = new BitmapFactory.Options();
        /*返回一个null 没有bitmap   不去真正的解析位图  但是能返回图片的一些信息（宽和高）*/
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile("/storage/sdcard0/dog.jpg", options);
        /*3.获取图片的宽和高*/
        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;
        Log.i(TAG, "click: " + imgHeight + "     " + imgWidth);


        /*4.计算缩放比*/
        int scale = 1;//自定义缩放比
        int h = imgHeight / height;
        int w = imgWidth / width;
        if (w > 1 && h > 1) {
            if (h > w) {
                scale = h;
            } else {
                scale = w;
            }
        }

        Log.i(TAG, "click: " + scale);

        /*5.按照缩放比显示图片*/
        options.inSampleSize = scale;


        /*6.开始真正的解析位图*/
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile("/storage/sdcard0/dog.jpg", options);

        /*7把bitmap显示在imageview 上面*/
        iv.setImageBitmap(bitmap);


    }

}
