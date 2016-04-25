package com.xll.administrator.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.xll.administrator.R;
import com.xll.administrator.leftmenu.PublishInfoActivity;
import com.xll.administrator.leftmenu.OutTotalActivity;
import com.xll.administrator.view.ActionbarSetting;
import com.xll.administrator.utils.OnFragmentInteractionListener;
import com.xll.administrator.utils.ParamContract;
import com.xll.administrator.view.TabCard;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener{


    private FragmentTabHost fTabHost;
    private NavigationView navigationView;
    public ActionbarSetting actionbar;
    public DrawerLayout drawer;
    private TextView exit;
    private SharedPreferences share;
    private View headerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init1();
    }

    private void init1(){


        share=getSharedPreferences(ParamContract.SHARE_NAME,MODE_PRIVATE);
        drawer= (DrawerLayout) this.findViewById(R.id.drawer);
        navigationView= (NavigationView) this.findViewById(R.id.nav);
        headerView=navigationView.getHeaderView(0);
        exit= (TextView) headerView.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        final TabCard tabCards[]=new TabCard[4];
        tabCards[0]=new TabCard(this);
        tabCards[1]=new TabCard(this);
        tabCards[2]=new TabCard(this);
        tabCards[3]=new TabCard(this);
        View[] views=new View[4];

        views[0]= tabCards[0].setImg(R.drawable.selector_tab1).setText(R.string.flea);
        views[1]= tabCards[1].setImg(R.drawable.selector_tab2).setText(R.string.activityNews);
        views[2]= tabCards[2].setImg(R.drawable.selector_tab3).setText(R.string.notice);
        views[3]= tabCards[3].setImg(R.drawable.selector_tab4).setText(R.string.playTogether);

        fTabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        fTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        final String[] strTab=new String[]{"flea","news","notice","play"};
        final int[] resId=new int[]{R.string.flea,R.string.activityNews,R.string.notice,R.string.playTogether};

        fTabHost.addTab(fTabHost.newTabSpec(strTab[0]).setIndicator(views[0]), FragmentFleaMD.class, null);
        fTabHost.addTab(fTabHost.newTabSpec(strTab[1]).setIndicator(views[1]),FragmentActivityNews.class,null);
        fTabHost.addTab(fTabHost.newTabSpec(strTab[2]).setIndicator(views[2]),FragmentNotice.class,null);
        fTabHost.addTab(fTabHost.newTabSpec(strTab[3]).setIndicator(views[3]), FragmentPlayTogether.class, null);

        fTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.e("tag", tabId);

                for (int i = 0; i < 4; i++) {
                    if (tabId.equals(strTab[i])) {
                        actionbar.setTitle(resId[i]);
                        tabCards[i].setImgSelect(true);
                    } else {
                        tabCards[i].setImgSelect(false);
                    }
                }
            }
        });
        actionbar=new ActionbarSetting(MainActivity.this);
        actionbar.setLeft(R.drawable.left,listener);
        actionbar.setRight(R.drawable.right,null);
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            drawer.openDrawer(navigationView);
        }
    };

    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.e("<<<<<<<<<<<<<<<<<<<<<<<","onAttachFragment");
        super.onAttachFragment(fragment);
        if(fragment instanceof FragmentActivityNews){
            actionbar.setTitle(R.string.activityNews);
        }else if(fragment instanceof FragmentFlea){
            actionbar.setTitle(R.string.flea);
        }else if(fragment instanceof FragmentPlayTogether){
            actionbar.setTitle(R.string.playTogether);
        }else if(fragment instanceof FragmentNotice){
            actionbar.setTitle(R.string.notice);
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.find_mate:
                Intent i1=new Intent(this, PublishInfoActivity.class);
                i1.putExtra("titleId",R.string.playmate);
                startActivity(i1);
                break;

            case R.id.sale:
                Intent i2=new Intent(this, PublishInfoActivity.class);
                i2.putExtra("titleId",R.string.sale);
                startActivity(i2);
                break;

            case R.id.broadcast:
                Intent i3=new Intent(this, PublishInfoActivity.class);
                i3.putExtra("titleId",R.string.broadcast);
                startActivity(i3);
                break;


            case R.id.create:
                Intent i4=new Intent(this, OutTotalActivity.class);
                i4.putExtra("titleId",R.string.out);
                startActivity(i4);
                break;
            default:
                break;


        }
        return false;
    }

    private void exit(){
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        ab.setTitle(R.string.prompt);
        ab.setMessage(R.string.sure_exit);
        ab.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                share.edit().remove("name").commit();
                share.edit().remove("uid").commit();
                MainActivity.this.finish();
            }
        });
        ab.setNegativeButton(R.string.cancel, null);
        ab.show();
    }


}
