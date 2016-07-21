package com.android.benben.a8;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LiYuanxiong on 2016/7/21 9:42.
 * Desribe:创建音乐播放器的服务
 */
public class MusicService extends Service {
    private static final String TAG = "lyx";
    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    /*第一次创建的时候调用*/
    @Override
    public void onCreate() {
        /*1.初始化mediaPlayer*/
        player = new MediaPlayer();
        super.onCreate();
    }

    /*销毁的时候调用*/
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /*设置播放音乐指定位置的方法*/
    public void seekToPosition(int position) {
        player.seekTo(position);
    }


    /*定义一个中间人对象*/
    private class MyBinder extends Binder implements IService{
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
            rePlayMusic();
        }

        @Override
        public void callSeekToPosition(int position) {
            seekToPosition(position);
        }
    }

    /*功能*/
    public void playMusic() {
        Log.i(TAG, "开始播放音乐");
        try {
            /*2.设置要播放的路径 path 可以是本地的也可以是网络的*/
            player.reset();//在播放之前先复位
            player.setDataSource("/sdcard/xpg.mp3");
            /*3.准备播放*/
            player.prepare();
            /*4.播放*/
            player.start();
            /*5.更新进度条*/
            updateSeekBar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*更新进度*/
    private void updateSeekBar() {
        /*1.获取当前歌曲的总时长*/
        final int duration = player.getDuration();
        /*2.计时器*/
        final Timer timer = new Timer();
        final TimerTask task=new TimerTask() {
            @Override
            public void run() {
                /*3.获取当前歌曲的进度*/
                int currentPosition = player.getCurrentPosition();

                /*由于要在主界面对seekBar进行更新不可能在服务里面实现
                所以只能把数据传递到MainActivity*/
                /*4.创建Message对象*/
                Message msg = Message.obtain();
                /*5.使用msg携带多个数据*/
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
        /*当歌曲播放完成的时候吧tim和task取消*/
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i(TAG, "歌曲播放完了");
                timer.cancel();
                task.cancel();
            }
        });
    }

    public void pauseMusic() {
        Log.i(TAG, "暂停音乐的播放");
        player.pause();
    }

    public void rePlayMusic() {
        Log.i(TAG, "继续播放音乐");
        player.start();
    }

}
