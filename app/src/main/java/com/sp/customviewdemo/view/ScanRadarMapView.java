package com.sp.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by songpeng on 2017/9/4.
 * <p>
 * Date 2017/9/4
 * <p>
 * Description 扫描雷达
 */

public class ScanRadarMapView extends View {
    private Paint mStrokePaint;//画空心圆

    private Paint mFillPaint;//画实心圆

    private int mWidth;//画布的宽

    private int mHeight;//画布的高

    private Matrix matrix;
    private int degrees;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            degrees++;
            matrix.postRotate(degrees, mWidth / 2, mHeight / 2);//旋转矩阵
            ScanRadarMapView.this.invalidate();// 重绘
            mHandler.postDelayed(mRunnable, 55);
        }
    };
    public ScanRadarMapView(Context context) {
        this(context,null);
    }

    public ScanRadarMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScanRadarMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setAntiAlias(true);

        mFillPaint = new Paint();
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setAntiAlias(true);

//        matrix = new Matrix();
        mHandler.postDelayed(mRunnable,500);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth,mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        matrix = canvas.getMatrix();
        canvas.save();

        canvas.drawCircle(0,0,mWidth/3,mStrokePaint);

        Shader mShader = new SweepGradient(0, 0, Color.TRANSPARENT, Color.parseColor("#33FFFFFF"));
        mFillPaint.setShader(mShader);
        canvas.setMatrix(matrix);
        canvas.drawCircle(0,0,mWidth/3,mFillPaint);
        matrix.reset();//重置矩阵，避免累加，越转越快
    }

















}
