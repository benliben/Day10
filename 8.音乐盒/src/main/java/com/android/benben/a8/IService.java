package com.android.benben.a8;

/**
 * Created by LiYuanxiong on 2016/7/21 10:08.
 * Desribe:定义接口 对外暴露方法
 */
public interface IService {
    void callPlayMusic();
    void callPauseMusic();
    void callRePlayMusic();

    void callSeekToPosition(int position);
}
