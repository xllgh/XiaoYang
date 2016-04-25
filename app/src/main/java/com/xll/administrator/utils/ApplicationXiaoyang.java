package com.xll.administrator.utils;

import android.app.Application;
import android.app.DownloadManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/4/15.
 */
public class ApplicationXiaoyang extends Application {

    private static ApplicationXiaoyang mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public static final String TAG=ApplicationXiaoyang.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public static synchronized ApplicationXiaoyang getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null){
            mRequestQueue= Volley.newRequestQueue(getInstance());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        getRequestQueue();
        if(mImageLoader==null){
            mImageLoader=new ImageLoader(this.mRequestQueue,new LruBitmapCache());
        }
        return this.mImageLoader;
    }
    public void addToRequestQueue(Request request,String tag){
        request.setTag(TextUtils.isEmpty(tag)?TAG:tag);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag){
        if(mRequestQueue!=null){
            mRequestQueue.cancelAll(tag);
        }
    }

}
