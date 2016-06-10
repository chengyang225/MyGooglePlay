package com.testdemo.chanian.mygoogleplay.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.manage.DownloadInfo;
import com.testdemo.chanian.mygoogleplay.manage.DownloadManager;
import com.testdemo.chanian.mygoogleplay.utils.CommonUtils;
import com.testdemo.chanian.mygoogleplay.utils.StringUtils;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;
import com.testdemo.chanian.mygoogleplay.views.ProgressView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by ChanIan on 16/5/26.
 */
//
public class ItemHolder extends BaseHolder<ItemInfoBean> implements DownloadManager.DownloadObserver

{
    @Bind(R.id.item_appinfo_iv_icon)
    ImageView mItemAppinfoIvIcon;
    @Bind(R.id.item_appinfo_tv_title)
    TextView mItemAppinfoTvTitle;
    @Bind(R.id.item_appinfo_rb_stars)
    RatingBar mItemAppinfoRbStars;
    @Bind(R.id.item_appinfo_tv_size)
    TextView mItemAppinfoTvSize;
    @Bind(R.id.item_appinfo_tv_des)
    TextView mItemAppinfoTvDes;
    @Bind(R.id.pv_my)
    ProgressView mProgressView;

    private ItemInfoBean mItemInfoBean;
    @Override
    public View initView() {
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_app_info, null);
        //与子对象绑定
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void refreshView(ItemInfoBean data) {
        mProgressView.setProgress(0);
        mItemInfoBean = data;

        mItemAppinfoRbStars.setRating(data.stars);//评级
        mItemAppinfoTvDes.setText(data.des);//描述
        mItemAppinfoTvSize.setText(StringUtils.formatFileSize(data.size));
        mItemAppinfoTvTitle.setText(data.name);//应用名

        //使用
        Picasso.with(UIUtils.getContext()).load(Constants.HttpUrl.IMAGEURL+data.iconUrl).into(mItemAppinfoIvIcon);


        //根据不同状态给用户提示
        DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(mItemInfoBean);
        refreshProgressUI(downloadInfo);
    }
    //刷新下载控件UI
    private void refreshProgressUI(DownloadInfo downloadInfo) {
        int curState = downloadInfo.curState;
        switch (curState) {
            case DownloadManager.STATE_UNDOWNLOAD:
                mProgressView.setIv(R.drawable.ic_download);
                mProgressView.setTv("下载");
                break;
            case DownloadManager.STATE_WAITDOWNLOAD:
                mProgressView.setIv(R.drawable.ic_pause);
                mProgressView.setTv("等待中....");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mProgressView.setIv(R.drawable.ic_pause);
                //开启进度条显示
                mProgressView.setEnable(true);
                int progress = (int) (downloadInfo.progress * 1.0 / downloadInfo.maxSize * 100 + .5f);
                mProgressView.setTv(progress + "%");
                mProgressView.setMax(downloadInfo.maxSize);
                mProgressView.setProgress(downloadInfo.progress);
                break;
            case DownloadManager.STATE_PAUSEDOWNLOAD:
                mProgressView.setIv(R.drawable.ic_resume);
                mProgressView.setTv("继续下载");
                break;
            case DownloadManager.STATE_DOWNLOADFAILED:
                mProgressView.setIv(R.drawable.ic_redownload);
                mProgressView.setTv("重试");
                break;
            case DownloadManager.STATE_INSTALLED:
                mProgressView.setIv(R.drawable.ic_install);
                mProgressView.setTv("已安装");
                break;

            case DownloadManager.STATE_DOWNLOADED:
                mProgressView.setEnable(false);
                mProgressView.setIv(R.drawable.ic_install);
                mProgressView.setTv("安装");
                break;
        }
    }

    @OnClick(R.id.pv_my)
    public void downloadClick(View view){
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
                refreshProgressUI(downloadInfo);
            }
        });
    }
}
//
//    private String mData;//需要绑定的数据
//
//    public ItemHolder() {
//        mView.setTag(this);//将holder本身作为tag传进去
//    }
//    //传递数据
//    public void setDataAndRefreshView(String data){
//        this.mData=data;
//        refreshView(data);
//    }
//
//    private void refreshView(String data) {
//    }




