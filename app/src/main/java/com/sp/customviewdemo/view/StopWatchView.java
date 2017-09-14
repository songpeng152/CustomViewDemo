package com.sp.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by songpeng on 2017/9/5.
 * <p>
 * Date 2017/9/5
 * <p>
 * Description 画仪表盘
 */

public class StopWatchView extends View {
    private String TAG = StopWatchView.class.getSimpleName();

    private int mWidth;//宽

    private int mHeight;//高

    private Paint mPaint; //画圆的画笔

    private Paint mSweepPaint;//画渐变的画笔

    private int[] colors = new int[]{Color.GREEN, Color.YELLOW, Color.RED, Color.RED};

    private float i = 0;

    private SweepGradient sweepGradient;
    public StopWatchView(Context context) {
        this(context,null);
    }

    public StopWatchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StopWatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mSweepPaint = new Paint();
        mSweepPaint.setStrokeWidth(10);
        mSweepPaint.setColor(Color.parseColor("#000000"));
        mSweepPaint.setAntiAlias(true);
        mSweepPaint.setStyle(Paint.Style.STROKE);





    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);


        setMeasuredDimension(mWidth,mHeight);

        sweepGradient = new SweepGradient(mWidth/2,mHeight/2,colors,
                null);
        mSweepPaint.setStrokeWidth(12);
        mSweepPaint.setShader(sweepGradient);
        Matrix rotateMatrix = new Matrix();

        rotateMatrix.setRotate(118, mWidth/2, mHeight/2);
        sweepGradient.setLocalMatrix(rotateMatrix);
        mSweepPaint.setShader(sweepGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);
        mPaint.setStrokeWidth(10);
        RectF mBigOval = new RectF(-mWidth/2+50,-mWidth/2+50,mWidth/2-50,mWidth/2-50);
        canvas.drawArc(mBigOval, -210, 240, false, mPaint); //底色的弧

        canvas.rotate(60);

        for (int i = 0; i <=30; i++) {
            mPaint.setStrokeWidth(1);
            canvas.drawLine(0,mWidth/2-70,0,mWidth/2-60,mPaint);
            if (i * 8 % 40 == 0) {
                mPaint.setStrokeWidth(1);
                canvas.drawLine(0,mWidth/2-90,0,mWidth/2-60,mPaint);

                canvas.save();
                canvas.translate(0,mWidth/2-80);
                canvas.rotate(-60-i*8);
                String text = String.valueOf(i*8+(i*8/40*10));
                Rect textBound = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), textBound);
//                canvas.drawText(String.valueOf(i*8+(i*8/40*10)),0,mWidth/2-80,mPaint);
                canvas.drawText(text,-(textBound.left+textBound.right)/2,(textBound.top+textBound.bottom)/2,mPaint);
                canvas.restore();
            }
            canvas.rotate(8);
        }
        canvas.rotate(50);

        canvas.drawArc(mBigOval,-210,i,false,mSweepPaint);
    }

    public void setData(int speed){
        i =(float) (speed * 0.8);
        Log.d(TAG, "setData: "+i);
        invalidate();
    }
}
