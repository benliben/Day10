package com.android.benben.a8;

import android.app.Service;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LiYuanxiong on 2016/7/20 16:02.
 * Desribe:
 */
public class MusicService extends Service {
    private static final String TAG = "lyx";
    private MediaPlayer player;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    /*服务第一次开启的时候调用*/
    @Override
    public void onCreate() {
        /*1.初始化mediaPlayer*/
        player = new MediaPlayer();
        super.onCreate();
    }
    /*服务销毁的时候调用*/
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*设置播放音乐指定位置的方法*/
    public void seekToPosition(int position) {
        player.seekTo(position);
    }

    /*1.定义一个中间人对象*/
    private class MyBinder extends Binder implements Iservice {
        /*播放音乐*/
        @Override
        public void callPlayMusic() {
            playMusic();
        }

        /*暂停播放*/
        @Override
        public void callPauseMusic() {
            pauseMusic();
        }

        /*继续播放*/
        @Override
        public void callRePlayMusic() {
            rePlayMuseic();
        }

        /*调用设置指定位置播放的方法*/
        @Override
        public void callSeekToPosition(int position) {
            seekToPosition(position);
        }
    }



    /*更新进度条的方法*/
    private void updateSeekBr() {
        /*1.获取当前歌曲的总时长*/
        final int duration = player.getDuration();
        /*2.一秒钟 */
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {

                /*3.获取当前歌曲的进度*/
                int currentPosition = player.getCurrentPosition();

                /*4.创建Message对象*/
                Message msg = Message.obtain();
                /*5使用msg携带对个数据*/
                Bundle bundle = new Bundle();
                bundle.putInt("duration", duration);
                bundle.putInt("currentPosition", currentPosition);
                msg.setData(bundle);
                /*发送消息  MainActivity的handleMessage会执行*/
                MainActivity.handler.sendMessage(msg);
            }
        };
        /*300毫秒以后 每隔一秒获取一次当前的歌曲进度*/
        timer.schedule(task, 300, 1000);
        /*3.当歌曲播放完成的时候吧time 和task取消*/
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            /*当歌曲播放完成的监听*/
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i(TAG, "歌曲已经播放完了");
                timer.cancel();
                task.cancel();
            }
        });

    }

    public void playMusic() {
        Log.i(TAG, "音乐播放了");
        /*设置要播放的资源路径 path 可以是本地的也可以是网络的*/
        try {
            player.reset();
            player.setDataSource("/sdcard/xpg.mp3");
            /*3.准备播放*/
            player.prepare();
            /*4.播放*/
            player.start();
            /*5更新进度条*/
            updateSeekBr();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseMusic() {
        Log.i(TAG, "音乐暂停了");
        player.pause();
    }

    public void rePlayMuseic() {
        Log.i(TAG, "继续播放音乐");
        player.start();
    }
}
