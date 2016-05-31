package com.testdemo.chanian.mygoogleplay.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.factory.ThreadPoolProxyFactory;
import com.testdemo.chanian.mygoogleplay.holder.LoadMoreHolder;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by ChanIan on 16/5/26.
 */
public abstract class SuperBaseAdapter<ITEMBEANTYPE> extends MyBaseAdapter implements AdapterView.OnItemClickListener {
    private static final int TYPE_LOAD_MORE = 0;
    private static final int TYPE_NOMAL = 1;
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreDataTask mLoadMoreDataTask;
    private int mState;

    public SuperBaseAdapter(List datas, ListView lv) {
        super(datas);
        lv.setOnItemClickListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;

        if (convertView == null) {
            //不知道子类具体的holder类型
            if (getItemViewType(position) == TYPE_LOAD_MORE) {

                holder = getLoadMoreHolder();
            } else {
                holder = getHolder(position);
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        //接收数据,绑定数据
        if (getItemViewType(position) == TYPE_LOAD_MORE) {
            if (hasLoadMore()) {//子类需要加载更多
                //加载更多
                triggerData();
            }
            else {
                holder.setDataAndRefreshView(LoadMoreHolder.LOADING_NONE);
            }
        } else {
            holder.setDataAndRefreshView(mDatas.get(position));//mDatas由父类提供

        }
        return holder.mView;
    }

    private void triggerData() {
        //重置状态
        mState = LoadMoreHolder.LOADING_MORE;
        if (mLoadMoreDataTask == null) {
            mLoadMoreDataTask = new LoadMoreDataTask();
            mLoadMoreHolder.refreshView(mState);
            ThreadPoolProxyFactory.getNomalThreadPoolProxy().execute(mLoadMoreDataTask);
        }
    }

    private BaseHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    //根据position返回不同样式
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {//加载到最后一个
            return TYPE_LOAD_MORE;
        } else {//普通状况
            return getNormalType(position);
        }
    }

    protected int getNormalType(int position) {
        return TYPE_NOMAL;
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;//加入了一个加载更多的item
    }

    //返回样式数量,默认为1
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    public abstract BaseHolder getHolder(int position);
    //点击错误页面重新刷新页面
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(getItemViewType(position)==TYPE_LOAD_MORE) {//先判断是加载更多情况
            if(mState==LoadMoreHolder.LOADING_ERROR) {//加载错误的时候
                triggerData();//重新加载
            }
        }else {//点击普通条目
            clickNormalItem(parent,view,position,id);
        }
    }

    public void clickNormalItem(AdapterView<?> parent, View view, int position, long id) {

    }

    //加载更多数据
    private class LoadMoreDataTask implements Runnable {

        @Override
        public void run() {
            //初始状态
            mState = LoadMoreHolder.LOADING_MORE;
            final List<ITEMBEANTYPE> datas = loadMoreData();//加载返回的数据
            try {

                if (datas == null) {//加载错误
                    mState = LoadMoreHolder.LOADING_ERROR;
                } else {//加载成功
                    if (datas.size() == Constants.PAGESIZE) {//还可以加载更多数据
                        mState = LoadMoreHolder.LOADING_MORE;
                    } else {//后面没有更多数据了
                        mState = LoadMoreHolder.LOADING_NONE;
                    }
                }
            } catch (Exception e) {//有异常表示加载错误
                e.printStackTrace();
                mState = LoadMoreHolder.LOADING_ERROR;
            }


            //根据数据刷新ui 回到主线程
            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    mDatas.addAll(datas);
                    notifyDataSetChanged();
                    //****************** 设置最新状态
                    mLoadMoreHolder.refreshView(mState);
                }
            });
            //加载完将任务置空
            mLoadMoreDataTask=null;
        }
    }

    //加载更多数据,因为不是每个子类都需要加载,所有不是抽象
    protected List<ITEMBEANTYPE> loadMoreData() {

        return null;
    }

    //是否需要加载,默认为false
    protected boolean hasLoadMore() {
        return false;
    }
}
