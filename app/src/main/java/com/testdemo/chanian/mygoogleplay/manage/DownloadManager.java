package com.testdemo.chanian.mygoogleplay.manage;

import android.util.Log;

import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.factory.ThreadPoolProxyFactory;
import com.testdemo.chanian.mygoogleplay.utils.CommonUtils;
import com.testdemo.chanian.mygoogleplay.utils.FileUtils;
import com.testdemo.chanian.mygoogleplay.utils.HttpUtils;
import com.testdemo.chanian.mygoogleplay.utils.IOUtils;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ChanIan on 16/5/31.
 */
//下载管理
public class DownloadManager {


    private DownloadManager() {
    }

    private static DownloadManager instance;

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public static final int STATE_UNDOWNLOAD = 0;
    public static final int STATE_DOWNLOADING = 1;
    public static final int STATE_PAUSEDOWNLOAD = 2;
    public static final int STATE_WAITDOWNLOAD = 3;
    public static final int STATE_DOWNLOADFAILED = 4;
    public static final int STATE_DOWNLOADED = 5;
    public static final int STATE_INSTALLED = 6;

    public Map<String, DownloadInfo> mDownloadInfoMap = new HashMap<>();

    /**
     * @param downloadInfo 下载的信息
     * @des 触发下载
     * @called 用户点击下载的时候
     */
    public void download(DownloadInfo downloadInfo) {
        //下载前将下载信息保存
        mDownloadInfoMap.put(downloadInfo.packName, downloadInfo);

        ///////////////////////////////////////////////////////////////////////////
        // 状态:未开始
        ///////////////////////////////////////////////////////////////////////////
        downloadInfo.curState = STATE_UNDOWNLOAD;
        //状态改变,通知观察者
        notifyObsever(downloadInfo);

        ///////////////////////////////////////////////////////////////////////////
        // 状态:等待下载
        ///////////////////////////////////////////////////////////////////////////
        downloadInfo.curState = STATE_WAITDOWNLOAD;
        //状态改变,通知观察者
        notifyObsever(downloadInfo);

        //开始下载
        DownloadTask task = new DownloadTask(downloadInfo);
        downloadInfo.downloadTask = task;
        ThreadPoolProxyFactory.getDownloadThreadPoolProxy().execute(task);
    }

    public DownloadInfo getDownloadInfo(ItemInfoBean itemInfoBean) {
        //常规赋值
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.maxSize = itemInfoBean.size;
        downloadInfo.name = itemInfoBean.downloadUrl;
        String dir = FileUtils.getDir("apk");//文件夹路径
        String fileName = itemInfoBean.packageName + ".apk";
        File file = new File(dir, fileName);
        String path = file.getAbsolutePath();
        downloadInfo.savedPath = path;
        downloadInfo.packName = itemInfoBean.packageName;

        //重要赋值
        ///////////////////////////////////////////////////////////////////////////
        // 状态:已安装 STATE_INSTALLED
        ///////////////////////////////////////////////////////////////////////////

        if (CommonUtils.isInstalled(UIUtils.getContext(), downloadInfo.packName)) {
            downloadInfo.curState = STATE_INSTALLED;
            //状态改变,通知观察者
            notifyObsever(downloadInfo);
            return downloadInfo;
        }

        ///////////////////////////////////////////////////////////////////////////
        // 状态:下载完成 STATE_DOWNLOADED
        ///////////////////////////////////////////////////////////////////////////
        File apkFile = new File(downloadInfo.savedPath);
        if (apkFile.exists() && apkFile.length() == downloadInfo.maxSize) {
            downloadInfo.curState = STATE_DOWNLOADED;
            //状态改变,通知观察者
            notifyObsever(downloadInfo);
            return downloadInfo;
        }

        ///////////////////////////////////////////////////////////////////////////
        // 状态:下载中,暂停下载,下载失败,等待下载(用户肯定点击了下载)
        ///////////////////////////////////////////////////////////////////////////
        if (mDownloadInfoMap.containsKey(itemInfoBean.packageName)) {
            downloadInfo = mDownloadInfoMap.get(itemInfoBean.packageName);
            return downloadInfo;
        }

        ///////////////////////////////////////////////////////////////////////////
        // 状态:未下载 STATE_UNDOWNLOAD
        ///////////////////////////////////////////////////////////////////////////
        downloadInfo.curState = STATE_UNDOWNLOAD;
        //状态改变,通知观察者
        notifyObsever(downloadInfo);
        return downloadInfo;


    }

    //暂停下载
    public void pauseDownload(DownloadInfo downloadInfo) {
        ///////////////////////////////////////////////////////////////////////////
        // 状态:暂停下载 STATE_PAUSEDOWNLOAD
        ///////////////////////////////////////////////////////////////////////////
        Log.v("ian", "STATE_PAUSEDOWNLOAD:DownloadManager");
        downloadInfo.curState = STATE_PAUSEDOWNLOAD;
        notifyObsever(downloadInfo);
    }

