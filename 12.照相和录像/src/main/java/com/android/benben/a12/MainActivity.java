package com.android.benben.a12;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*点击照相*/
    public void click1(View view) {

        /*创建意图*/
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        /*存放图片的路径*/
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "haha.png");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        /*开启一个Activity 并获取结果*/
        startActivityForResult(intent, 1);

    }


    /*当开启的页面关闭的时候*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("lyx", "hah");
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*点击录像*/
    public void click2(View view) {
             /*创建意图*/
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        /*存放图片的路径*/
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "haha.3gp");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        /*开启一个Activity 并获取结果*/
        startActivityForResult(intent, 2);

    }
}
