package com.xll.administrator.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/4/4.
 */
public class HttpUtils {
    Context context;
    //thinkPHP框架的URL默认是区分大小写的
    public static final String IP="172.26.63.4";
    public static final String URL_IMG="http://"+IP+":8088/SmallFlea/index.php?m=home&c=user&a=uploadImg";
    public static final String URL_BASE = "http://"+IP+":8088/SmallFlea/index.php?";
    public static final String URL_PUT_PLAY = URL_BASE + "m=Home&c=Playmate&a=publishPlaymateNews&";
    public static final String URL_GET_PLAY = URL_BASE + "m=Home&c=Playmate&a=getPlaymateNews";
    public static final String URL_PUT_ACTIVITY = URL_BASE + "m=Home&c=ActivityNews&a=publishActivityNews&";
    public static final String URL_GET_ACTIVITY = URL_BASE + "m=Home&c=ActivityNews&a=getActivityNews";
    public static final String URL_LOGIN = URL_BASE + "m=Home&c=User&a=login&";
    public static final String URL_PUT_NOTICE = URL_BASE + "m=Home&c=Broadcast&a=publishBroadcast&";
    public static final String URL_GET_NOTICE = URL_BASE + "m=Home&c=Broadcast&a=getAllBroadcast";
    public static final String URL_PUT_TRADE = URL_BASE + "m=Home&c=Trade&a=publishTradeNews&";
    public static final String URL_GET_TRADE = URL_BASE + "m=Home&c=Trade&a=getTradeNews&";
    public static final String URL_GET_ALL_TRADE = URL_BASE + "m=Home&c=Trade&a=getAllTradeNews";
    public static final String URL_GET_ALL_OUT=URL_BASE+"m=Home&c=Trade&a=getAllOut";


    public HttpUtils(Context context) {
        this.context = context;
    }

    public static String hucPost(HashMap<String, String> map, String strUrl) {
        PrintWriter pw = null;
        BufferedReader br = null;
        HttpURLConnection conn = null;
        StringBuilder requestBuilder = new StringBuilder();
        StringBuilder resultBuilder = new StringBuilder();
        try {
            URL url = new URL(strUrl);
            //1、拿到http连接,设置请求方式
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //2、配置请求，请求头
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);


            //3、发送请求正文
            Set<Map.Entry<String, String>> set = map.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                requestBuilder.append(URLEncoder.encode(entry.getKey(), "utf-8"))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "utf-8"))
                        .append("&");
            }
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded; charset=utf-8");//解决中文乱码问题
            conn.setRequestProperty("Content-Length", String.valueOf(requestBuilder.toString().length()));
            conn.setDoOutput(true);
            //ouputString ,dataOutPutStream,printWriter
            pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
            pw.write(requestBuilder.toString());
            pw.flush();
            pw.close();
            Log.e("TAG", "write ok");
            String temp;
            if (conn.getResponseCode() == 200) {
                conn.setDoInput(true);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((temp = br.readLine()) != null) {
                    resultBuilder.append(temp);
                }
                br.close();
                Log.e("TAG", "read ok");
            }
            return resultBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return null;

    }

    public void volleyPost() {

    }


    public static String hucGet(String strUrl, HashMap<String, String> map) {
        try {
            StringBuilder strBuilder = new StringBuilder();
            StringBuilder contentBuilder = new StringBuilder();
            strBuilder.append(strUrl);
            if (map != null) {
                Set<Map.Entry<String, String>> set = map.entrySet();
                Iterator<Map.Entry<String, String>> iterator = set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> tmp = iterator.next();
                    strBuilder.append(URLEncoder.encode(tmp.getKey(), "utf-8"))
                            .append("=")
                            .append(URLEncoder.encode("\"" + tmp.getValue() + "\"", "utf-8"))
                            .append("&");

                }

            }
            URL url = new URL(strBuilder.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(5 * 1000);
            connection.setReadTimeout(10 * 1000);
            connection.setRequestProperty("Content-Type", " application/x-www-form-urlencoded:charset=utf-8");

            connection.connect();

            if (connection.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String ss = null;
                while ((ss = br.readLine()) != null) {
                    contentBuilder.append(ss);
                }
                Log.e("<<<<<<<<<<<<<<<<<<", contentBuilder.toString());
                return contentBuilder.toString();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static void volleyGet(String url, HashMap<String, String> map, final GetDataListener listener, String tag) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url);
        if (map != null) {
            Set<Map.Entry<String, String>> set = map.entrySet();
            Iterator<Map.Entry<String, String>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    urlBuilder.append(URLEncoder.encode(entry.getKey(), "utf-8"))
                            .append("=")
                            .append(URLEncoder.encode( entry.getValue() , "utf-8"))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBuilder.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailed(error.toString());
            }
        });

        ApplicationXiaoyang.getInstance().addToRequestQueue(stringRequest, tag);

    }

    public static void imgVolleyShow(String strUrl,ImageView imageView,int resDefalut,int resFailed){
       ImageLoader imageLoader=ApplicationXiaoyang.getInstance().getImageLoader();
       imageLoader.get(strUrl,ImageLoader.getImageListener(imageView,resDefalut,resFailed));
    }



    public static ProgressDialog setProgressDialog(Context context) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setIndeterminate(true);
        return null;
    }


    public static String pictureUpload(String strUrl, Bitmap bitmap, String pictureName) {
        String end="\r\n";
        String twoHyphens="--";
        String boundary = "*****";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.setUseCaches(false);
            huc.setRequestMethod("POST");

            huc.setRequestProperty("Accept", "image/*");
            huc.setRequestProperty("Connection", "Keep-Alive");
            huc.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            huc.setRequestProperty("Charset", "UTF-8");
           // huc.setConnectTimeout(30 * 1000);
           // huc.connect();

            DataOutputStream dos=new DataOutputStream(huc.getOutputStream());

            //1、读取图片流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] ba = bos.toByteArray();

            dos.writeBytes(twoHyphens+boundary+end);
            dos.writeBytes("Content-Disposition:form-data;" +
                    "name=\"" + "myFile"  + "\";filename=\""+pictureName+"\"" + end);
            dos.writeBytes(end);
            InputStream isTemp=new ByteArrayInputStream(ba);
            int bufferSize=1024;
            byte[] buffer=new byte[bufferSize];
            int length=0;
            while ((length=isTemp.read(buffer,0,bufferSize))>0){
                dos.write(buffer,0,length);
            }
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();
            dos.close();

            StringBuilder resultBuilder = new StringBuilder();
            if (huc.getResponseCode() == 200) {
                String temp;
                BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
                while ((temp = br.readLine()) != null) {
                    resultBuilder.append(temp);
                }
                Log.e("<<<<<<<<<<<<<<<<<", resultBuilder.toString());
                return resultBuilder.toString();
            } else {
                Log.e("<<<<<<<<<<<<<<<<<<<", "erro");
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static class BitmapCache implements ImageLoader.ImageCache{

        private LruCache<String,Bitmap> mCache;

        public BitmapCache(){
            int maxSize=5*1024*1024;
            mCache=new LruCache<String,Bitmap>(maxSize){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                 return value.getRowBytes()*value.getHeight();
                }
            };
        }
        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }
}
