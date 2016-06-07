package com.testdemo.chanian.mygoogleplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.holder.DetailButtonHolder;
import com.testdemo.chanian.mygoogleplay.holder.DetailDesHolder;
import com.testdemo.chanian.mygoogleplay.holder.DetailPicHolder;
import com.testdemo.chanian.mygoogleplay.holder.DetailSafeHolder;
import com.testdemo.chanian.mygoogleplay.holder.DetailTitleHolder;
import com.testdemo.chanian.mygoogleplay.manage.DownloadInfo;
import com.testdemo.chanian.mygoogleplay.manage.DownloadManager;
import com.testdemo.chanian.mygoogleplay.protocol.DetailProtocol;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ChanIan on 16/5/30.
 */
public class DetailActivity extends AppCompatActivity {


    @Bind(R.id.detail_fl_bottom)
    FrameLayout mDetailFlBottom;
    @Bind(R.id.detail_fl_info)
    FrameLayout mDetailFlInfo;
    @Bind(R.id.detail_fl_safe)
    FrameLayout mDetailFlSafe;
    @Bind(R.id.detail_fl_pic)
    FrameLayout mDetailFlPic;
    @Bind(R.id.detail_fl_des)
    FrameLayout mDetailFlDes;
    private String mPackName;
    private DetailProtocol mProtocol;
    private ItemInfoBean mInfoBean;
    private DetailButtonHolder mButtonHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化actionbar
        initActionBar();

        //初始化首页
        LoadingPage page = new LoadingPage(UIUtils.getContext()) {
            @Override
            protected LoadDataState initData() {
                return DetailActivity.this.initData();
            }

            @Override
            protected View initSuccessView() {
                View view = View.inflate(UIUtils.getContext(), R.layout.item_deital, null);
                ButterKnife.bind(DetailActivity.this, view);
                //标题详情
                DetailTitleHolder titleHolder = new DetailTitleHolder();
                mDetailFlInfo.addView(titleHolder.mView);
                titleHolder.setDataAndRefreshView(mInfoBean);
                //安全标签
                DetailSafeHolder safeHolder = new DetailSafeHolder();
                mDetailFlSafe.addView(safeHolder.mView);
                safeHolder.setDataAndRefreshView(mInfoBean);
                //截图
                DetailPicHolder picHolder = new DetailPicHolder();
                mDetailFlPic.addView(picHolder.mView);
                picHolder.setDataAndRefreshView(mInfoBean);
                //描述
                DetailDesHolder desHolder = new DetailDesHolder();
                mDetailFlDes.addView(desHolder.mView);
                desHolder.setDataAndRefreshView(mInfoBean);
                //下载,分享
                mButtonHolder = new DetailButtonHolder();
                //添加观察者
                DownloadManager.getInstance().addObserver(mButtonHolder);
                mDetailFlBottom.addView(mButtonHolder.mView);
                mButtonHolder.setDataAndRefreshView(mInfoBean);
                return view;
            }
        };
        setContentView(page);
        //拿到传过来的包名
        Intent intent = getIntent();
        mPackName = intent.getStringExtra("packName");

        page.triggleData();

    }

    //失去焦点时解除绑定
    @Override
    protected void onPause() {
        super.onPause();
        if (mButtonHolder != null) {
            DownloadManager.getInstance().removeObsever(mButtonHolder);


        }
    }

    //获取焦点时重新绑定
    @Override
    protected void onResume() {
        super.onResume();
        if (mButtonHolder != null) {
            DownloadManager.getInstance().addObserver(mButtonHolder);
            //手动发布最新状态
            DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(mInfoBean);
            DownloadManager.getInstance().notifyObsever(downloadInfo);
        }
    }

    //加载数据
    private LoadingPage.LoadDataState initData() {
        mProtocol = new DetailProtocol(mPackName);
        try {
            ItemInfoBean itemInfoBean = mProtocol.loadData(0);
            mInfoBean = itemInfoBean;
            if (itemInfoBean == null) {
                return LoadingPage.LoadDataState.EMPTY;
            }
            return LoadingPage.LoadDataState.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPage.LoadDataState.ERROR;
        }

    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("GooglePlay");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //点击返回按钮关闭页面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
