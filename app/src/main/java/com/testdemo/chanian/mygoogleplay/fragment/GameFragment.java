package com.testdemo.chanian.mygoogleplay.fragment;

import android.view.View;
import android.widget.ListView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.base.ItemAdapter;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.factory.ListViewFactory;
import com.testdemo.chanian.mygoogleplay.protocol.GameProtocol;

import java.util.List;


public class GameFragment extends BaseFragment {

    private GameProtocol mGameProtocol;
    private List<ItemInfoBean> mItemBeen;

    @Override
    protected View initSuccessView() {
        ListView listview = ListViewFactory.getListview();
        listview.setAdapter(new GameAdapter(mItemBeen,listview));
        return listview;
    }
    //初始化数据
    @Override
    protected LoadingPage.LoadDataState initData() {
        mGameProtocol = new GameProtocol();
        try {
            mItemBeen = mGameProtocol.loadData(0);
            return checkLoadData(mItemBeen);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPage.LoadDataState.ERROR;
        }
    }
    private class GameAdapter extends ItemAdapter {
        public GameAdapter(List datas, ListView lv) {
            super(datas, lv);
        }
        //加载更多数据
        @Override
        protected List<ItemInfoBean> loadMoreData() {
            try {
                Thread.sleep(1000);
                List<ItemInfoBean> itemBeen = mGameProtocol.loadData(mDatas.size());
                return itemBeen;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.loadMoreData();
        }
    }
}