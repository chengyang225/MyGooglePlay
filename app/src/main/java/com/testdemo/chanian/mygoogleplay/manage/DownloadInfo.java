package com.testdemo.chanian.mygoogleplay.manage;

import java.io.File;

/**
 * Created by ChanIan on 16/6/6.
 */
//
public class DownloadInfo {

    public String name;
    public String packName;
    public String savedPath;
    public long maxSize;
    public int curState=DownloadManager.STATE_UNDOWNLOAD;
    public long progress;
    public Runnable downloadTask;

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "name='" + name + '\'' +
                ", packName='" + packName + '\'' +
                ", savedPath='" + savedPath + '\'' +
                ", maxSize=" + maxSize +
                ", curState=" + curState +
                ", progress=" + progress +
                '}';
    }
}
