package com.xll.administrator.view;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xll.administrator.R;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ActionbarSetting {
    View view;
    ImageView left,right;
    TextView title;

    public ActionbarSetting(Context context){
        view= LayoutInflater.from(context).inflate(R.layout.actionbar,null);
        left= (ImageView) view.findViewById(R.id.left);
        right= (ImageView) view.findViewById(R.id.right);
        title= (TextView) view.findViewById(R.id.title);
    }

    public ActionbarSetting(Activity activity){
        left= (ImageView) activity.findViewById(R.id.left);
        right= (ImageView) activity.findViewById(R.id.right);
        title= (TextView) activity.findViewById(R.id.title);
    }

    public void setLeft(int id,View.OnClickListener listener){
        left.setBackgroundResource(id);
        if(listener!=null){
            left.setOnClickListener(listener);
        }
    }



    public void setLeft(Bitmap bitmap,View.OnClickListener listener){
        left.setImageBitmap(bitmap);
        if(listener!=null){
            left.setOnClickListener(listener);
        }
    }

    public void setRight(int id,View.OnClickListener listener){
        right.setBackgroundResource(id);
        if(listener!=null){
            right.setOnClickListener(listener);
        }
    }

    public void setTitle(int id){
        title.setText(id);
    }
}
