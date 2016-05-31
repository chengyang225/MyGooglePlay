package com.testdemo.chanian.mygoogleplay.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.base.SuperBaseAdapter;
import com.testdemo.chanian.mygoogleplay.bean.CateInfoBean;
import com.testdemo.chanian.mygoogleplay.factory.ListViewFactory;
import com.testdemo.chanian.mygoogleplay.holder.CateDetailHolder;
import com.testdemo.chanian.mygoogleplay.holder.CateTittleHolder;
import com.testdemo.chanian.mygoogleplay.protocol.CateProtocol;

import java.util.ArrayList;
import java.util.List;


public class CateFragment extends BaseFragment {
private List<CateInfoBean> mCateInfoBeans=new ArrayList<>();

    private CateProtocol mCateProtocol;

    @Override
    protected View initSuccessView() {
        ListView listview = ListViewFactory.getListview();
        listview.setAdapter(new CateAdapter( mCateInfoBeans,listview));
        return listview;
    }

    @Override
    protected LoadingPage.LoadDataState initData() {
        mCateProtocol = new CateProtocol();
        try {
            List<CateInfoBean> infoBeen = mCateProtocol.loadData(0);
            mCateInfoBeans=infoBeen;
            return checkLoadData(infoBeen);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPage.LoadDataState.ERROR;
        }
    }
    private class CateAdapter extends SuperBaseAdapter<CateInfoBean>{
        public CateAdapter(List datas, ListView lv) {
            super(datas, lv);
        }

        @Override
        public BaseHolder getHolder(int position) {
            CateInfoBean cateInfoBean = mCateInfoBeans.get(position);
            if(cateInfoBean.isTittle) {
                return new CateTittleHolder();
            }
            return new CateDetailHolder();
        }

        @Override
        protected int getNormalType(int position) {
            CateInfoBean infoBean = mCateInfoBeans.get(position);
            if(infoBean.isTittle) {
                return 1;
            }
            return 2;
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }
    }
}