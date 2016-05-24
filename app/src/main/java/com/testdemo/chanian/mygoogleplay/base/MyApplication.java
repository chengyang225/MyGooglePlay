package com.testdemo.chanian.mygoogleplay.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * Created by ChanIan on 16/5/24.
 */
public class MyApplication extends Application {


    private static Handler mHandler;
    private static Context mContext;
    private static int mMainThreadId;
    //获取唯一handler
    public static Handler getmHandler() {
        return mHandler;
    }
    //获取上下文
    public static Context getmContext() {
        return mContext;
    }
    //获取主线程id
    public static int getmMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mContext = getApplicationContext();
        mMainThreadId = Process.myTid();
    }
}
