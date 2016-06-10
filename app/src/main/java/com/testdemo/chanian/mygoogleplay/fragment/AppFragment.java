package com.testdemo.chanian.mygoogleplay.fragment;

import android.view.View;
import android.widget.ListView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.base.ItemAdapter;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.factory.ListViewFactory;
import com.testdemo.chanian.mygoogleplay.holder.ItemHolder;
import com.testdemo.chanian.mygoogleplay.manage.DownloadInfo;
import com.testdemo.chanian.mygoogleplay.manage.DownloadManager;
import com.testdemo.chanian.mygoogleplay.protocol.AppProtocol;

import java.util.List;


public class AppFragment extends BaseFragment {

    private AppProtocol mAppProtocol;
    private List<ItemInfoBean> mItemBeen;
    private AppAdapter mAdapter;

    @Override
    protected View initSuccessView() {
        ListView listview = ListViewFactory.getListview();
        mAdapter = new AppAdapter(mItemBeen, listview);
        listview.setAdapter(mAdapter);
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
    @Override
    public void onPause() {
        super.onPause();
        if(mAdapter!=null) {
            List<ItemHolder> holders = mAdapter.mItemHolders;
            //失去焦点时移除观察者
            for (ItemHolder holder :holders){
                DownloadManager.getInstance().removeObsever(holder);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter!=null) {
            List<ItemHolder> holders = mAdapter.mItemHolders;
            //获得焦点时添加观察者并通知界面更新
            for (ItemHolder holder :holders){
                DownloadManager.getInstance().addObserver(holder);
                DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(holder.mData);
                DownloadManager.getInstance().notifyObsever(downloadInfo);
            }
        }
    }

}
