package com.testdemo.chanian.mygoogleplay.holder;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testdemo.chanian.mygoogleplay.R;
import com.testdemo.chanian.mygoogleplay.base.BaseHolder;
import com.testdemo.chanian.mygoogleplay.bean.CateInfoBean;
import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by ChanIan on 16/5/29.
 */
public class CateDetailHolder extends BaseHolder<CateInfoBean> {

    @Bind(R.id.item_category_icon_1)
    ImageView mItemCategoryIcon1;
    @Bind(R.id.item_category_name_1)
    TextView mItemCategoryName1;
    @Bind(R.id.item_category_item_1)
    LinearLayout mItemCategoryItem1;
    @Bind(R.id.item_category_icon_2)
    ImageView mItemCategoryIcon2;
    @Bind(R.id.item_category_name_2)
    TextView mItemCategoryName2;
    @Bind(R.id.item_category_item_2)
    LinearLayout mItemCategoryItem2;
    @Bind(R.id.item_category_icon_3)
    ImageView mItemCategoryIcon3;
    @Bind(R.id.item_category_name_3)
    TextView mItemCategoryName3;
    @Bind(R.id.item_category_item_3)
    LinearLayout mItemCategoryItem3;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_cate_info, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void refreshView(CateInfoBean data) {
        initData(mItemCategoryName1, data.name1, mItemCategoryIcon1, data.url1);
        initData(mItemCategoryName2, data.name2, mItemCategoryIcon2, data.url2);
        initData(mItemCategoryName3, data.name3, mItemCategoryIcon3, data.url3);
    }

    private void initData(TextView itemCategoryName, final String name, ImageView itemCategoryIcon, String url) {
        if (name != null && url != null) {//与标题情况分开

            itemCategoryName.setText(name);
            Picasso.with(UIUtils.getContext()).load(Constants.HttpUrl.IMAGEURL + url).into(itemCategoryIcon);
            LinearLayout parent = (LinearLayout) mItemCategoryName1.getParent();
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(UIUtils.getContext(), name, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ViewParent parent = mItemCategoryName1.getParent();
            ((ViewGroup) parent).setVisibility(View.GONE);
        }
    }


}
