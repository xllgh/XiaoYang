package com.xll.administrator.leftmenu;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xll.administrator.R;
import com.xll.administrator.utils.DataParser;
import com.xll.administrator.utils.GetDataListener;
import com.xll.administrator.utils.HttpUtils;
import com.xll.administrator.utils.RVAdapter;
import com.xll.administrator.view.ActionbarSetting;

import java.util.ArrayList;
import java.util.HashMap;

public class OutTotalActivity extends AppCompatActivity implements View.OnClickListener,GetDataListener{
    RecyclerView recyclerView;
    ArrayList<HashMap<String,String>> totalList=new ArrayList<>();
    RVAdapter adapter;
    String TAG="OutTotalActivity";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_total);
        initWork();
        getAllOutNews();
    }

    private void initWork(){
        ActionbarSetting setting=new ActionbarSetting(this);
        setting.setLeft(R.drawable.back,this);
        setting.setRight(R.drawable.picture, this);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        adapter=new RVAdapter(this,totalList,RVAdapter.fraTotal);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getAllOutNews(){
        dialog=ProgressDialog.show(this,null,getString(R.string.waitData),true,true);
        HttpUtils.volleyGet(HttpUtils.URL_GET_ALL_OUT,null,this,TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left:
                this.finish();
                break;
            case R.id.right:
                break;
        }

    }

    @Override
    public void onSuccess(String str) {
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
        totalList.clear();
        totalList.addAll(new DataParser().parseAlloutData(str));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String str) {
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
