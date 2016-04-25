package com.xll.administrator.flea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xll.administrator.R;
import com.xll.administrator.utils.RVAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/20.
 */

public class FleaRecyle extends FleaParent {
    ArrayList<HashMap<String, String>> recylerList = new ArrayList<>();
    RVAdapter adapter;
    View view;
    RecyclerView recyclerView;
    static FleaRecyle fleaRecyle;


    public static FleaRecyle getInstance() {
        if (fleaRecyle == null) {
            fleaRecyle = new FleaRecyle();
        }
        return fleaRecyle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    private void info() {
        recylerList.clear();
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("name", "教科书回收小站");
        map1.put("place", "汇贤楼208");
        map1.put("worktime", "周一到周五 9:00-18:00");
        map1.put("detail", "回收各专业的教科书，考研辅导书，托福雅思等等");
        map1.put("connect", "1234567890");

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("name", "课外书回收小站");
        map2.put("place", "汇贤楼308");
        map2.put("worktime", "周一到周五 9:00-18:00");
        map2.put("detail", "你不需要的各种课外书，交给我们，让它物尽其用");
        map2.put("connect", "1234567890");

        HashMap<String, String> map3 = new HashMap<>();
        map3.put("name", "衣加衣");
        map3.put("place", "院青协");
        map3.put("worktime", "周一到周五 9:00-18:00");
        map3.put("detail", "整理你的衣柜，把你不需要的衣服带给需要的人");
        map3.put("connect", "1234567890");

        HashMap<String, String> map4 = new HashMap<>();
        map4.put("name", "可回收物品");
        map4.put("place", "汇贤楼308");
        map4.put("worktime", "周一到周五 9:00-18:00");
        map4.put("detail", "废弃的塑料瓶，电池等等");
        map4.put("connect", "1234567890");

        HashMap<String, String> map5 = new HashMap<>();
        map5.put("name", "教科书回收小站");
        map5.put("place", "汇贤楼208");
        map5.put("worktime", "周一到周五 9:00-18:00");
        map5.put("detail", "回收各专业的教科书，考研辅导书，托福雅思等等");
        map5.put("connect", "1234567890");

        HashMap<String, String> map6 = new HashMap<>();
        map6.put("name", "课外书回收小站");
        map6.put("place", "汇贤楼308");
        map6.put("worktime", "周一到周五 9:00-18:00");
        map6.put("detail", "你不需要的各种课外书，交给我们，让它物尽其用");
        map6.put("connect", "1234567890");

        HashMap<String, String> map7 = new HashMap<>();
        map7.put("name", "衣加衣");
        map7.put("place", "院青协");
        map7.put("worktime", "周一到周五 9:00-18:00");
        map7.put("detail", "整理你的衣柜，把你不需要的衣服带给需要的人");
        map7.put("connect", "1234567890");

        HashMap<String, String> map8 = new HashMap<>();
        map8.put("name", "可回收物品");
        map8.put("place", "汇贤楼308");
        map8.put("worktime", "周一到周五 9:00-18:00");
        map8.put("detail", "废弃的塑料瓶，电池等等");
        map8.put("connect", "1234567890");

        recylerList.add(map1);
        recylerList.add(map2);
        recylerList.add(map3);
        recylerList.add(map4);
        recylerList.add(map5);
        recylerList.add(map6);
        recylerList.add(map7);
        recylerList.add(map8);

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("<<<<<<<<<<<<<<<<<<<<<<", "FleaRecyle onResume");
        info();
        adapter = new RVAdapter(getActivity(), recylerList, RVAdapter.fraRecyler);
        super.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        super.recyclerView.setAdapter(adapter);

    }
}
