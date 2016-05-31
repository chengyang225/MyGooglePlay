package com.testdemo.chanian.mygoogleplay.manage;

/**
 * Created by ChanIan on 16/5/31.
 */
//下载管理
public class DownloadManager {
    private DownloadManager(){}
    private static DownloadManager instance;
    public static DownloadManager getInstance(){
        if(instance==null) {
            synchronized (DownloadManager.class){
                if(instance==null) {
                    instance=new DownloadManager();
                }
            }
        }
        return instance;
    }
}
