package com.xll.administrator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.xll.administrator.R;


public class HangBall extends View {
    private int defColor=0x99999999;
    private float defLength=20f;
    private String defText="HallBall";
    int lineColor=defColor;
    float lineLenght=defLength;
    Paint linePaint;

    int textColor=defColor;
    String text=defText;
    Paint textPaint;

    int ballColor=defColor;
    Paint ballPaint;

    float radius;
    private float defRedius=20;



    public HangBall(Context context) {
        super(context);
        init();
    }

    public HangBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context,attrs);
        init();
    }

    public HangBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context,attrs);
        init();
    }

    private void getAttributes(Context context,AttributeSet attrs){
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.hangBall);
        lineColor=typedArray.getInteger(R.styleable.hangBall_lineColor, defColor);
        ballColor=typedArray.getInteger(R.styleable.hangBall_ballColor, defColor);
        textColor=typedArray.getInteger(R.styleable.hangBall_textColor, defColor);

        lineLenght=typedArray.getDimension(R.styleable.hangBall_lineLength, defLength);
        radius=typedArray.getDimension(R.styleable.hangBall_ballRadius,defRedius);

        text=typedArray.getString(R.styleable.hangBall_text);
        typedArray.recycle();
    }

    private void  init(){
        linePaint=new Paint();
        linePaint.setColor(defColor);
        linePaint.setStrokeWidth(2);

        textPaint=new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setStrokeWidth(5);

        ballPaint=new Paint();
        ballPaint.setColor(ballColor);
        ballPaint.setStrokeWidth(3);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx=canvas.getWidth()/2;
        canvas.drawLine(cx, 0, cx,lineLenght , linePaint);
        RectF rectF=new RectF(cx-radius,lineLenght,cx+radius,lineLenght+2*radius);
        canvas.drawOval(rectF,ballPaint);
        canvas.drawText(text,rectF.left,rectF.centerY(),textPaint);

    }
}
