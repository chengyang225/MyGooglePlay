package com.testdemo.chanian.mygoogleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Process;

import com.testdemo.chanian.mygoogleplay.base.MyApplication;

/**
 * Created by ChanIan on 16/5/24.
 */
public class UIUtils {
    //得到上下文
    public static Context getContext() {
        return MyApplication.getmContext();
    }

    //得到handler
    public static Handler getHandler() {
        return MyApplication.getmHandler();
    }

    //得到Resources
    public static Resources getResources() {
        return getContext().getResources();
    }

    //得到字符串
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    //得到字符数组
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }
    //得到包名
    public static String getPackageName() {
        return getContext().getPackageName();
    }
    //安全处理刷新等问题
    public static void postTaskSafely(Runnable task){
        //得到当前线程id
        int currentId = Process.myTid();
        //得到主线程id
        int mainThreadId = MyApplication.getmMainThreadId();
        if(currentId==mainThreadId) {//当前线程为主线程
            task.run();
        }else {//当前线程为子线程
            Handler handler = getHandler();
            handler.post(task);
        }
    }
}
