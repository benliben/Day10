package com.android.benben.a11videoview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView vv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vv = (VideoView) findViewById(R.id.main_vv);


        vv.setVideoPath("/sdcard/oppo.mp4");
        vv.start();
    }
}
