package com.testdemo.chanian.mygoogleplay.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.astuetz.PagerSlidingTabStripCopy;
import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.factory.FragmentFactory;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dl_slide;
    private ActionBar mActionBar;
    private ActionBarDrawerToggle mToggle;
    private PagerSlidingTabStripCopy tabs;
    private ViewPager vp_content;
    private String[] mTittles;
    private ContentAdapter mAdapter;
    private BaseFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getData();
    }

    //获取数据
    private void getData() {
        //获取标题数据
        mTittles = UIUtils.getStringArray(R.array.main_titles);
        mAdapter = new ContentAdapter(getSupportFragmentManager());
        vp_content.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        tabs.setViewPager(vp_content);
    }

    //viewpager适配器
    private class ContentAdapter extends FragmentStatePagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            //            Log.i("ian", "ContentAdapter getCount()");
            if (mTittles != null) {

                return mTittles.length;
            }
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //            Log.i("ian", "ContentAdapter instantiateItem()");
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.getFragment(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTittles[position];
        }
    }

    //初始化组件
    private void initView() {
        //得到actionBar
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("GooglePlay");
        mActionBar.setDisplayHomeAsUpEnabled(true);
        //设置侧滑
        dl_slide = (DrawerLayout) findViewById(R.id.dl_slide);
        //设置点击回退按钮
        mToggle = new ActionBarDrawerToggle(this, dl_slide, R.string.open, R.string.close);
        //同步状态
        mToggle.syncState();
        //dl_slide设置监听
        dl_slide.setDrawerListener(mToggle);

        tabs = (PagerSlidingTabStripCopy) findViewById(R.id.tabs);
        vp_content = (ViewPager) findViewById(R.id.vp_content);

        //监听页面切换
        final MyOnPageChangeListener listener = new MyOnPageChangeListener();
        tabs.setOnPageChangeListener(listener);
        //默认一开始加载首页
        //检测当整个布局渲染完成后才开始渲染控件
        vp_content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                listener.onPageSelected(0);
                //移除检测
                vp_content.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });
    }

    //设置按钮点击侧边栏回退
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //打开或关闭侧边栏
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //切换页面的监听器
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //在选中时才去请求数据,不要做预加载
            mFragment = FragmentFactory.getFragment(position);
            mFragment.mLoadingPage.triggleData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
