package com.testdemo.chanian.mygoogleplay.holder;

import android.view.View;
import android.widget.Button;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;
import com.testdemo.chanian.mygoogleplay.views.ProgressButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChanIan on 16/5/30.
 */
public class DetailButtonHolder extends BaseHolder<ItemInfoBean> {

    @Bind(R.id.app_detail_download_btn_favo)
    Button mAppDetailDownloadBtnFavo;
    @Bind(R.id.app_detail_download_btn_share)
    Button mAppDetailDownloadBtnShare;
    @Bind(R.id.app_detail_download_btn_download)
    ProgressButton mAppDetailDownloadBtnDownload;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_bottom, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void refreshView(ItemInfoBean data) {

    }

    @OnClick(R.id.app_detail_download_btn_download)
    public void onClick() {

    }
}
