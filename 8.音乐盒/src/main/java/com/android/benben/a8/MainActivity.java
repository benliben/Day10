package com.android.benben.a8;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private myConn conn;
    private IService iservice;
    private static SeekBar sb;

    /*用于接收MusicService传递过来的数据*/
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            /*1.获取msg携带的数据*/
            Bundle data = msg.getData();
            /*2.获取当前的进度和总进度*/
            int duration = data.getInt("duration");
            int currentPosition = data.getInt("currentPosition");
            Log.i("lyx", "handleMessage: " + duration +"*"+ currentPosition);
            /*3.设置seekBar最大值和当前值*/
            sb.setMax(duration);//设置最大值
            sb.setProgress(currentPosition);//设置当前的值

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initService();
        sbChangeListener();

    }

    private void initService() {
        /*1.调用MusicService方法来开启服务，保证服务能够长期在后台运行*/
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        /*2.调用bindService 目的是为了获取我们定义的中间人对象*/
        conn = new myConn();
        /*2.2连接到到MusicService服务，获取定义的中间人对象*/
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    /*对seekBar的监听*/
    private void sbChangeListener() {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /*拖动过程中*/
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            /*开始拖动*/
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /*停止拖动*/
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /*设置播放的位置*/
                iservice.callSeekToPosition(seekBar.getProgress());
            }
        });
    }


    /*2.1获取中间人对象*/
    private class myConn implements ServiceConnection {
        /*当连接成功的时候调用*/
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /*获取定义的中间人对象*/
            iservice = (IService) service;
        }

        /*当连接失败的时候调用*/
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    /*播放按钮*/
    public void click1(View view) {
        iservice.callPlayMusic();
    }
    /*暂停按钮*/
    public void click2(View view) {
        iservice.callPauseMusic();
    }
    /*继续播放按钮*/
    public void click3(View view) {
        iservice.callRePlayMusic();
    }
    private void initView() {
        sb = (SeekBar) findViewById(R.id.main_sb);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
