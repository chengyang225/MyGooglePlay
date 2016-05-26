package com.testdemo.chanian.mygoogleplay.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import java.util.Random;


public class HotFragment extends BaseFragment {


    @Override
    protected View initSuccessView() {
        TextView tv=new TextView(UIUtils.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.RED);
        tv.setText(this.getClass().getSimpleName());
        return tv;
    }

    @Override
    protected LoadingPage.LoadDataState initData() {
        //模拟网络请求,延时两秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //随机返回一种状态
        LoadingPage.LoadDataState[] states={LoadingPage.LoadDataState.SUCCESS, LoadingPage.LoadDataState.EMPTY
                , LoadingPage.LoadDataState.ERROR};
        Random random = new Random();
        int index = random.nextInt(3);
        return states[index];
    }
}
