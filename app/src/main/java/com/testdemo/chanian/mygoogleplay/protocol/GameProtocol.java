package com.testdemo.chanian.mygoogleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testdemo.chanian.mygoogleplay.base.BaseProtocol;
import com.testdemo.chanian.mygoogleplay.bean.ItemInfoBean;

import java.util.List;

/**
 * Created by ChanIan on 16/5/27.
 */
public class GameProtocol extends BaseProtocol<List<ItemInfoBean>>{

    @Override
    protected List<ItemInfoBean> parseJson(String jsonString) {
        Gson gson = new Gson();

        return gson.fromJson(jsonString,new TypeToken<List<ItemInfoBean>>(){}.getType());
    }

    @Override
    protected String interfaceKey() {
        return "game?";
    }
}

