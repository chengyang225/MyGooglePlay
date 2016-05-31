package com.testdemo.chanian.mygoogleplay.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
public class DetailPicHolder extends BaseHolder<ItemInfoBean> {
    @Bind(R.id.app_detail_pic_iv_container)
    LinearLayout mAppDetailPicIvContainer;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_detail_pic, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void refreshView(ItemInfoBean data) {
        List<String> screen = data.screen;
        for(int i = 0; i < screen.size(); i++) {

            //设置图片
            ImageView imageView = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.HttpUrl.IMAGEURL+screen.get(i)).into(imageView);
            //设置布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i!=0) {
                params.leftMargin=UIUtils.px2Dp(5);
            }
            mAppDetailPicIvContainer.addView(imageView,params);
        }
    }
}
