package com.xll.administrator.view;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.test.ActivityInstrumentationTestCase2;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.xll.administrator.R;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PeriodtimeSetting implements View.OnClickListener{
    private TextInputEditText startime;
    private TextInputEditText endtime;
    private View view;
    private Activity activity;

   public  PeriodtimeSetting(Activity ativity){
       this.activity=ativity;
    view= LayoutInflater.from(ativity).inflate(R.layout.timeperiod,null);
       startime= (TextInputEditText) view.findViewById(R.id.startime);
       endtime= (TextInputEditText) view.findViewById(R.id.endtime);
       startime.setOnClickListener(this);
       endtime.setOnClickListener(this);
   }


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startime:

                break;
            case R.id.endtime:
                break;
        }

    }
}
