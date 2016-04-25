package com.xll.administrator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xll.administrator.R;
import com.xll.administrator.flea.FleaBook;
import com.xll.administrator.flea.FleaOther;
import com.xll.administrator.flea.FleaRecyle;
import com.xll.administrator.utils.MyFragmentPagerAdapter;
import com.xll.administrator.utils.MyPagerAdapter;
import com.xll.administrator.view.TabCard;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/20.
 */
public class FragmentFleaMD  extends Fragment{
    private View rootView;
    private ViewPager viewPager,viewPager1;
    private ImageView iv1,iv2,iv3,iv4;
    private TabLayout tabLayout;
    private ArrayList<View> pageList = new ArrayList<>();
    private ArrayList<Fragment> fleaList=new ArrayList<>();
    private MyPagerAdapter pAdapter;
    private ImageView[] imgs;
    private MyFragmentPagerAdapter fAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_flea_md,null);
        initWork();
        return rootView;
    }

    private void findView(){
        viewPager= (ViewPager) rootView.findViewById(R.id.viewPager);
        viewPager1= (ViewPager) rootView.findViewById(R.id.viewPager1);
        iv1= (ImageView) rootView.findViewById(R.id.iv1);
        iv2= (ImageView) rootView.findViewById(R.id.iv2);
        iv3= (ImageView) rootView.findViewById(R.id.iv3);
        iv4= (ImageView) rootView.findViewById(R.id.iv4);
        imgs=new ImageView[]{iv1,iv2,iv3,iv4};
        tabLayout= (TabLayout) rootView.findViewById(R.id.tabLayout);

    }

    private void setTabLayout(){
        View tab1=new TabCard(getActivity()).setImg(R.drawable.se).setText(R.string.recycle);
        View tab2=new TabCard(getActivity()).setImg(R.drawable.se).setText(R.string.findbook);
        View tab4=new TabCard(getActivity()).setImg(R.drawable.se).setText(R.string.findother);
        tabLayout.addTab(tabLayout.newTab().setTag("1").setCustomView(tab1));
        tabLayout.addTab(tabLayout.newTab().setTag("2").setCustomView(tab2));
        tabLayout.addTab(tabLayout.newTab().setTag("3").setCustomView(tab4));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int page=Integer.valueOf((String)tab.getTag());
                viewPager1.setCurrentItem(page-1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void upperPager(){
        pageList.add(generateImg(R.drawable.iv1));
        pageList.add(generateImg(R.drawable.iv2));
        pageList.add(generateImg(R.drawable.iv3));
        pageList.add(generateImg(R.drawable.iv4));
        pAdapter = new MyPagerAdapter(getActivity(), pageList);
        viewPager.setAdapter(pAdapter);
        showIndicator(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void bottomPager(){
        fleaList.add(new FleaRecyle());
        fleaList.add(FleaBook.getInstance());
        fleaList.add(FleaOther.getInstance());
        fAdapter=new MyFragmentPagerAdapter(getFragmentManager(),fleaList);
        viewPager1.setAdapter(fAdapter);
        viewPager1.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void initWork(){
        findView();
        setTabLayout();
        upperPager();
        bottomPager();
    }

    private ImageView generateImg(int resId){
        ImageView iv=new ImageView(getActivity());
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
        iv.setBackgroundResource(resId);
        return iv;
    }

    private void showIndicator(int which){
        for(int i=0;i<4;i++){
            if(which==i){
                imgs[i].setSelected(true);
            }else {
                imgs[i].setSelected(false);
            }
        }

    }
}
