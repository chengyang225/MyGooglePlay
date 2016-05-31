package com.testdemo.chanian.mygoogleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.protocol.HotProtocol;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;
import com.testdemo.chanian.mygoogleplay.views.FlowLayout;

import java.util.List;
import java.util.Random;


public class HotFragment extends BaseFragment {


    private List<String> mDatas;

    @Override
    protected View initSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
        for (String data :mDatas){
            TextView textView = new TextView(UIUtils.getContext());
            textView.setText(data);
            //设置居中显示和padding
            textView.setGravity(Gravity.CENTER);
            int padding=UIUtils.px2Dp(5);
            textView.setPadding(padding,padding,padding,padding);
            //设置随机背景和颜色
            GradientDrawable bg = new GradientDrawable();
            Random random = new Random();
            bg.setCornerRadius(5);
            int alpha=255;
            int r=random.nextInt(50)+180;
            int g=random.nextInt(50)+180;
            int b=random.nextInt(50)+180;
            int argb = Color.argb(alpha, r, g, b);
            bg.setColor(argb);
            //设置点击样式
            GradientDrawable pressBg = new GradientDrawable();
            pressBg.setColor(Color.CYAN);

            StateListDrawable select=new StateListDrawable();
            select.addState(new int[]{android.R.attr.state_pressed},pressBg);
            select.addState(new int[]{},bg);
            textView.setBackground(select);
            textView.setClickable(true);
            flowLayout.addView(textView);
        }
        scrollView.addView(flowLayout);
        return scrollView;
    }
    //请求数据
    @Override
    protected LoadingPage.LoadDataState initData() {
        HotProtocol protocol = new HotProtocol();
        try {
            mDatas = protocol.loadData(0);
            return checkLoadData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPage.LoadDataState.ERROR;
        }
    }
}
