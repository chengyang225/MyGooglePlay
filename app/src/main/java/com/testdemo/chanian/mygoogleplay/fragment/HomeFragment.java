package com.testdemo.chanian.mygoogleplay.fragment;

import android.view.View;
import android.widget.ListView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.base.ItemAdapter;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.bean.HomeInfoBean;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.factory.ListViewFactory;
import com.testdemo.chanian.mygoogleplay.holder.AdHolder;
import com.testdemo.chanian.mygoogleplay.protocol.HomeProtocol;

import java.util.List;


public class HomeFragment extends BaseFragment {

    private List<ItemInfoBean> mDatas;
    private HomeProtocol mProtocol;
    private ListView mLv;
    private List<String> mPictures;

    @Override
    protected View initSuccessView() {
        mLv = ListViewFactory.getListview();
        AdHolder holder = new AdHolder();
        //添加广告页面
        mLv.addHeaderView(holder.mView);
        holder.refreshView(mPictures);
        mLv.setAdapter(new MyAdapter(mDatas, mLv));
        return mLv;
    }

    @Override
    protected LoadingPage.LoadDataState initData() {
        mProtocol = new HomeProtocol();
        try {
            HomeInfoBean homeInfoBean = mProtocol.loadData(0);
            //判断返回的数据
            LoadingPage.LoadDataState state = checkLoadData(homeInfoBean);
            if (state != LoadingPage.LoadDataState.SUCCESS) {
                return state;
            }
            state = checkLoadData(homeInfoBean.list);
            if (state != LoadingPage.LoadDataState.SUCCESS) {
                return state;
            }
            mDatas = homeInfoBean.list;
            mPictures = homeInfoBean.picture;
            return LoadingPage.LoadDataState.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPage.LoadDataState.ERROR;
        }

    }


    private class MyAdapter extends ItemAdapter {

        public MyAdapter(List datas, ListView lv) {
            super(datas, lv);
        }

        //加载更多数据
        @Override
        protected List<ItemInfoBean> loadMoreData() {
            try {
                Thread.sleep(1000);
                HomeInfoBean infoBean = mProtocol.loadData(mDatas.size());
                return infoBean.list;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }
}
