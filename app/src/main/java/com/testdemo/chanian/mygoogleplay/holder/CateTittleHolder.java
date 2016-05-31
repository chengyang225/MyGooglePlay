package com.testdemo.chanian.mygoogleplay.holder;

import android.view.View;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.bean.CateInfoBean;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

/**
 * Created by ChanIan on 16/5/29.
 */
public class CateTittleHolder extends BaseHolder<CateInfoBean> {

    private TextView mTv;

    @Override
    public View initView() {
        mTv = new TextView(UIUtils.getContext());
        return mTv;
    }

    @Override
    protected void refreshView(CateInfoBean data) {
        mTv.setText(data.title);
    }

}
