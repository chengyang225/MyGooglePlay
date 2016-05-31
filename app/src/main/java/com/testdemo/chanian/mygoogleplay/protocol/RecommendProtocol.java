package com.testdemo.chanian.mygoogleplay.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testdemo.chanian.mygoogleplay.base.BaseProtocol;

import java.util.List;

/**
 * Created by ChanIan on 16/5/27.
 */
public class RecommendProtocol extends BaseProtocol<List<String>>{

    @Override
    protected List<String> parseJson(String jsonString) {
        Gson gson = new Gson();

        return gson.fromJson(jsonString,new TypeToken<List<String>>(){}.getType());
    }

    @Override
    protected String interfaceKey() {
        return "recommend?";
    }
}

