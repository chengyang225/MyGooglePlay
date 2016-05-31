package com.testdemo.chanian.mygoogleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.activity.DetailActivity;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.utils.StringUtils;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by ChanIan on 16/5/30.
 */
public class DetailTitleHolder extends BaseHolder<ItemInfoBean> {
    @Bind(R.id.app_detail_info_iv_icon)
    ImageView mAppDetailInfoIvIcon;
    @Bind(R.id.app_detail_info_tv_name)
    TextView mAppDetailInfoTvName;
    @Bind(R.id.app_detail_info_rb_star)
    RatingBar mAppDetailInfoRbStar;
    @Bind(R.id.app_detail_info_tv_downloadnum)
    TextView mAppDetailInfoTvDownloadnum;
    @Bind(R.id.app_detail_info_tv_version)
    TextView mAppDetailInfoTvVersion;
    @Bind(R.id.app_detail_info_tv_time)
    TextView mAppDetailInfoTvTime;
    @Bind(R.id.app_detail_info_tv_size)
    TextView mAppDetailInfoTvSize;
    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_info, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void refreshView(ItemInfoBean data) {
        Picasso.with(UIUtils.getContext()).load(Constants.HttpUrl.IMAGEURL+data.iconUrl).into(mAppDetailInfoIvIcon);
        String downloadNum = UIUtils.getString(R.string.app_detail_info_downloadnum, data.downloadNum);
        String size = UIUtils.getString(R.string.app_detail_info_size, StringUtils.formatFileSize(data.size));
        String date = UIUtils.getString(R.string.app_detail_info_time, data.date);
        String version = UIUtils.getString(R.string.app_detail_info_version, data.version);
        mAppDetailInfoRbStar.setRating(data.stars);
        mAppDetailInfoTvDownloadnum.setText(downloadNum);
        mAppDetailInfoTvSize.setText(size);
        mAppDetailInfoTvName.setText(data.name);
        mAppDetailInfoTvVersion.setText(version);
        mAppDetailInfoTvTime.setText(date);
    }
}
