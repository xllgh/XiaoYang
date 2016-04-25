package com.xll.administrator.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/15.
 */
public class DataParser {


    public ArrayList<HashMap<String,String>> parsePlaymateData(String str){
        ArrayList<HashMap<String,String>> lists=new ArrayList<>();
        try {
            JSONArray array=new JSONObject(str).getJSONArray("lists");
            for(int i=0;i<array.length();i++){
                JSONObject temp=array.getJSONObject(i);
                HashMap<String,String> map=new HashMap<>();
                map.put("uid",temp.getString("uid"));
                map.put("name",temp.getString("name"));
                map.put("connect",temp.getString("connect"));
                map.put("detail",temp.getString("detail"));
                map.put("ptime",temp.getString("ptime"));
                map.put("img",temp.getString("img"));
                map.put("briefIntro",temp.optString("briefintro"));
                map.put("gendar",temp.getString("gendar"));
                map.put("place",temp.getString("place"));
                map.put("time",temp.getString("time"));
                lists.add(map);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }


    public ArrayList<HashMap<String,String>> parseNoticeData(String str){

        ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String, String>>();
        try {
            JSONArray array=new JSONObject(str).getJSONArray("lists");
            for(int i=0;i<array.length();i++){
                JSONObject temp=array.getJSONObject(i);
                HashMap<String,String> map=new HashMap<>();
                map.put("tel",temp.optString("tel"));
                map.put("name",temp.optString("name"));
                map.put("img",temp.optString("img"));
                map.put("detail",temp.optString("detail"));
                map.put("ptime",temp.optString("ptime"));
                map.put("startime",temp.optString("startime"));
                map.put("endtime",temp.optString("endtime"));
                map.put("title",temp.optString("title"));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;

    }

    public ArrayList<HashMap<String,String>> parseActivityNewsData(String str){
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        try {
            JSONObject object=new JSONObject(str);
            JSONArray array=object.getJSONArray("lists");
            for(int i=0;i<array.length();i++){
                JSONObject temp=array.getJSONObject(i);
                HashMap<String,String> map=new HashMap<>();
                map.put("name",temp.getString("aname"));
                map.put("host",temp.getString("ahost"));
                map.put("detail",temp.getString("adetail"));
                map.put("ptime",temp.getString("ptime"));
                map.put("startime",temp.getString("startime"));
                map.put("endtime",temp.getString("endtime"));
                map.put("img",temp.getString("aimg"));
                map.put("category",temp.getString("category"));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<HashMap<String,String>> parseFleaData(String str){
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        try {
            JSONArray array=new JSONObject(str).getJSONArray("lists");
            for(int i=0;i<array.length();i++){
                JSONObject temp=array.getJSONObject(i);
                HashMap<String,String> map=new HashMap<>();
                map.put("name",temp.getString("name"));
                map.put("tel",temp.getString("tel"));
                map.put("category",temp.getString("category"));
                map.put("img",temp.optString("img", ""));
                map.put("detail",temp.getString("detail"));
                map.put("img1",temp.optString("img1", ""));
                map.put("img2",temp.optString("img2", ""));
                map.put("img3",temp.optString("img3", ""));
                map.put("title",temp.optString("title"));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public HashMap<String,String> parseFindMateData(String str){
        HashMap<String,String> map=new HashMap<>();
        try {
            JSONObject object=new JSONObject(str);
            map.put("code",object.getString("code"));
            map.put("msg",object.getString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
      return map;
    }


    public ArrayList<HashMap<String,String>> parseAlloutData(String str){
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        try {
            JSONObject object=new JSONObject(str);
            JSONArray array=object.getJSONArray("lists");
            for(int i=0;i<array.length();i++){
                JSONObject temp=array.getJSONObject(i);
                HashMap<String,String> map=new HashMap<>();
                map.put("ptime",temp.getString("ptime"));
                map.put("detail",temp.getString("adetail"));
                map.put("img",temp.getString("aimg"));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
