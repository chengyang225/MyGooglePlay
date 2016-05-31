package com.testdemo.chanian.mygoogleplay.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.utils.StringUtils;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by ChanIan on 16/5/26.
 */
//
public class ItemHolder extends BaseHolder<ItemInfoBean>

{
    @Bind(R.id.item_appinfo_iv_icon)
    ImageView mItemAppinfoIvIcon;
    @Bind(R.id.item_appinfo_tv_title)
    TextView mItemAppinfoTvTitle;
    @Bind(R.id.item_appinfo_rb_stars)
    RatingBar mItemAppinfoRbStars;
    @Bind(R.id.item_appinfo_tv_size)
    TextView mItemAppinfoTvSize;
    @Bind(R.id.item_appinfo_tv_des)
    TextView mItemAppinfoTvDes;

    @Override
    public View initView() {
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_app_info, null);
        //与子对象绑定
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void refreshView(ItemInfoBean data) {
        mItemAppinfoRbStars.setRating(data.stars);//评级
        mItemAppinfoTvDes.setText(data.des);//描述
        mItemAppinfoTvSize.setText(StringUtils.formatFileSize(data.size));
        mItemAppinfoTvTitle.setText(data.name);//应用名

        //使用
        Picasso.with(UIUtils.getContext()).load(Constants.HttpUrl.IMAGEURL+data.iconUrl).into(mItemAppinfoIvIcon);
    }
}
//
//    private String mData;//需要绑定的数据
//
//    public ItemHolder() {
//        mView.setTag(this);//将holder本身作为tag传进去
//    }
//    //传递数据
//    public void setDataAndRefreshView(String data){
//        this.mData=data;
//        refreshView(data);
//    }
//
//    private void refreshView(String data) {
//    }




