package com.testdemo.chanian.mygoogleplay.fragment;

import android.view.View;
import android.widget.ListView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.base.SuperBaseAdapter;
import com.testdemo.chanian.mygoogleplay.bean.SubjectBean;
import com.testdemo.chanian.mygoogleplay.factory.ListViewFactory;
import com.testdemo.chanian.mygoogleplay.holder.SubjectHolder;
import com.testdemo.chanian.mygoogleplay.protocol.SubjectProtocol;

import java.util.List;


public class SubjFragment extends BaseFragment {


    private SubjectProtocol mSubjectProtocol;
    private List<SubjectBean> mItemBeen;

    @Override
    protected View initSuccessView() {
        ListView listview = ListViewFactory.getListview();
        listview.setAdapter(new SubjectAdapter(mItemBeen,listview));
        return listview;
    }
    //初始化数据
    @Override
    protected LoadingPage.LoadDataState initData() {
        mSubjectProtocol = new SubjectProtocol();
        try {
            mItemBeen = mSubjectProtocol.loadData(0);
            return checkLoadData(mItemBeen);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPage.LoadDataState.ERROR;
        }
    }
    private class SubjectAdapter extends SuperBaseAdapter<SubjectBean> {
        public SubjectAdapter(List datas, ListView lv) {
            super(datas, lv);
        }

        @Override
        public BaseHolder getHolder(int position) {
            return new SubjectHolder();
        }
        //加载更多数据
        @Override
        protected List<SubjectBean> loadMoreData() {
            try {
                Thread.sleep(1000);
                List<SubjectBean> itemBeen = mSubjectProtocol.loadData(mDatas.size());
                return itemBeen;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.loadMoreData();
        }
        //需要加载更多
        @Override
        protected boolean hasLoadMore() {
            return true;
        }
    }
}