    //取消下载
    public void cancelDownload(DownloadInfo downloadInfo) {
        ///////////////////////////////////////////////////////////////////////////
        // 状态:暂停下载 STATE_PAUSEDOWNLOAD
        ///////////////////////////////////////////////////////////////////////////
        downloadInfo.curState = STATE_UNDOWNLOAD;
        notifyObsever(downloadInfo);
        ThreadPoolProxyFactory.getDownloadThreadPoolProxy().remove(downloadInfo.downloadTask);
    }


    private class DownloadTask implements Runnable {
        private DownloadInfo mDownloadInfo;

        public DownloadTask(DownloadInfo downloadInfo) {
            mDownloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            ///////////////////////////////////////////////////////////////////////////
            // 状态:开始下载 STATE_DOWNLOADING
            ///////////////////////////////////////////////////////////////////////////
            mDownloadInfo.curState = STATE_DOWNLOADING;
            //状态改变,通知观察者
            notifyObsever(mDownloadInfo);


            long initRange = 0;
            File saveApk = new File(mDownloadInfo.savedPath);
            if (saveApk.exists()) {//断点下载的文件
                initRange = saveApk.length();
            }
            //处理上一次的UI进度
            mDownloadInfo.progress = initRange;

            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                //开始异步下载
                OkHttpClient client = new OkHttpClient();
                String url = Constants.HttpUrl.DOWNLOADURL;
                //拼接参数
                Map<String, Object> params = new HashMap<>();
                params.put("name", mDownloadInfo.name);
                params.put("range", String.valueOf(initRange));
                //准备url
                String paramsByMap = HttpUtils.getUrlParamsByMap(params);
                url += paramsByMap;
                //准备request
                Request request = new Request.Builder().get().url(url).build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    boolean isPause = false;
                    inputStream = response.body().byteStream();
                    //写进内存卡
                    outputStream = new FileOutputStream(saveApk, true);
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = inputStream.read(buffer)) != -1) {

                        if (mDownloadInfo.curState == STATE_PAUSEDOWNLOAD) {
                            isPause = true;
                            break;
                        }

                        ///////////////////////////////////////////////////////////////////////////
                        // 状态:下载中
                        ///////////////////////////////////////////////////////////////////////////
                        mDownloadInfo.curState = STATE_DOWNLOADING;
                        //给当前进度赋值
                        mDownloadInfo.progress += len;
                        //状态改变,通知观察者
                        notifyObsever(mDownloadInfo);


                        outputStream.write(buffer, 0, len);
                        if (saveApk.length() == mDownloadInfo.maxSize) {//下载完毕跳出循环
                            break;
                        }
                    }
                    if (isPause) {//暂停,什么也不做

                    } else {

                        ///////////////////////////////////////////////////////////////////////////
                        // 状态:结束下载 STATE_DOWNLOADED
                        ///////////////////////////////////////////////////////////////////////////
                        mDownloadInfo.curState = STATE_DOWNLOADED;
                        //状态改变,通知观察者
                        notifyObsever(mDownloadInfo);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                ///////////////////////////////////////////////////////////////////////////
                // 状态:下载失败 STATE_DOWNLOADFAILED
                ///////////////////////////////////////////////////////////////////////////
                mDownloadInfo.curState = STATE_DOWNLOADFAILED;
                //状态改变,通知观察者
                notifyObsever(mDownloadInfo);

            } finally {
                IOUtils.close(outputStream);
                IOUtils.close(inputStream);
            }


        }


    }
    ///////////////////////////////////////////////////////////////////////////
    // 自定义观察者模式检测下载状态改变
    ///////////////////////////////////////////////////////////////////////////

    //第一步:定义接口和抽象方法
    public interface DownloadObserver {
        void onDownloadInfoChanged(DownloadInfo downloadInfo);
    }

    //第二步:定义封装观察者实现类接口的集合
    public List<DownloadObserver> mObservers = new ArrayList<>();

    //第三步:添加观察者
    public synchronized void addObserver(DownloadObserver observer) {
        if (observer == null) {
            throw new RuntimeException("观察者为空");
        }
        if (!mObservers.contains(observer)) {

            mObservers.add(observer);
        }
    }

    //第四步:移除观察者
    public synchronized void removeObsever(DownloadObserver observer) {
        mObservers.remove(observer);
    }


    //第五步:实现观察者回调方法
    public void notifyObsever(DownloadInfo downloadInfo) {
        for (DownloadObserver observer : mObservers) {
            observer.onDownloadInfoChanged(downloadInfo);
        }
    }

}
