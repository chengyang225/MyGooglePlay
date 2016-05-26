package com.testdemo.chanian.mygoogleplay.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.testdemo.chanian.mygoogleplay.holder.HomeHolder;

import java.util.List;

/**
 * Created by ChanIan on 16/5/26.
 */
public abstract class SuperBaseAdapter extends MyBaseAdapter {
    public SuperBaseAdapter(List datas) {
        super(datas);
    }
    @Override
    public android.view.View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if(convertView==null) {
            holder=getHolder();//不知道子类具体的holder类型
        }else {
            holder= (BaseHolder) convertView.getTag();
        }
        holder.setDataAndRefreshView(mDatas.get(position));//mDatas由父类提供
        return holder.mView;
    }

    public abstract BaseHolder getHolder();
}
