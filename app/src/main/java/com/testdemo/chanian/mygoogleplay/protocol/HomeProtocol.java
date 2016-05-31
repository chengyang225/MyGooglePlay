package com.testdemo.chanian.mygoogleplay.protocol;

import com.google.gson.Gson;
import com.testdemo.chanian.mygoogleplay.base.BaseProtocol;
import com.testdemo.chanian.mygoogleplay.bean.HomeInfoBean;
import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ChanIan on 16/5/27.
 */
public class HomeProtocol extends BaseProtocol<HomeInfoBean>{

    @Override
    protected HomeInfoBean parseJson(String jsonString) {
        Gson gson = new Gson();
        HomeInfoBean homeInfoBean = gson.fromJson(jsonString, HomeInfoBean.class);
        if(homeInfoBean!=null) {
            return homeInfoBean;
        }
        return null;
    }

    @Override
    protected String interfaceKey() {
        return "home?";
    }
}

