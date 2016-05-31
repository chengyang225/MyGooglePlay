package com.testdemo.chanian.mygoogleplay.protocol;

import android.util.Log;

import com.google.gson.Gson;
import com.testdemo.chanian.mygoogleplay.base.BaseProtocol;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChanIan on 16/5/30.
 */
public class DetailProtocol extends BaseProtocol<ItemInfoBean> {
    private String mPackName;

    public DetailProtocol(String packName) {
        mPackName = packName;
    }

    @Override
    protected ItemInfoBean parseJson(String jsonString) throws JSONException {
        Gson gson = new Gson();
        return gson.fromJson(jsonString,ItemInfoBean.class);
    }

    @Override
    protected String interfaceKey() {
        return "detail?";
    }

    @Override
    protected Map<String, Object> getKeyMap(int index) {
        Map<String, Object> paramsMap=new HashMap<>();
        paramsMap.put("packageName", mPackName);
        return paramsMap;
    }
}
