package com.testdemo.chanian.mygoogleplay.holder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ChanIan on 16/5/30.
 */
public class DetailDesHolder extends BaseHolder<ItemInfoBean> implements View.OnClickListener {
    @Bind(R.id.app_detail_des_tv_des)
    TextView mAppDetailDesTvDes;
    @Bind(R.id.app_detail_des_tv_author)
    TextView mAppDetailDesTvAuthor;
    @Bind(R.id.app_detail_des_iv_arrow)
    ImageView mAppDetailDesIvArrow;
    private int mMeasuredHeight;
    private boolean isOpen;
    private ItemInfoBean mDatas;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_des, null);
        ButterKnife.bind(this, view);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    protected void refreshView(ItemInfoBean data) {
        mDatas = data;
        mAppDetailDesTvDes.setText(data.des);
        mAppDetailDesTvAuthor.setText(data.author);
        //监听页面渲染后的高度
        mAppDetailDesTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mMeasuredHeight = mAppDetailDesTvDes.getMeasuredHeight();
                mAppDetailDesTvDes.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                toggleAnimation(false);
            }
        });
        //默认第一次不展开
    }

    @Override
    public void onClick(View v) {
        toggleAnimation(true);
    }

    private void toggleAnimation(boolean isAnimation) {
        if (isOpen) {
            int start = mMeasuredHeight;
            int end = getHeight(7, mDatas.des);
            doAnimation(start, end);
        } else {
            int start = getHeight(7, mDatas.des);
            int end = mMeasuredHeight;
            doAnimation(start, end);
        }
        isOpen = !isOpen;
    }

    private void doAnimation(int start, int end) {
        ObjectAnimator animator = ObjectAnimator.ofInt(mAppDetailDesTvDes, "height", start, end);
        animator.start();
        //箭头动画
        if (isOpen) {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 0, 180);
            rotation.start();
        } else {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 180, 0);
            rotation.start();
        }
        //点击展开直接滚动到末尾
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewParent parent = mAppDetailDesTvDes.getParent();
                while (true){
                    //先找到第二级容器
                     parent = parent.getParent();
                    if(parent instanceof ScrollView) {//找到了
                        //滑到最底层
                        ((ScrollView)parent).fullScroll(View.FOCUS_DOWN);
                        break;
                    }
                    if(parent==null) {//到顶了
                        break;
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private int getHeight(int lines, String des) {
        //得到具体textview高度
        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(des);
        textView.setLines(lines);
        textView.measure(0, 0);
        return textView.getMeasuredHeight();
    }
}
