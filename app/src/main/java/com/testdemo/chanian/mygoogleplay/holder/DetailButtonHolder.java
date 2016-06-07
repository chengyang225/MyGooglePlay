package com.testdemo.chanian.mygoogleplay.holder;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.manage.DownloadInfo;
import com.testdemo.chanian.mygoogleplay.manage.DownloadManager;
import com.testdemo.chanian.mygoogleplay.utils.CommonUtils;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;
import com.testdemo.chanian.mygoogleplay.views.ProgressButton;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChanIan on 16/5/30.
 */
public class DetailButtonHolder extends BaseHolder<ItemInfoBean> implements DownloadManager.DownloadObserver {

    @Bind(R.id.app_detail_download_btn_favo)
    Button mAppDetailDownloadBtnFavo;
    @Bind(R.id.app_detail_download_btn_share)
    Button mAppDetailDownloadBtnShare;
    @Bind(R.id.app_detail_download_btn_download)
    ProgressButton mAppDetailDownloadBtnDownload;
    private ItemInfoBean mItemInfoBean;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_bottom, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void refreshView(ItemInfoBean data) {
        mItemInfoBean = data;
        //根据不同状态给用户提示(修改按钮显示UI)
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(data);
        refereshUI(downloadInfo);
    }

    //刷新UI
    private void refereshUI(DownloadInfo downloadInfo) {
        int curState = downloadInfo.curState;
        mAppDetailDownloadBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
        switch (curState) {
            case DownloadManager.STATE_UNDOWNLOAD:

                mAppDetailDownloadBtnDownload.setText("下载");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mAppDetailDownloadBtnDownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
                //开启进度条显示
                mAppDetailDownloadBtnDownload.setIsProgressEnable(true);
                int progress = (int) (downloadInfo.progress * 1.0 / downloadInfo.maxSize * 100 + .5f);
                mAppDetailDownloadBtnDownload.setText(progress + "%");
                mAppDetailDownloadBtnDownload.setMax(downloadInfo.maxSize);
                mAppDetailDownloadBtnDownload.setProgress(downloadInfo.progress);
                break;
            case DownloadManager.STATE_PAUSEDOWNLOAD:

                mAppDetailDownloadBtnDownload.setText("继续下载");
                break;
            case DownloadManager.STATE_DOWNLOADFAILED:
                mAppDetailDownloadBtnDownload.setText("重试");
                break;
            case DownloadManager.STATE_INSTALLED:
                mAppDetailDownloadBtnDownload.setText("打开");
                break;
            case DownloadManager.STATE_WAITDOWNLOAD:
                mAppDetailDownloadBtnDownload.setText("等待中....");
                break;
            case DownloadManager.STATE_DOWNLOADED:
                mAppDetailDownloadBtnDownload.setIsProgressEnable(false);
                mAppDetailDownloadBtnDownload.setText("安装");
                break;
        }
    }

    @OnClick(R.id.app_detail_download_btn_download)
    public void onClick() {
        //根据不同的状态触发不同操作
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(mItemInfoBean);
        int curState = downloadInfo.curState;
        switch (curState) {
            case DownloadManager.STATE_UNDOWNLOAD:
                //开始下载
                doDownload(downloadInfo);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                //暂停下载
                pauseDownload(downloadInfo);
                break;
            case DownloadManager.STATE_PAUSEDOWNLOAD:
                //继续下载
                doDownload(downloadInfo);
                break;
            case DownloadManager.STATE_DOWNLOADFAILED:
                //重新下载
                doDownload(downloadInfo);
                break;
            case DownloadManager.STATE_WAITDOWNLOAD:
                //取消下载
                cancelDownload(downloadInfo);
                break;
            case DownloadManager.STATE_DOWNLOADED:
                //安装应用
                installApp(downloadInfo);
                break;
            case DownloadManager.STATE_INSTALLED:
                //打开应用
                openApp(downloadInfo);
                break;

        }

    }

    //打开应用
    private void openApp(DownloadInfo downloadInfo) {

        CommonUtils.openApp(UIUtils.getContext(), downloadInfo.packName);
    }

    //安装应用
    private void installApp(DownloadInfo downloadInfo) {
        File file = new File(downloadInfo.savedPath);
        CommonUtils.installApp(UIUtils.getContext(), file);
    }

    //取消下载
    private void cancelDownload(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().cancelDownload(downloadInfo);
    }

    //继续下载
    private void doDownload(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().download(downloadInfo);
    }

    //暂停下载
    private void pauseDownload(DownloadInfo downloadInfo) {
        DownloadManager.getInstance().pauseDownload(downloadInfo);
    }

    //    //开始下载
    //    private void download(DownloadInfo downloadInfo) {
    ////        downloadInfo.maxSize = mItemInfoBean.size;
    ////        downloadInfo.name = mItemInfoBean.downloadUrl;
    ////        String dir = FileUtils.getDir("apk");//文件夹路径
    ////        String fileName = mItemInfoBean.packageName + ".apk";
    ////        File file = new File(dir, fileName);
    ////        String path = file.getAbsolutePath();
    ////        downloadInfo.savedPath = path;
    ////        downloadInfo.packName = mItemInfoBean.packageName;
    //        DownloadManager.getInstance().download(downloadInfo);
    //    }

    @Override
    public void onDownloadInfoChanged(final DownloadInfo downloadInfo) {
        //过滤传递过来的downloadinfo信息
        if (!downloadInfo.packName.equals(mItemInfoBean.packageName)) {
            return;
        }
        //回到主线程更新UI
        UIUtils.postTaskSafely(new Runnable() {
            @Override
            public void run() {
                refereshUI(downloadInfo);
            }
        });
    }
}
