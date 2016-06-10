package com.testdemo.chanian.mygoogleplay.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.R;

/**
 * Created by ChanIan on 16/6/6.
 */
public class ProgressView extends LinearLayout {

    private ImageView mIv;
    private float max = 100;
    private float mProgress;
    private boolean isEnable = true;

    public void setMax(float max) {
        this.max = max;
    }

    public void setProgress(float progress) {
        mProgress = progress;
        //设置重绘
        invalidate();
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    //设置文本
    public void setTv(String content) {
        mTv.setText(content);
    }

    //设置图标
    public void setIv(int resId) {
        mIv.setImageResource(resId);
    }

    private TextView mTv;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //挂载自定义控件
//        View view = LayoutInflater.from(context).inflate( R.layout.item_progress_view, null);
        View view = ProgressView.inflate(context, R.layout.item_progress_view, this);
        mIv = (ImageView) view.findViewById(R.id.iv_icon);
        mTv = (TextView) view.findViewById(R.id.tv_content);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isEnable) {
            RectF rectF = new RectF(mIv.getLeft(), mIv.getTop(), mIv.getRight(), mIv.getBottom());
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            int startAngle = -90;
            int sweepAngle = (int) (mProgress*1.0f/max*360);
            canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
