package com.android.benben.a7;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*1.初始化mediaplayer*/
        MediaPlayer mediaPlayer = new MediaPlayer();
        /*2.设置要播放的资源 path，可以是本地路径也可以是网路路径*/
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "xpg.mp3");
            String uri = file+"";
            mediaPlayer.setDataSource(uri);
            Log.i("lyx", "拿到路径");

            /*3.准备播放*/
            mediaPlayer.prepare();
            Log.i("lyx", "准备播放");
            /*4.开始播放*/
            mediaPlayer.start();
            Log.i("lyx", "播放");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
