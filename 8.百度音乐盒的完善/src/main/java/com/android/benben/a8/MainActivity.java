package com.android.benben.a8;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {


    private Iservice iservice;
    private MyConn conn;
    private static SeekBar sb;

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            /*1.获取msg携带的数据*/
            Bundle data = msg.getData();
            /*获取当前的进度和总进度*/
            int duration = data.getInt("duration");
            int currentPosition = data.getInt("currentPosition");
            /*3.设置seekBar的最大进度和当期进度*/
            sb.setMax(duration);//进度条的最大值
            sb.setProgress(currentPosition);//设置当前进度

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        /*1.调用MusicService方法开启服务，保证服务在后台长期运行*/
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        /*2.调用bindService 目的是为了获取我们定义的中间人对象*/
        conn = new MyConn();
        /*链接MusicService服务，获取我们定义的中间人对象*/
        bindService(intent, conn, BIND_AUTO_CREATE);
        /*3.给seekBar设置监听*/
        sbChangeListener();


    }

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

            /*当停止拖动执行*/
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /*设置播放的位置*/
                iservice.callSeekToPosition(seekBar.getProgress());

            }
        });
    }

    private void initView() {
        sb = (SeekBar) findViewById(R.id.main_sb);
    }
    /*2.1获取中间人对象*/
    private class MyConn implements ServiceConnection {

        /*当链接成功时调用*/
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /*获取我们定义的中间人对象*/
            iservice = (Iservice) service;
        }

        /*当链接失败时调用*/
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    /*播放*/
    public void click1(View view) {
        iservice.callPlayMusic();
    }
    /*暂停*/
    public void click2(View view) {
        iservice.callPauseMusic();
    }
    /*继续*/
    public void click3(View view) {
        iservice.callRePlayMusic();
    }

    @Override
    protected void onDestroy() {
        /*在Activity销毁的时候取消绑定服务*/
        super.onDestroy();
    }
}
