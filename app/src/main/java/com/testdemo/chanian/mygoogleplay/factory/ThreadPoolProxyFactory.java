package com.testdemo.chanian.mygoogleplay.factory;

import com.testdemo.chanian.mygoogleplay.manage.ThreadPoolProxy;

/**
 * Created by ChanIan on 16/5/26.
 */
//线程池代理工厂
public class ThreadPoolProxyFactory {
    //普通线程池
    static ThreadPoolProxy mNomalThreadPoolProxy;
    //下载线程池
    static ThreadPoolProxy mDownloadThreadPoolProxy;
    //创建普通线程池
    public static ThreadPoolProxy getNomalThreadPoolProxy(){
        if(mNomalThreadPoolProxy==null) {
            synchronized (ThreadPoolProxyFactory.class){//线程安全
                if(mNomalThreadPoolProxy==null) {
                    mNomalThreadPoolProxy=new ThreadPoolProxy(5,7,2000);
                }
            }
        }
        return mNomalThreadPoolProxy;
    }
    //创建下载线程池
    public static ThreadPoolProxy getDownloadThreadPoolProxy(){
        if(mDownloadThreadPoolProxy==null) {
            synchronized (ThreadPoolProxyFactory.class){//线程安全
                if(mDownloadThreadPoolProxy==null) {
                    mDownloadThreadPoolProxy=new ThreadPoolProxy(8,10,2000);
                }
            }
        }
        return mDownloadThreadPoolProxy;
    }
}
