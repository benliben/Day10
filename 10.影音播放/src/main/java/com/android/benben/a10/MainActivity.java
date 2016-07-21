package com.android.benben.a10;

import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private int currentPosition;//当前视频播放的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*找到控件*/
        final SurfaceView sv = (SurfaceView) findViewById(R.id.main_sv);
        final SurfaceHolder sh = sv.getHolder();
                /*添加一个CallBack*/
        sh.addCallback(new SurfaceHolder.Callback() {

            /*当surfaceView初始化了*/
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                        /*1.初始化MediaPlayer*/
                player = new MediaPlayer();
                try {
                             /*2.设置要播放的资源 path  可以是本地路径 也可以是网络路径*/
                    player.setDataSource("/sdcard/oppo.mp4");
                            /*2.1设置播放视频的内容  SurfaceHolder用来维护视频播放的内容*/
                    player.setDisplay(sh);
                             /*3.准备播放*/
                    player.prepare();
                            /*4.开始播放*/
                    player.start();
                            /*5.继续上传位置继续播*/
                    player.seekTo(currentPosition);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            /*当surfaceView发生改变的时候*/
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {

            }

            /*当surfaceView销毁的时候*/
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (player != null && player.isPlaying()) {
                            /*获取当前视频播放的位置*/
                    currentPosition = player.getCurrentPosition();
                    player.stop();
                    player.release();
                }
            }
        });
    }
}
