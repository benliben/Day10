package com.android.benben.a8;

/**
 * Created by LiYuanxiong on 2016/7/20 16:14.
 * Desribe:
 */
public interface Iservice {
    void callPlayMusic();
    void callPauseMusic();
    void callRePlayMusic();

    void callSeekToPosition(int position);
}
