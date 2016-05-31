package com.testdemo.chanian.mygoogleplay.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.testdemo.chanian.mygoogleplay.base.BaseFragment;
import com.testdemo.chanian.mygoogleplay.base.LoadingPage;
import com.testdemo.chanian.mygoogleplay.protocol.RecommendProtocol;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;
import com.testdemo.chanian.mygoogleplay.views.flyinout.ShakeListener;
import com.testdemo.chanian.mygoogleplay.views.flyinout.StellarMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RecommFragment extends BaseFragment {

private List<String> mDatas=new ArrayList<>();
    private RecommendProtocol mProtocol;
    private ShakeListener mListener;

    @Override
    protected View initSuccessView() {
        final StellarMap map = new StellarMap(UIUtils.getContext());
        //第一页
        //拆分屏幕
        map.setRegularity(15,20);
        final RecoAdapter adapter = new RecoAdapter();
        map.setAdapter(adapter);
        map.setGroup(0,true);

        //摇一摇切换
        mListener = new ShakeListener(UIUtils.getContext());
        mListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                //得到当前页
                int currentGroup = map.getCurrentGroup();
                if(currentGroup==adapter.getGroupCount()-1) {//最后一组
                    currentGroup=0;
                }else {
                    currentGroup++;
                }
                map.setGroup(currentGroup,true);
            }
        });
        return map;
    }

    @Override
    public void onResume() {
        if(mListener!=null) {
            mListener.resume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if(mListener!=null) {
            mListener.pause();
        }
        super.onPause();
    }

    @Override
    protected LoadingPage.LoadDataState initData() {
        mProtocol = new RecommendProtocol();
        try {
            mDatas=mProtocol.loadData(0);
//            Log.v("ian", "r:"+mDatas);
            return checkLoadData(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPage.LoadDataState.ERROR;
        }
    }
    private class RecoAdapter implements StellarMap.Adapter {
        public static final int PAGESIZE=15;
        @Override
        public int getGroupCount() {//一共多少组
            if(mDatas.size()%PAGESIZE==0) {
                return mDatas.size()/PAGESIZE;
            }
            return mDatas.size()/PAGESIZE+1;
        }

        @Override
        public int getCount(int group) {//每组个数
            if(mDatas.size()%PAGESIZE==0) {
                return PAGESIZE;
            }
            if(group==getGroupCount()-1) {//最后一组
                return mDatas.size()%PAGESIZE;
            }
            return PAGESIZE;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            TextView tv = new TextView(UIUtils.getContext());
            String data = mDatas.get(group * PAGESIZE + position);
//            Log.v("ian", "data:"+data);
            //设置随机大小
            Random random = new Random();
            tv.setTextSize(random.nextInt(6)+12);
            //设置随机显示颜色
            int alpha=255;
            int r=random.nextInt(50)+180;
            int g=random.nextInt(50)+180;
            int b=random.nextInt(50)+180;
            int argb = Color.argb(alpha, r, g, b);
            tv.setTextColor(argb);
            tv.setText(data);
            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}
