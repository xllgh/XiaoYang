package com.xll.administrator.ui;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;
import com.xll.administrator.R;
import com.xll.administrator.user.LoginActivity;
import com.xll.administrator.utils.ParamContract;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    TextView text1,text2;
    SharedPreferences share;
    int uid=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        animation();
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                nextStep();
            }
        };

        timer.schedule(task,2000);

    }


    public void init(){
        share=getSharedPreferences(ParamContract.SHARE_NAME,MODE_PRIVATE);
        uid=share.getInt("uid",-1);
        text1= (TextView) findViewById(R.id.text1);
        text2= (TextView) findViewById(R.id.text2);
    }

    public void animation(){
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int mHeight=dm.heightPixels/2;
        int mWidth=dm.widthPixels/2;

        AnimatorSet set1=(AnimatorSet)  AnimatorInflater.loadAnimator(this,R.animator.ani_appname);
        set1.setDuration(3000);
        set1.setTarget(text1);
        set1.start();

        ValueAnimator animatorY= ValueAnimator.ofFloat(0,mHeight);
        ValueAnimator animatorX=ValueAnimator.ofFloat(0,mWidth);


       /* animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                text1.setTranslationX((float) animation.getAnimatedValue());
            }
        });*/
        animatorX.setDuration(300);
        animatorX.setInterpolator(new BounceInterpolator());
        animatorX.start();
    }


    private void nextStep(){
        if(uid>0){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
        WelcomeActivity.this.finish();
    }



       /*
       1、通过资源文件res/animator（ViewAnimation中是在res/anim）,在Android3.1中使用
          -- 不同的XML标签对应不同的animation类
              <animator>-----ValueAnimation
              <objectAnimator>----ObjectAnimator
              <set>------AnimatorSet

          -- AnimatorInflater.loadAnimation(context,R.anim.property_animator);

        2、通过代码
          --animator的类：ObjectAnimator,ValueAnimator,AnimatorSet
             (1)valueAnimator:通过工厂类来实例化一个对象 ofInt()、ofFloat()、ofObject()、ofArgb()、ofPropertyValuesHolder();
                   想要实现效果valueAnimation.addUpdateListener()这一步非常的重要,在AnimatorUpdateListener中为对象设置属性。
                   ps:解析ofPropertyValuesHolder()工厂类 ，PropertyValuesHolder通过ofFloat()、ofInt()、ofKeyframe()、ofObject()工厂类来实例化
                      keyframe也是通过工厂类来实例化的



             (2)objectAnimator:也可以通过工厂类来实例化对象，与上面的类不同的地方是，在工厂类中为设置对象
                  ps:解析ofPropertyValuesHolder()工厂类 ，PropertyValuesHolder通过ofFloat()、ofInt()、ofKeyframe()、ofObject()工厂类来实例化
                      keyframe也是通过工厂类来实例化的
             (3)AnimatorSet:通过new的方法来实例化对象
                play()  playTogether playSequentially   play.with.before,after




*/


}
