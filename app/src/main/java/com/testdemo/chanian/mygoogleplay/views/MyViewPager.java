package com.testdemo.chanian.mygoogleplay.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ChanIan on 16/5/29.
 */
public class MyViewPager extends ViewPager {

    private float mOldX;
    private float mOldY;

    public MyViewPager(Context context) {
        this(context,null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                //得到原始坐标
                mOldX = ev.getRawX();
                mOldY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float newX = ev.getRawX();
                float newY = ev.getRawY();
                if(Math.abs(newX-mOldX)>Math.abs(newY-mOldY)) {
                    //左右滑动,自己处理,请求父控件不要拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    //上下滑动,交给父控件处理
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                
                break;
        }
        return super.onTouchEvent(ev);
    }
}
