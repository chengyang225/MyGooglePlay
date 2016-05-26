package com.testdemo.chanian.mygoogleplay.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.base.SuperBaseAdapter;
import com.testdemo.chanian.mygoogleplay.holder.HomeHolder;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment {

    private List<String> mDatas;

    @Override
    protected View initSuccessView() {

        ListView lv = new ListView(UIUtils.getContext());
        lv.setBackgroundColor(Color.CYAN);
        lv.setAdapter(new MyAdapter(mDatas));
        return lv;
    }

    @Override
    protected LoadingPage.LoadDataState initData() {
        //模拟网络请求,延时两秒
        try {
            mDatas = new ArrayList();
            for (int i = 0; i < 50; i++) {
                mDatas.add("hehe" + i);
            }
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        //随机返回一种状态
//        LoadingPage.LoadDataState[] states = {LoadingPage.LoadDataState.SUCCESS, LoadingPage.LoadDataState.EMPTY
//                , LoadingPage.LoadDataState.ERROR};
//        Random random = new Random();
//        int index = random.nextInt(3);
        return LoadingPage.LoadDataState.SUCCESS;
    }

    private class ViewHolder {
        TextView tv1;
        TextView tv2;
    }

    private class MyAdapter extends SuperBaseAdapter{

        public MyAdapter(List datas) {
            super(datas);
        }

        @Override
        public BaseHolder getHolder() {
            return new HomeHolder();
        }
    }


}
