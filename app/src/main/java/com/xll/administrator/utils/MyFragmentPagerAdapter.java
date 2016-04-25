package com.xll.administrator.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xll.administrator.flea.FleaParent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/20.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragments;

  public  MyFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments){
      super(fm);
      this.fragments=fragments;
  }

    @Override
    public Fragment getItem(int position) {
      return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
