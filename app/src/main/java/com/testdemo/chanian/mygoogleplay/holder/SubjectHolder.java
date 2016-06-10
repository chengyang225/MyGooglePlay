package com.testdemo.chanian.mygoogleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.bean.SubjectBean;
import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by ChanIan on 16/5/29.
 */
public class SubjectHolder extends BaseHolder<SubjectBean> {
    @Bind(R.id.item_subject_iv_icon)
    ImageView mItemSubjectIvIcon;
    @Bind(R.id.item_subject_tv_title)
    TextView mItemSubjectTvTitle;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    protected void refreshView(SubjectBean data) {
        mItemSubjectTvTitle.setText(data.des);

        Picasso.with(UIUtils.getContext()).load(Constants.HttpUrl.IMAGEURL+data.url).into(mItemSubjectIvIcon);
    }
}
