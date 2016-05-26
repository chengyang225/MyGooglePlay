package com.testdemo.chanian.mygoogleplay.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.factory.ThreadPoolProxyFactory;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

/**
 * Created by ChanIan on 16/5/25.
 */
//用语过渡初始化视图和数据
public abstract class LoadingPage extends FrameLayout {

    private View mLoadingView;//正在加载视图
    private View mEmptyView;//空视图
    private View mErrorView;//错误视图
    private View mSucessView;//请求成功视图

    public static final int STATE_LOADING = 0;//加载中
    public static final int STATE_EMPTY = 1;//空
    public static final int STATE_ERROR = 2;//错误
    public static final int STATE_SUCESS = 3;//成功

    private int mCurrentState = STATE_LOADING;//当前状态
    public LoadingDataTask mDataTask;//加载数据任务

    //使用枚举将加载数据状态限制在成功,空,错误三种状态
    public enum LoadDataState {
        SUCCESS(STATE_SUCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);
        int state;

        public int getState() {
            return state;
        }

        LoadDataState(int state) {
            this.state = state;
        }
    }


    public LoadingPage(Context context) {
        super(context);
        initView();
    }

    //初始化常规视图
    private void initView() {
        mLoadingView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pager_loading, null);
        mEmptyView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pager_empty, null);
        mErrorView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.pager_error, null);
        //将视图添加到容器中
        this.addView(mLoadingView);
        this.addView(mEmptyView);
        this.addView(mErrorView);
        //刷新页面显示
        refreshViewPage();

        //点击错误页面按钮重新加载数据
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新加载数据
                triggleData();
            }
        });
    }
    //页面显示分析
    //Fragment共性-->页面共性-->视图的展示

    /**
     * 任何应用其实就只有4种页面类型
     * ① 加载页面
     * ② 错误页面
     * ③ 空页面
     * ④ 成功页面
     * ①②③三种页面一个应用基本是固定的
     * 每一个fragment对应的页面④就不一样
     * 进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个
     */

    //刷新页面显示
    private void refreshViewPage() {
        //控制只显示一种状态
        mLoadingView.setVisibility((mCurrentState == STATE_LOADING ? VISIBLE : GONE));
        mEmptyView.setVisibility((mCurrentState == STATE_EMPTY ? VISIBLE : GONE));
        mErrorView.setVisibility((mCurrentState == STATE_ERROR ? VISIBLE : GONE));
        //数据加载成功,显示成功视图
        if (mCurrentState == STATE_SUCESS && mSucessView == null) {
            mSucessView = initSuccessView();
            this.addView(mSucessView);
        }

        //设置加载成功后视图显示
        if (mSucessView != null) {
            mSucessView.setVisibility((mCurrentState == STATE_SUCESS ? VISIBLE : GONE));

        }
    }

    // 数据加载的流程

    /**
     * ① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
     * ② 异步加载数据  -->显示加载视图
     * ③ 处理加载结果
     * ① 成功-->显示成功视图
     * ② 失败
     * ① 数据为空-->显示空视图
     * ② 数据加载失败-->显示加载失败的视图
     */
    public void triggleData() {
        //如果不是成功状态或者没有加载任务时才去加载
        if (mCurrentState != mCurrentState || mDataTask == null) {
            //每次加载数据要将状态重置为正在加载状态
            mCurrentState = STATE_LOADING;
            //刷新页面
            refreshViewPage();
            //要从网络获取,开条子线程
            mDataTask = new LoadingDataTask();
//            new Thread(mDataTask).start();
            //使用线程池请求
            ThreadPoolProxyFactory.getNomalThreadPoolProxy().execute(mDataTask);
        }
    }

    private class LoadingDataTask implements Runnable {
        @Override
        public void run() {
            //获取数据,得到返回状态
            LoadDataState state = initData();
            //更新当前状态
            mCurrentState = state.getState();
            //回主线程更新页面
            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    refreshViewPage();
                }
            });
            //每次加载完成一个任务后任务要置空
            mDataTask = null;
        }
    }

    //每个子类初始化数据不一样,让子类自己实现去
    protected abstract LoadDataState initData();

    //每个子类显示view不一样,让子类自己实现去
    protected abstract View initSuccessView();
}
