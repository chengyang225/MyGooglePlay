package com.testdemo.chanian.mygoogleplay.protocol;

import android.util.Log;

import com.testdemo.chanian.mygoogleplay.base.BaseProtocol;
import com.testdemo.chanian.mygoogleplay.bean.CateInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanIan on 16/5/29.
 */
public class CateProtocol extends BaseProtocol<List<CateInfoBean>> {
    @Override
    protected List<CateInfoBean> parseJson(String jsonString) throws JSONException {
        List<CateInfoBean> infoBeens=new ArrayList<>();
        JSONArray jsonArrayInfos = new JSONArray(jsonString);
        for(int i = 0; i < jsonArrayInfos.length(); i++) {
            JSONObject jsonObj = jsonArrayInfos.getJSONObject(i);
            String tittle = jsonObj.getString("title");
            CateInfoBean cateInfoBean = new CateInfoBean();
            cateInfoBean.isTittle=true;
            cateInfoBean.title=tittle;
            infoBeens.add(cateInfoBean);

            JSONArray infos = jsonObj.getJSONArray("infos");
            for(int j = 0; j < infos.length(); j++) {
                CateInfoBean infoBean = new CateInfoBean();
                JSONObject jsonObject = infos.getJSONObject(j);
                String name1 = jsonObject.getString("name1");
                String name2 = jsonObject.getString("name2");
                String name3 = jsonObject.getString("name3");
                String url1 = jsonObject.getString("url1");
                String url2 = jsonObject.getString("url2");
                String url3 = jsonObject.getString("url3");
                infoBean.name1=name1;
                infoBean.name2=name2;
                infoBean.name3=name3;
                infoBean.url1=url1;
                infoBean.url2=url2;
                infoBean.url3=url3;
                infoBeens.add(infoBean);
            }


        }
        return infoBeens;
    }

    @Override
    protected String interfaceKey() {
        return "category?";
    }
}
