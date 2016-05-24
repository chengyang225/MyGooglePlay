package com.testdemo.chanian.mygoogleplay.factory;

import android.support.v4.app.Fragment;

import com.testdemo.chanian.mygoogleplay.fragment.AppFragment;
import com.testdemo.chanian.mygoogleplay.fragment.CateFragment;
import com.testdemo.chanian.mygoogleplay.fragment.GameFragment;
import com.testdemo.chanian.mygoogleplay.fragment.HomeFragment;
import com.testdemo.chanian.mygoogleplay.fragment.HotFragment;
import com.testdemo.chanian.mygoogleplay.fragment.RecommFragment;
import com.testdemo.chanian.mygoogleplay.fragment.SubjFragment;

/**
 * Created by ChanIan on 16/5/24.
 */
public class FragmentFactory {
//    <item>首页</item>
//    <item>应用</item>
//    <item>游戏</item>
//    <item>专题</item>
//    <item>推荐</item>
//    <item>分类</item>
//    <item>排行</item>
    public static final int HOMEFRAGMENT=0;
    public static final int APPFRAGMENT=1;
    public static final int GAMEFRAGMENT=2;
    public static final int SUBFRAGMENT=3;
    public static final int RECOFRAGMENT=4;
    public static final int CATEFRAGMENT=5;
    public static final int HOTFRAGMENT=6;
    public static Fragment getFragment(int resId){
        Fragment fragment=null;
        switch (resId) {
            case HOMEFRAGMENT :
                fragment=new HomeFragment();
                break;
            case APPFRAGMENT :
                fragment=new AppFragment();
                break;
            case GAMEFRAGMENT :
                fragment=new GameFragment();
                break;
            case SUBFRAGMENT :
                fragment=new SubjFragment();
                break;
            case RECOFRAGMENT :
                fragment=new RecommFragment();
                break;
            case CATEFRAGMENT :
                fragment=new CateFragment();
                break;
            case HOTFRAGMENT :
                fragment=new HotFragment();
                break;
        }
        return fragment;
    }
}
