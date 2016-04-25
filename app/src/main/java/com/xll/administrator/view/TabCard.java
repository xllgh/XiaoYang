package com.xll.administrator.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xll.administrator.R;

/**
 * Created by Administrator on 2016/4/3.
 */
public class TabCard {

    View tab;
    ImageView img;
    TextView text;

    public  TabCard(Context context){
        tab= LayoutInflater.from(context).inflate(R.layout.item_tabcard,null);
        img= (ImageView) tab.findViewById(R.id.image);
        text= (TextView) tab.findViewById(R.id.text);
    }
    public TabCard setImg(int drawableId){
        img.setBackgroundResource(drawableId);
        return this;
    }
    public View setText(int stringId){
        text.setText(stringId);
        return tab;
    }

    public View getView(){
        return tab;
    }

    public void setImgSelect(boolean enable){
        img.setSelected(enable);
    }



}
