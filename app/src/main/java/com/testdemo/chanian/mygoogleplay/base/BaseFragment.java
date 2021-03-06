package com.testdemo.chanian.mygoogleplay.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by ChanIan on 16/5/25.
 */
public abstract class BaseFragment extends Fragment {

    public LoadingPage mLoadingPage;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        //加载数据只能让子类实现
        //显示视图只能让子类实现
        //避免mLoadingPage重复创建
        if (mLoadingPage == null) {
            mLoadingPage = new LoadingPage(getContext()) {
                @Override
                protected LoadDataState initData() {
                    //加载数据只能让子类实现
                    return BaseFragment.this.initData();
                }

                @Override
                protected View initSuccessView() {
                    //显示视图只能让子类实现
                    return BaseFragment.this.initSuccessView();
                }
            };
        }
        //加载数据
        mLoadingPage.triggleData();
        return mLoadingPage;
    }

    //每个子类显示view不一样,让子类自己实现去
    protected abstract View initSuccessView();

    //每个子类初始化数据不一样,让子类自己实现去
    protected abstract LoadingPage.LoadDataState initData();

    //检查返回的数据状态
    protected static LoadingPage.LoadDataState checkLoadData(Object data) {
        if (data == null) {
            return LoadingPage.LoadDataState.EMPTY;
        }
        if (data instanceof List) {//如果是集合
            if ((List) data == null || ((List) data).size() == 0) {

                return LoadingPage.LoadDataState.EMPTY;
            }
        }
        if (data instanceof Map) {//若果是map
            if ((Map) data == null || ((Map) data).size() == 0) {

                return LoadingPage.LoadDataState.EMPTY;
            }
        }
        return LoadingPage.LoadDataState.SUCCESS;
    }
}
