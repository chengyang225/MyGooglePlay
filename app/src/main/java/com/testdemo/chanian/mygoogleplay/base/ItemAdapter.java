package com.testdemo.chanian.mygoogleplay.base;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.testdemo.chanian.mygoogleplay.activity.DetailActivity;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.holder.ItemHolder;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by ChanIan on 16/5/30.
 */
//home,app,game三个页面的adapter
public class ItemAdapter extends SuperBaseAdapter<ItemInfoBean> {

    public ItemAdapter(List datas, ListView lv) {
        super(datas, lv);
    }

    @Override
    public BaseHolder getHolder(int position) {

        return new ItemHolder();
    }

    @Override
    protected boolean hasLoadMore() {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //处理首页点击移位
        position=position-((ListView)parent).getHeaderViewsCount();
        //强转数据
        ItemInfoBean bean = (ItemInfoBean) mDatas.get(position);
//        Toast.makeText(UIUtils.getContext(), bean.packageName, Toast.LENGTH_SHORT).show();

        //跳转页面
        Intent intent=new Intent(UIUtils.getContext(), DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //将包名传过去
        intent.putExtra("packName",((ItemInfoBean) mDatas.get(position)).packageName);
        UIUtils.getContext().startActivity(intent);
        super.onItemClick(parent, view, position, id);
    }
}
