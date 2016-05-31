package com.testdemo.chanian.mygoogleplay.fragment;

import android.view.View;
import android.widget.ListView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.base.ItemAdapter;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.factory.ListViewFactory;
import com.testdemo.chanian.mygoogleplay.protocol.AppProtocol;

import java.util.List;


public class AppFragment extends BaseFragment {

    private AppProtocol mAppProtocol;
    private List<ItemInfoBean> mItemBeen;

    @Override
    protected View initSuccessView() {
        ListView listview = ListViewFactory.getListview();
        listview.setAdapter(new AppAdapter(mItemBeen,listview));
        return listview;
    }
    //初始化数据
    @Override
    protected LoadingPage.LoadDataState initData() {
        mAppProtocol = new AppProtocol();
        try {
            mItemBeen = mAppProtocol.loadData(0);
            return checkLoadData(mItemBeen);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPage.LoadDataState.ERROR;
        }
    }
    private class AppAdapter extends ItemAdapter{
        public AppAdapter(List datas, ListView lv) {
            super(datas, lv);
        }

        //加载更多数据
        @Override
        protected List<ItemInfoBean> loadMoreData() {
            try {
                Thread.sleep(1000);
                List<ItemInfoBean> itemBeen = mAppProtocol.loadData(mDatas.size());
                return itemBeen;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.loadMoreData();
        }
    }

}
