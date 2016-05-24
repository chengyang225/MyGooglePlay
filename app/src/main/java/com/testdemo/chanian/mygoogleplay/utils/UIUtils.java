package com.testdemo.chanian.mygoogleplay.utils;

import android.content.Context;
import android.content.res.Resources;

import com.testdemo.chanian.mygoogleplay.base.MyApplication;

/**
 * Created by ChanIan on 16/5/24.
 */
public class UIUtils {
    //得到上下文
    public static Context getContext(){
        return MyApplication.getmContext();
    }
    //得到Resources
    public static Resources getResources(){
        return getContext().getResources();
    }
    //得到字符串
    public static String getString(int resId){
        return getResources().getString(resId);
    }
    //得到字符数组
    public static String[] getStringArray(int resId){
        return getResources().getStringArray(resId);
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }
}
