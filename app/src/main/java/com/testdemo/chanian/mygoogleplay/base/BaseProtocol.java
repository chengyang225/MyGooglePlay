package com.testdemo.chanian.mygoogleplay.base;

import android.util.Log;

import com.testdemo.chanian.mygoogleplay.config.Constants;
import com.testdemo.chanian.mygoogleplay.utils.FileUtils;
import com.testdemo.chanian.mygoogleplay.utils.HttpUtils;
import com.testdemo.chanian.mygoogleplay.utils.IOUtils;
import com.testdemo.chanian.mygoogleplay.utils.UIUtils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ChanIan on 16/5/27.
 */
public abstract class BaseProtocol<T> {
    public T loadData(int index) throws Exception {
        //从内存中获取
        T t = getDataFromMem(index);
        if (t != null) {
            Log.v("ian", "从内存获取数据");
            return t;

        }
       t = getDataFromDisk(index);
        if (t != null){
            Log.v("ian", "从本地获取数据");
            return t;
        }


        return getDataFromNet(index);

    }
    //从内存获取
    private T getDataFromMem(int index) {
        try {
            //从内存中获取资源
            MyApplication app = (MyApplication) UIUtils.getContext();
            Map<String, String> keyMap = app.getKey();
            String key=generateKey(index);
            if(keyMap.containsKey(key)) {
                String jsonString = keyMap.get(key);
                T t = parseJson(jsonString);

                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private T getDataFromDisk(int index)  {
        BufferedReader reader=null;
        try {
            //从本地获取
            String dir = FileUtils.getDir("json");
            String key = generateKey(index);
            File file =  new File(dir, key);
            if(file.exists()) {//本地有缓存
                reader=new BufferedReader(new FileReader(file));
                String oldTime = reader.readLine();
                long newTime = new Date().getTime();
                Log.v("ian", "time:"+newTime);
                if(newTime-Long.parseLong(oldTime)<= Constants.PERIOD) {
                    String jsonString = reader.readLine();//本地的缓存
                    T t = parseJson(jsonString);
                    //存到内存
                    MyApplication app = (MyApplication) UIUtils.getContext();
                    Map<String, String> keyMap = app.getKey();
                    keyMap.put(key,jsonString);
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关流
            IOUtils.close(reader);
        }
        return null;
    }



    //生成唯一key
    private String generateKey(int index) {
        //由于详情页面传入key和其他页面不同,因此要生成新的唯一key,value
        Map<String, Object> keyMap = getKeyMap(index);
        for (Map.Entry<String,Object> info :keyMap.entrySet()){
            String key = info.getKey();//key:app/detail....
            Object value = info.getValue();//value:0,20/com.m520it.www
            return interfaceKey()+"."+value;

        }
        return null;
    }

    //从网络获取资源
    private T getDataFromNet(int index) {
        BufferedWriter writer=null;
        try {
            //创建请求客户端
            OkHttpClient client = new OkHttpClient();
            //准备url
            String url = Constants.HttpUrl.BASEURL + interfaceKey();
            Map<String, Object> paramsMap = getKeyMap(index);
            String urlParamsByMap = HttpUtils.getUrlParamsByMap(paramsMap);
            url += urlParamsByMap;
//            Log.v("ian", "url:"+url);
            //创建请求
            Request request = new Request.Builder().get().url(url).build();
            //执行请求
            Response response = client.newCall(request).execute();
            //获取数据
            if (response.isSuccessful()) {//判断响应成功
                String jsonString = response.body().string();
                T t = parseJson(jsonString);

                //存到内存
                Log.v("ian", "存数据到内存");
                MyApplication app = (MyApplication) UIUtils.getContext();
                Map<String, String> keyMap = app.getKey();
                keyMap.put(generateKey(index),jsonString);
                //写到本地
                Log.v("ian", "存数据到本地");
                String dir = FileUtils.getDir(generateKey(index));
                File file = new File(dir, generateKey(index));
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(String.valueOf(new Date().getTime()));
                writer.newLine();;
                //去掉换行和空格
                if(jsonString.contains("\r\n")) {
                    jsonString.replaceAll("\r\n","");
                }
                if(jsonString.contains(" ")) {
                    jsonString.replaceAll(" ","");
                }
                writer.write(jsonString);
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
        return null;
    }
    //让子类去复写传入不同参数
    protected Map<String, Object> getKeyMap(int index) {
        Map<String, Object> paramsMap=new HashMap<>();
        paramsMap.put("index", String.valueOf(index));
        return paramsMap;
    }

    //解析数据,只有子类知道返回什么类型
    protected abstract T parseJson(String jsonString) throws JSONException;
    //只有子类知道请求key
    protected abstract String interfaceKey();
}
