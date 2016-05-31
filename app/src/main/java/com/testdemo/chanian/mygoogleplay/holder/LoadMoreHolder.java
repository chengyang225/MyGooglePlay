package com.testdemo.chanian.mygoogleplay.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ChanIan on 16/5/27.
 */
//加载更多holder
public class LoadMoreHolder extends BaseHolder<Integer> {
    public static final int LOADING_MORE=0;
    public static final int LOADING_ERROR=1;
    public static final int LOADING_NONE=2;

    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;//正在加载中
    @Bind(R.id.item_loadmore_tv_retry)
    TextView mItemLoadmoreTvRetry;
    @Bind(R.id.item_loadmore_container_retry)//加载错误
    LinearLayout mItemLoadmoreContainerRetry;

    @Override
    public View initView() {
        //加载布局
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_load_more, null);
        //绑定子控件
        ButterKnife.bind(this,view);
        return view;
    }
    //控制显示视图
    @Override
    public void refreshView(Integer data) {
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);
        switch (data) {
            case  LOADING_MORE:
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case  LOADING_ERROR:
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
        }
    }
}
