package com.testdemo.chanian.mygoogleplay.config;

import com.testdemo.chanian.mygoogleplay.utils.LogUtils;

/**
 * Created by ChanIan on 16/5/24.
 */
public class Constants {
    public static final int PAGESIZE = 20;
    public static final long PERIOD = 10 * 60 * 60 * 1000;//本地缓存周期
    public static int DEBUGLEVEL= LogUtils.LEVEL_ALL;
    public static class HttpUrl{
    public static final String BASEURL ="http://192.168.30.252:8080/GooglePlayServer/" ;
    public static final String IMAGEURL =BASEURL+"image?name=" ;

    }
}
