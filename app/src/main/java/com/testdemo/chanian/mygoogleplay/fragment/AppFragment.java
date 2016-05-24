package com.testdemo.chanian.mygoogleplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.utils.UIUtils;



public class AppFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv=new TextView(UIUtils.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setText(this.getClass().getSimpleName());
        return tv;
    }
}
