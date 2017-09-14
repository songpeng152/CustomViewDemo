package com.sp.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sp.customviewdemo.R;

/**
 * Created by songpeng on 2017/9/4.
 * <p>
 * Date 2017/9/4
 * <p>
 * Description 画雷达（蜘蛛网）
 */

public class DrawRadarMapView extends View {
    private String TAG = DrawRadarMapView.class.getSimpleName();
    private int mWidth;//屏幕的宽

    private int mHeight;//屏幕的高

    private static final int count = 6;//数据的个数

    private float angle = (float) (Math.PI * 2 / count);

    private Paint mRadarMapPaint;//画雷达的画笔

    private Paint mTitlePaint;//写title的画笔

    private Paint mAreaPaint;//画面积的画笔

    private float radius; //网格的最大半径

    private int mCenterX;//中心x坐标

    private int mCenterY;//中心y坐标

    private String titleArray[] = {"身份特质","履约能力","信用历史","人脉关系","行为偏好","承担风险"};

    private double value[] = {80,50,30,20,15,10};

    private int maxValue = 100;

    public DrawRadarMapView(Context context) {
        this(context, null);
    }

    public DrawRadarMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawRadarMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadarMapPaint = new Paint();
        mRadarMapPaint.setStyle(Paint.Style.STROKE);
        mRadarMapPaint.setStrokeWidth(1);
        mRadarMapPaint.setColor(getResources().getColor(R.color.black));
        mRadarMapPaint.setAntiAlias(true);

        mTitlePaint = new Paint();
        mTitlePaint.setStyle(Paint.Style.FILL);
        mTitlePaint.setStrokeWidth(2);
        mTitlePaint.setColor(getResources().getColor(R.color.black));
        mTitlePaint.setAntiAlias(true);

        mAreaPaint = new Paint();
        mAreaPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mAreaPaint.setStrokeWidth(2);
        mAreaPaint.setColor(getResources().getColor(R.color.red));
        mAreaPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;

        radius = Math.min(mWidth, mHeight) * 1 / 3;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRadarMap(canvas);
        drawArea(canvas);
    }

    private void drawRadarMap(Canvas canvas) {
        canvas.translate(mCenterX,mCenterY);
        // 先画count边形
        Path path = new Path();
        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        for (int i = 1; i < count; i++) {//中心点不用绘制
            float curR = r * i;//当前半径
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(curR, 0);
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x = (float) ( curR * Math.cos(angle * j));
                    float y = (float) ( curR * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }
            path.close();//闭合路径
            canvas.drawPath(path, mRadarMapPaint);
        }

        Path path1 = new Path();
        for (int i = 0; i < count/2; i++) {//连接雷达的线
//            float x = (float) (mCenterX + radius * Math.cos(angle * i));
//            float y = (float) (mCenterY + radius * Math.sin(angle * i));
            float x = (float) (radius * Math.cos(angle * i));
            float y = (float) (radius * Math.sin(angle * i));
            path1.moveTo(x,y);
            Rect textBound = new Rect();
            mTitlePaint.getTextBounds(titleArray[i],0,titleArray[i].length(),textBound);
            if( y < 0){
                y-=20;
            }else{
                y+=20;
            }
            if(x<0){
                x-=20;
            }else{
                x+=20;
            }
            canvas.drawText(titleArray[i],x/*-((textBound.left+textBound.right))/2*/,y,mTitlePaint);

            float x1 = (float) (-radius * Math.cos(angle * i));
            float y1 = (float) (-radius * Math.sin(angle * i));
            path1.lineTo(x1,y1);
            if(y1<0){
                y1-=20;
            }else{
                y1+=20;
            }
            if(x1<0){
                x1-=50;
            }else{
//                x1+=20;
            }
            canvas.drawText(titleArray[i+3],x1,y1,mTitlePaint);
        }
        canvas.drawPath(path1,mRadarMapPaint);
    }

    private void drawArea(Canvas canvas){
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            double percent = value[i]/maxValue;
            Log.d(TAG, "drawArea: percent "+percent);
            float x = (float) (radius * Math.cos(angle * i) * percent);
            float y = (float) (radius * Math.sin(angle * i) * percent);
            if(i == 0){
                path.moveTo(x,y);
            }else{
                path.lineTo(x,y);
                Log.d(TAG, "drawArea: x "+x);
                Log.d(TAG, "drawArea: y "+y);
            }
            mAreaPaint.setAlpha(255);
            canvas.drawCircle(x,y,5,mAreaPaint);
        }
        path.close();
        mAreaPaint.setAlpha(180);
        canvas.drawPath(path,mAreaPaint);
    }



}
