package com.xll.administrator.flea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xll.administrator.R;

/**
 * Created by Administrator on 2016/4/20.
 */
public abstract class FleaParent extends Fragment {

    public View rootView;
    public RecyclerView recyclerView;
    private static FleaParent fleaParent;

    public FleaParent(){

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("<<<<<<<<<<<<<<<<","parent onCreate");
        rootView=inflater.inflate(R.layout.flea_parent,null);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.recyclerView);
        return rootView;
    }
}
