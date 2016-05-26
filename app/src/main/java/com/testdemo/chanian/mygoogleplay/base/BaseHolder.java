package com.testdemo.chanian.mygoogleplay.base;

import android.view.View;

/**
 * Created by ChanIan on 16/5/26.
 */
//所有holder的基类
public abstract class BaseHolder<ITEMBEANTYPE> {
    public View mView;//加载的布局
    private ITEMBEANTYPE mData;//需绑定的数据

    public BaseHolder() {
        mView=initView();
        mView.setTag(this);
    }
    //不知道子类详细布局,交给子类去实现
    public abstract View initView();

    public void setDataAndRefreshView(ITEMBEANTYPE data){
        this.mData=data;
        refreshView(data);
    }
    //不知道子类具体数据类型,让子类去实现
    protected abstract void refreshView(ITEMBEANTYPE data);
}
