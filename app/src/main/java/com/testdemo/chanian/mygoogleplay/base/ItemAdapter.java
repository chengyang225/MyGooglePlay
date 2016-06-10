package com.testdemo.chanian.mygoogleplay.base;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.testdemo.chanian.mygoogleplay.activity.DetailActivity;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;
import com.testdemo.chanian.mygoogleplay.holder.ItemHolder;
import com.testdemo.chanian.mygoogleplay.manage.DownloadManager;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanIan on 16/5/30.
 */
//home,app,game三个页面的adapter
public class ItemAdapter extends SuperBaseAdapter<ItemInfoBean> {

    public ItemAdapter(List datas, ListView lv) {
        super(datas, lv);
    }
    public List<ItemHolder> mItemHolders=new ArrayList<>();

    @Override
    public BaseHolder getHolder(int position) {

        ItemHolder holder = new ItemHolder();
        //添加观察者到集合中
        mItemHolders.add(holder);
        //添加观察者到观察者集合中
        DownloadManager.getInstance().addObserver(holder);

        return holder;
    }

    @Override
    protected boolean hasLoadMore() {
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //处理首页点击移位
        position=position-((ListView)parent).getHeaderViewsCount();
//        Log.v("ian", "position:"+position);
        //强转数据
        ItemInfoBean bean = (ItemInfoBean) mDatas.get(position);
        Toast.makeText(UIUtils.getContext(), bean.packageName, Toast.LENGTH_SHORT).show();

        //跳转页面
        Intent intent=new Intent(UIUtils.getContext(), DetailActivity.class);
        //fragment和activity之间跳转必须设置flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //将包名传过去
        intent.putExtra("packName",bean.packageName);
        UIUtils.getContext().startActivity(intent);
        super.onItemClick(parent, view, position, id);
    }
}
