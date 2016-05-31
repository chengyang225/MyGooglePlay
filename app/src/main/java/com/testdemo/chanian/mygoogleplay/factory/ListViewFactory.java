package com.testdemo.chanian.mygoogleplay.factory;

import android.widget.ListView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

/**
 * Created by ChanIan on 16/5/28.
 */
//专门生成listview
public class ListViewFactory {
    public static ListView getListview() {

        ListView listView = new ListView(UIUtils.getContext());
        return listView;
    }
}
