package com.testdemo.chanian.mygoogleplay.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

/**
 * Created by ChanIan on 16/5/26.
 */
//
public class HomeHolder extends BaseHolder

{
    public View mView;//加载的布局
    private TextView tv1;
    private TextView tv2;
    @Override
    public View initView() {
        mView = LayoutInflater.from(UIUtils.getContext()).inflate(android.R.layout.simple_list_item_2, null);
        tv1 = (TextView) mView.findViewById(android.R.id.text1);
        tv2 = (TextView) mView.findViewById(android.R.id.text2);
        return mView;
    }

    @Override
    protected void refreshView(Object data) {
        tv1.setText(data + "   head");
        tv2.setText(data + "   foot");

    }
//
//    private String mData;//需要绑定的数据
//
//    public HomeHolder() {
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


}

