package com.testdemo.chanian.mygoogleplay.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;


/**
 * Created by ChanIan on 16/5/30.
 */
public class DetailSafeHolder extends BaseHolder<ItemInfoBean> implements View.OnClickListener {
    @Bind(R.id.app_detail_safe_iv_arrow)
    ImageView mAppDetailSafeIvArrow;
    @Bind(R.id.app_detail_safe_pic_container)
    LinearLayout mAppDetailSafePicContainer;
    @Bind(R.id.app_detail_safe_des_container)
    LinearLayout mAppDetailSafeDesContainer;
    private boolean isOpen;
    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_safe, null);
        ButterKnife.bind(this, view);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    protected void refreshView(ItemInfoBean data) {
        List<ItemInfoBean.SafeBean> safeBeen = data.safe;
        for (ItemInfoBean.SafeBean bean :safeBeen){
            String safeUrl = bean.safeUrl;
            //设置图片标签
            ImageView imageView = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.HttpUrl.IMAGEURL+safeUrl).into(imageView);
            //将图片加入容器
            mAppDetailSafePicContainer.addView(imageView);

            //设置文字描述
            TextView textView = new TextView(UIUtils.getContext());
            textView.setGravity(Gravity.CENTER);
            int padding=UIUtils.px2Dp(4);
            textView.setPadding(padding,padding,padding,padding);
            ImageView arrowIcon = new ImageView(UIUtils.getContext());
            LinearLayout layout = new LinearLayout(UIUtils.getContext());
            textView.setText(bean.safeDes);
            Picasso.with(UIUtils.getContext()).load(Constants.HttpUrl.IMAGEURL+bean.safeDesUrl).into(arrowIcon);
            //根据是否有扣费颜色区分tv背景
            if(bean.safeDesColor==0) {//常规
                textView.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
            }else {
                textView.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));

            }
            layout.addView(arrowIcon);
            layout.addView(textView);
            mAppDetailSafeDesContainer.addView(layout);

        }
        //第一次进来默认折叠不带动画
        toggleAnimation(false);
    }

    @Override
    public void onClick(View v) {
        toggleAnimation(true);
    }

    private void toggleAnimation(boolean b) {
        if(isOpen) {
            //通过测量得到高度
            mAppDetailSafeDesContainer.measure(0,0);
            int height = mAppDetailSafeDesContainer.getMeasuredHeight();
            int start=height;
            int end=0;
            //添加动画
            doAnimation(start, end);
        }else {
            mAppDetailSafeDesContainer.measure(0,0);
            int height = mAppDetailSafeDesContainer.getMeasuredHeight();
            int start=0;
            int end=height;
            doAnimation(start,end);
        }
        isOpen=!isOpen;
    }

    private void doAnimation(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.start();
        //添加监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //得到原始高
                int height = (int) animation.getAnimatedValue();
                //得到布局参数
                ViewGroup.LayoutParams params = mAppDetailSafeDesContainer.getLayoutParams();
                params.height=height;
                mAppDetailSafeDesContainer.setLayoutParams(params);
            }
        });
        //箭头动画
        if(isOpen) {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 0, 180);
            rotation.start();
        }else {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 180, 0);
            rotation.start();
        }
    }
}
