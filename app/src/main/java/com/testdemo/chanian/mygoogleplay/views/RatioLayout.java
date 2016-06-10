package com.testdemo.chanian.mygoogleplay.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.testdemo.chanian.mygoogleplay.R;

/**
 * Created by ChanIan on 16/6/8.
 */
public class RatioLayout extends FrameLayout {
    private float mPicRatio = 1.f;//图片宽高比(picWidth/picHeight)
    public static final int WIDTH_EXACTLY=1;//宽度确定,可以计算高度
    public static final int HEIGHT_EXACTLY=2;//高度确定,可以计算宽度
    private int mCurrentMode=HEIGHT_EXACTLY;//默认高度确定

    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }

    public void setCurrentMode(int currentMode) {
        mCurrentMode = currentMode;
    }

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mCurrentMode=typedArray.getInt(R.styleable.RatioLayout_relative,0);
        mPicRatio=typedArray.getFloat(R.styleable.RatioLayout_picRatio,1);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //通过宽度模式计算宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY&&mCurrentMode==WIDTH_EXACTLY) {
            //如果宽度确定动态计算高度
            int width = MeasureSpec.getSize(widthMeasureSpec);
            //通过宽高比确定高度
            int height = (int) (width / mPicRatio + .5f);
            //求出子类宽高
            int childWidth=width-getPaddingLeft()-getPaddingRight();
            int childHeight=height-getPaddingTop()-getPaddingBottom();
            //根据指定约束让子类自己测量自己
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec,childHeightMeasureSpec);
            //储存测量的宽高
            setMeasuredDimension(width, height);
        } else if (heightMode == MeasureSpec.EXACTLY&&mCurrentMode==HEIGHT_EXACTLY) {
            //如果高度确定动态计算宽度
            int height = MeasureSpec.getSize(heightMeasureSpec);
            //通过宽高比确定高度
            int width = (int) (height * mPicRatio + .5f);
            //求出子类宽高
            int childWidth=width-getPaddingLeft()-getPaddingRight();
            int childHeight=height-getPaddingTop()-getPaddingBottom();
            //根据指定约束让子类自己测量自己
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec,childHeightMeasureSpec);
            //储存测量的宽高
            setMeasuredDimension(width, height);
        } else {

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
