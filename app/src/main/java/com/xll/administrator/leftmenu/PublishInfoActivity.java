package com.xll.administrator.leftmenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.xll.administrator.R;
import com.xll.administrator.utils.DataParser;
import com.xll.administrator.utils.GetDataListener;
import com.xll.administrator.utils.HttpUtils;
import com.xll.administrator.utils.ParamContract;
import com.xll.administrator.view.ActionbarSetting;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class PublishInfoActivity extends AppCompatActivity implements View.OnClickListener,GetDataListener,AdapterView.OnItemSelectedListener{
    TextInputEditText briefIntro,time,place,connect;
    EditText intro;
    Button btnSure;
    SharedPreferences share;
    private ActionbarSetting setting ;
    private ImageView right;
    private ImageView selectImage;
    private static final int request_camera=1;
    private static final int request_gallery=2;
    private static final int request_crop=3;
    private String pictureName;
    private String TAG="PublishInfoActivity";
    ProgressDialog pdialog;
    private LinearLayout spinnerLayout;
    private int TitleId;
    private Spinner spinner;
    String strCategory;
    String[] array;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_find_mate);
        init();
    }

    private void custom(){
        TitleId=getIntent().getIntExtra("titleId",R.string.app_name);
        setting.setTitle(TitleId);

        switch (TitleId){
            case R.string.playmate:
                break;
            case R.string.sale:
                place.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.VISIBLE);
                break;
            case R.string.broadcast:
                place.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                break;
            case R.string.out:
                break;
        }
    }


    private void init(){
        array=getResources().getStringArray(R.array.categorys);
        selectImage= (ImageView) findViewById(R.id.img);
        setting=new ActionbarSetting(this);
        setting.setLeft(R.drawable.back, this);
        setting.setRight(R.drawable.picture, this);

        share=getSharedPreferences(ParamContract.APP_NAME,MODE_PRIVATE);
        briefIntro= (TextInputEditText) findViewById(R.id.briefIntro);
        time= (TextInputEditText) findViewById(R.id.time);
        place= (TextInputEditText) findViewById(R.id.place);
        intro= (EditText) findViewById(R.id.introduce);
        btnSure= (Button) findViewById(R.id.sure);
        right= (ImageView) findViewById(R.id.right);
        connect= (TextInputEditText) findViewById(R.id.connect);
        btnSure.setOnClickListener(this);
        spinner= (Spinner) findViewById(R.id.category);
        spinnerLayout= (LinearLayout) findViewById(R.id.spinnerLayout);
        spinner.setOnItemSelectedListener(this);
        custom();
    }

    private void publishNews(){
        if(pdialog==null){
            pdialog=ProgressDialog.show(PublishInfoActivity.this,null,getString(R.string.waitData),true);
        }else{
            pdialog.show();
        }
        String uid=share.getString("uid","9999");
        String name=share.getString("name","xll");
        String strGendar=share.getString("gendar","female");
        String strTheme=briefIntro.getText().toString();
        String strIntro=intro.getText().toString();
        String strConnect=connect.getText().toString();
        String strTime=time.getText().toString();
        String strPlace=place.getText().toString();
        HashMap<String,String> map=new HashMap<>();

        map.put("uid",uid);
        map.put("name",name);
        map.put("gendar",strGendar);
        map.put("briefIntro",strTheme);
        map.put("detail", strIntro);
        map.put("connect", strConnect);
        map.put("ptime",new SimpleDateFormat(getString(R.string.timestamp1)).format(new Date()));
        map.put("time", TextUtils.isEmpty(strTime) ? "暂无" : strTime);
        map.put("place", TextUtils.isEmpty(strPlace) ? "暂无" : strPlace);
        if(spinnerLayout.getVisibility()==View.VISIBLE){
            map.put("category",TextUtils.isEmpty(strCategory)?"暂无":strCategory);
        }

        if(TextUtils.isEmpty(pictureName)){
            Toast.makeText(PublishInfoActivity.this,"请上传图片",Toast.LENGTH_SHORT).show();
        }else {
            map.put("img", pictureName+".png");
            pictureName=null;
        }
        switch (TitleId){
            case R.string.playmate:
                publishMateNews(map);
                break;
            case R.string.sale:
                publishSale(map);
                break;
            case R.string.broadcast:
                publishBroadcast(map);
                break;
           default:
               break;
        }


    }

    private void publishMateNews(HashMap<String,String > map){
         HttpUtils.volleyGet(HttpUtils.URL_PUT_PLAY,map,this,TAG);
    }
    private void publishBroadcast(HashMap<String,String> map){
        HttpUtils.volleyGet(HttpUtils.URL_PUT_NOTICE,map,this,TAG);
    }
    private void publishSale(HashMap<String,String> map){
        HttpUtils.volleyGet(HttpUtils.URL_PUT_TRADE,map,this,TAG);
    }

    private void selectPicture(){
        PopupMenu menu=new PopupMenu(this,right);
        menu.getMenuInflater().inflate(R.menu.picture_menu,menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.camera:
                        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,request_camera);
                        break;
                    case R.id.gallery:
                        Intent ii=new Intent(Intent.ACTION_GET_CONTENT);
                        ii.setType("image/*");
                        startActivityForResult(ii,request_gallery);
                        break;
                }
                return true;
            }
        });
        menu.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(data==null){
            return;
        }
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case request_camera:
                    Bundle bundle=data.getExtras();
                    if(bundle!=null){
                        Bitmap bm=bundle.getParcelable("data");
                       Uri uri= bitmapToFileUri(bm);
                        startImageZoom(uri);
                    }

                    break;
                case request_gallery:
                    Uri curi=data.getData();
                    Uri uri=  galleryToUri(curi);
                    startImageZoom(uri);
                    break;

                case request_crop:

                    Bundle result=data.getExtras();
                    if(result==null){
                        return;
                    }
                  final  Bitmap bitmap=result.getParcelable("data");
                    ImageView img=new ImageView(this);
                    img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    img.setImageBitmap(bitmap);
                    AlertDialog.Builder ab=new AlertDialog.Builder(this);
                    ab.setView(img);
                    ab.setTitle("确认要上传该图片吗");
                    ab.setNegativeButton(R.string.cancel, null);
                    ab.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            pdialog= ProgressDialog.show(PublishInfoActivity.this, null, getString(R.string.waitData), true, true);
                            new UploadThread(bitmap).start();

                        }
                    });
                    ab.show();
                    break;
            }
        }

    }

    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(pdialog!=null&&pdialog.isShowing()){

                pdialog.dismiss();
            }
            switch (msg.what){
                case 1:
                    Toast.makeText(PublishInfoActivity.this,"图片上传成功",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(PublishInfoActivity.this,"图片上传失败，请检查网络或者重试",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        strCategory=array[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        strCategory=array[0];
    }


    class UploadThread extends Thread{
        Bitmap bitmap;
        UploadThread(Bitmap bitmap){
            this.bitmap=bitmap;
        }
        @Override
        public void run() {
            super.run();
            String eee=new SimpleDateFormat(getString(R.string.timestamp)).format(new Date());

            String str=HttpUtils.pictureUpload(HttpUtils.URL_IMG, bitmap,eee );
            try {
                Log.e("tag",str);
                JSONObject object=new JSONObject(str);
                String code=object.getString("code");
                if(code.equals("0")){
                    pictureName=eee;
                    handler.obtainMessage(1,object.getString("msg")).sendToTarget();
                }else{
                    handler.obtainMessage(2,object.getString("msg")).sendToTarget();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private Uri bitmapToFileUri(Bitmap bm){
        File bmFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()+File.separator+"Xiaoyang");

        if(!bmFile.exists()){
            bmFile.mkdir();
        }
         File img=new File(bmFile.getAbsolutePath(),pictureName+".png");
        try {
            FileOutputStream fos=new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG,85,fos);
            bm.recycle();
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Uri galleryToUri(Uri uri){

        InputStream is=null;
        try {
            is=getContentResolver().openInputStream(uri);
            Bitmap bitmap= BitmapFactory.decodeStream(is);
            is.close();
            return  bitmapToFileUri(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private void startImageZoom(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,request_crop);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sure:
                publishNews();
                break;
            case R.id.left:
                this.finish();
                break;
            case R.id.right:
                selectPicture();
                break;
        }
    }

    @Override
    public void onSuccess(String str) {
        Log.e("<<<<<<<<<<<<<<<<<<<<<", str);
        if(pdialog!=null&&pdialog.isShowing()){
            pdialog.dismiss();
        }
        HashMap<String,String> map=new DataParser().parseFindMateData(str);
       if( map.get("code").equals("0")){
           try {
               Toast.makeText(PublishInfoActivity.this, URLDecoder.decode(map.get("msg"),"utf-8"),Toast.LENGTH_SHORT).show();
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }
       }else{
           Toast.makeText(PublishInfoActivity.this,"发布信息失败",Toast.LENGTH_SHORT).show();
       }


    }

    @Override
    public void onFailed(String str) {
        if(pdialog!=null&&pdialog.isShowing()){
            pdialog.dismiss();
        }
        Toast.makeText(PublishInfoActivity.this,"发布信息失败",Toast.LENGTH_SHORT).show();

    }
}
