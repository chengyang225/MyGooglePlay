package com.testdemo.chanian.mygoogleplay.bean;

import java.util.List;

/**
 * Created by ChanIan on 16/5/28.
 */
public class ItemInfoBean {

    public int id;             // id : 1525489
    public String name;        // name : 小码哥程序员
    public String packageName; // packageName : com.m520it.www
    public String iconUrl;     // iconUrl : app/com.m520it.www/icon.jpg
    public float stars;          // stars : 5
    public long size;           // size : 91767
    public String downloadUrl; // downloadUrl : app/com.m520it.www/com.m520it.www.apk
    public String des;         // des : 产品介绍：google市场app测试。


    public String downloadNum;  //downloadNum : 40万+
    public String version;//version : 1.1.0605.0
    public String date;//date : 2015-06-10
    public String author;//author : 王维波
    public List<String> screen;//screen : ["app/com.m520it.www/screen0.jpg","app/com.m520it.www/screen1.jpg","app/com.m520it.www/screen2.jpg","app/com.m520it.www/screen3.jpg","app/com.m520it.www/screen4.jpg"]
    public List<SafeBean> safe;//safe : [{"safeUrl":"app/com.m520it.www/safeIcon0.jpg","safeDesUrl":"app/com.m520it.www/safeDesUrl0.jpg","safeDes":"已通过安智市场安全检测，请放心使用","safeDesColor":0},{"safeUrl":"app/com.m520it.www/safeIcon1.jpg","safeDesUrl":"app/com.m520it.www/safeDesUrl1.jpg","safeDes":"无任何形式的广告","safeDesColor":0}]

    public static class SafeBean {
        public String safeUrl;//safeUrl : app/com.m520it.www/safeIcon0.jpg
        public String safeDesUrl;//safeDesUrl : app/com.m520it.www/safeDesUrl0.jpg
        public String safeDes;//safeDes : 已通过安智市场安全检测，请放心使用
        public int safeDesColor;//safeDesColor : 0


    }

    @Override
    public String toString() {
        return "ItemInfoBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", stars=" + stars +
                ", size=" + size +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", des='" + des + '\'' +
                ", downloadNum='" + downloadNum + '\'' +
                ", version='" + version + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", screen=" + screen +
                ", safe=" + safe +
                '}';
    }
}
