package com.sp.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sp.customviewdemo.R;

import java.util.Calendar;

/**
 * Created by songpeng on 2017/8/31.
 * <p>
 * Date 2017/8/31
 * <p>
 * Description 画表
 */

public class DrawWatch extends View{
    private String TAG = DrawWatch.class.getSimpleName();
    private int mWidth;

    private int mHeight;

    private Paint paint;

    private float r;

    private Canvas canvas;

    public DrawWatch(Context context) {
        this(context,null);
    }

    public DrawWatch(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawWatch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawCircle(canvas);
        drawPointerSecond(canvas);
        postInvalidateDelayed(1000);
    }

    private void drawCircle(Canvas canvas) {
        r = (float) (Math.min(mWidth, mHeight)/2 *0.8);
        canvas.translate(mWidth/2, mHeight /2);//移动画布中心点到屏幕中央
        paint.setColor(getResources().getColor(R.color.brown));//设置画笔的颜色
        canvas.drawRect(-r-20,-r-20,r+20,r+20,paint);//画一个正方形，就是外边褐色的框
        paint.setColor(getResources().getColor(R.color.black));
        paint.setStyle(Paint.Style.FILL);//设置画笔是实心的，画出表盘的圆来
        paint.setColor(getResources().getColor(R.color.white));
        canvas.drawCircle(0,0,r+5,paint);

        paint.setStyle(Paint.Style.STROKE);//设置画笔为描边
        paint.setColor(getResources().getColor(R.color.black));
        paint.setStrokeWidth(1);
        paint.setTextSize(15);
        canvas.save();//保存画布

        int lineWidth;
        for (int i = 0; i < 60; i++) {//现在就是要画出表盘的那些个刻度
            if (i % 5 == 0) {//是画整点的刻度的，比如12，1，2，3点
                paint.setStrokeWidth(3);//设置一下画笔的宽度
                lineWidth = 20;//设置整点刻度的线长度
                String text = String.valueOf(12 - i/5);//计算当前要填写的数字
                Rect textBound = new Rect();
                paint.getTextBounds(text, 0, text.length(), textBound);
                canvas.save();
                paint.setStrokeWidth(2);
                paint.setTextSize(25);
                paint.setStyle(Paint.Style.FILL);
                canvas.translate(0,10+lineWidth-r+(textBound.bottom - textBound.top));
                canvas.rotate(6 * i);//做一下画布的旋转，这样数字的显示就是正常的哩
                canvas.drawText(text, -(textBound.right - textBound.left) / 2,10, paint);
                canvas.restore();
            }else{//画每一分的小刻度的
                paint.setStrokeWidth(1);
                lineWidth = 10;
                paint.setStrokeWidth(1);
            }
            canvas.drawLine(0,-r+10,0,10+lineWidth-r,paint);
            canvas.rotate(-360/60);//画布逆时针赚
        }
        canvas.restore();
    }


    /**
     * 表的指针
     * @param canvas 第一版使用的是60分的，每一秒6度
     */
    private void drawPointer(Canvas canvas){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); //时
        int minute = calendar.get(Calendar.MINUTE); //分
        int second = calendar.get(Calendar.SECOND); //秒
        int angleHour = ((hour % 60) * 360 / 12); //时针转过的角度
        int angleMinute = minute * 360 / 60; //分针转过的角度
        int angleSecond = second * 360 / 60; //秒针转过的角度
        paint.setStyle(Paint.Style.FILL);


        canvas.save();//分针转动
        paint.setColor(getResources().getColor(R.color.black));
        canvas.rotate(angleMinute);//旋转画布 angleMinute的角度
        RectF rectFMinute = new RectF(-4, -r*2/3,  4, 30);
        canvas.drawRoundRect(rectFMinute, 10, 10, paint);
        canvas.restore();




        int hourMinute = angleMinute / (6 * 12);//时针转动
        if (hourMinute > 0) {//主要实现让时针12分钟转动一格，
            // 防止时针一小时转一大格不好看

            canvas.save();
            canvas.rotate(angleHour+hourMinute * 6);
            RectF rectFHour1 = new RectF(-6, -r/2,  6, 30);
            canvas.drawRoundRect(rectFHour1, 10, 10, paint);
            canvas.restore();
        }else{
            canvas.save();
            canvas.rotate(angleHour);
            RectF rectFHour = new RectF(-6, -r/2,  6, 30);
            canvas.drawRoundRect(rectFHour, 10, 10, paint);
            canvas.restore();
        }


        canvas.save();//秒针转动
        paint.setColor(getResources().getColor(R.color.red));
        canvas.drawCircle(0,0,15,paint);
        canvas.rotate(angleSecond);
        RectF rectFSecond = new RectF(-3, -r+5,  3, 30);
        canvas.drawRoundRect(rectFSecond, 10, 10, paint);
        canvas.restore();
    }

    /**
     * 表的指针
     * @param canvas 第二版使用的是每一分下边再细分6度，这样就可以让指针变化的范围更小
     */
    private void drawPointerSecond(Canvas canvas){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); //时
        int minute = calendar.get(Calendar.MINUTE); //分
        int second = calendar.get(Calendar.SECOND); //秒
        int angleHour = ((hour % 60) * 360 / 12); //时针转过的角度
        int angleMinute = minute * 360 / 60; //分针转过的角度
        int angleSecond = second * 360 / 60; //秒针转过的角度
        paint.setStyle(Paint.Style.FILL);


        int minuteSecond = angleSecond/(6*10);
        if (minuteSecond>0) {

            canvas.save();//分针转动
            paint.setColor(getResources().getColor(R.color.black));
            canvas.rotate(angleMinute + minuteSecond);//旋转画布 angleMinute的角度
            RectF rectFMinute = new RectF(-4, -r*2/3,  4, 30);
            canvas.drawRoundRect(rectFMinute, 10, 10, paint);
            canvas.restore();

        }else{

            canvas.save();//分针转动
            paint.setColor(getResources().getColor(R.color.black));
            canvas.rotate(angleMinute);//旋转画布 angleMinute的角度
            RectF rectFMinute = new RectF(-4, -r*2/3,  4, 30);
            canvas.drawRoundRect(rectFMinute, 10, 10, paint);
            canvas.restore();

        }


        int hourMinute = angleMinute / (6 * 12);//时针转动
        if (hourMinute > 0) {//主要实现让时针12分钟转动一格，
            // 防止时针一小时转一大格不好看
            canvas.save();
            canvas.rotate(angleHour+hourMinute * 6);
            RectF rectFHour1 = new RectF(-6, -r/2,  6, 30);
            canvas.drawRoundRect(rectFHour1, 10, 10, paint);
            canvas.restore();
        }else{
            canvas.save();
            canvas.rotate(angleHour);
            RectF rectFHour = new RectF(-6, -r/2,  6, 30);
            canvas.drawRoundRect(rectFHour, 10, 10, paint);
            canvas.restore();
        }


        canvas.save();//秒针转动
        paint.setColor(getResources().getColor(R.color.red));
        canvas.drawCircle(0,0,15,paint);
        canvas.rotate(angleSecond);
        RectF rectFSecond = new RectF(-3, -r+5,  3, 30);
        canvas.drawRoundRect(rectFSecond, 10, 10, paint);
        canvas.restore();
    }


















}
