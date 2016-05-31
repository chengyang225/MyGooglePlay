package com.testdemo.chanian.mygoogleplay.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;


/**
 * Created by ChanIan on 16/5/28.
 */
public class AdHolder extends BaseHolder<List<String>> implements ViewPager.OnPageChangeListener {
    @Bind(R.id.item_home_picture_pager)
    ViewPager mItemHomePicturePager;
    @Bind(R.id.item_home_picture_container_indicator)
    LinearLayout mItemHomePictureContainerIndicator;
    private List<String> mPageIcons = new ArrayList<>();

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_home_viewpager, null);
        //绑定子控件
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void refreshView(List<String> data) {
        mPageIcons = data;
        mItemHomePicturePager.setAdapter(new AppAdapter());
        //处理页面下方小圆点
        for (int i = 0; i < data.size(); i++) {
            ImageView dotIv = new ImageView(UIUtils.getContext());
            dotIv.setImageResource(R.drawable.indicator_normal);
            //默认第一个为选中状态
            if (i == 0) {
                dotIv.setImageResource(R.drawable.indicator_selected);
            }
            int width = UIUtils.px2Dp(5);
            int height = UIUtils.px2Dp(10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            //设置边距
            params.leftMargin = 5;
            params.bottomMargin = 5;
            mItemHomePictureContainerIndicator.addView(dotIv, params);
        }
        //设置无限轮播
        mItemHomePicturePager.addOnPageChangeListener(this);
        //设置默认第一张
        int index=Integer.MAX_VALUE/2-Integer.MAX_VALUE/2%mPageIcons.size();
        mItemHomePicturePager.setCurrentItem(index);
        //设置自动轮播
        final AutoPlayTask task = new AutoPlayTask();
        task.start();
        //点击时停止轮播
        mItemHomePicturePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                        task.stop();
                        break;
                    case MotionEvent.ACTION_CANCEL :
                        task.start();
                        break;
                }
                return true;
            }
        });

    }
    private class AutoPlayTask implements Runnable{
        public void start(){
            UIUtils.getHandler().postDelayed(this,2000);
        }
        public void stop(){
            UIUtils.getHandler().removeCallbacks(this);
        }
        @Override
        public void run() {
            int currentItem = mItemHomePicturePager.getCurrentItem();
            currentItem++;
            mItemHomePicturePager.setCurrentItem(currentItem);
            //开始任务
            start();
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }
    //滑动结束
    @Override
    public void onPageSelected(int i) {
        i=i%mPageIcons.size();
        for(int j = 0; j < mPageIcons.size(); j++) {
            //还原所有原点
            ImageView dotIv = (ImageView) mItemHomePictureContainerIndicator.getChildAt(j);
            dotIv.setImageResource(R.drawable.indicator_normal);
            //设置选中状态
            if (i == j) {
                dotIv.setImageResource(R.drawable.indicator_selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private class AppAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (mPageIcons != null) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        //初始化显示的广告
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position=position%mPageIcons.size();
            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);//设置自适应
            String url = mPageIcons.get(position);
            url = Constants.HttpUrl.IMAGEURL + url;
            //            Log.v("ian", "url:"+url);
            Picasso.with(UIUtils.getContext()).load(url).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }

}
