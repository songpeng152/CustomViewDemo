package com.sp.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sp.customviewdemo.PieDate;
import com.sp.customviewdemo.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by songpeng on 2017/8/31.
 * <p>
 * Date 2017/8/31
 * <p>
 * Description 画饼状图
 */

public class PieChartView extends View {
    private String TAG = PieChartView.class.getSimpleName();
    private Paint mPaint;//画笔

    private int mWidth, mHeight;//宽、高

    private List<PieDate> pieDateList;

    private float startAngle; //起始角度


    private int[] mColors = {0xFFCFFF00, 0xFF6495AD, 0xFFE32636, 0xFFA00000, 0xFF8A8A0A, 0xFFBA8C69, 0xFFAA8A80,
            0xFFE6A800, 0xFF7CFC00};

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float r = Math.min(mWidth,mHeight) / 2 * 0.5f;
        canvas.translate(mWidth/2,mHeight/2);
        RectF rectF = new RectF(-r,-r,r,r);
        for (int i = 0; i < pieDateList.size(); i++) {
            PieDate pieDate = pieDateList.get(i);
            mPaint.setColor(pieDate.getPieColor());
            canvas.drawArc(rectF,startAngle,pieDate.getPieAngle(),true,mPaint);
            if (i != 0) {
                float angle = pieDate.getPieAngle()/2 + startAngle;
//                float angle1 = pieDate.getPieAngle()*4/10 + startAngle;
                float angle1 = angle + 70;
                float r1 = 20;
                float x  = (float) (r*Math.cos(angle * Math.PI/180));
                float y = (float) (r*Math.sin(angle * Math.PI/180));
                float x1  = x*10/9+ (float) (r1*Math.cos(angle1 * Math.PI/180));
                float y1 = y*10/9 + (float) (r1*Math.sin(angle1 * Math.PI/180));

                Log.d(TAG, "onDraw: angle "+angle);
                canvas.drawLine(x/2,y/2,x*10/9,y*10/9,mPaint);//画一根扇形中间往外的线
                canvas.drawLine(x*10/9,y*10/9,x1,y1,mPaint);//画一个斜线
                mPaint.setColor(getResources().getColor(R.color.black));
                DecimalFormat bigDecimal = new DecimalFormat("###.00");
                canvas.drawText(pieDate.getPieName() + " "+bigDecimal.format(pieDate.getPiePercent()* 100)+"%",x1,y1,mPaint);
//                canvas.drawTextOnPath();
            }
            startAngle += pieDate.getPieAngle();
        }
    }

    public void setDate(List<PieDate> pieList) {
        pieDateList = pieList;
        initDate(pieList);
        invalidate();

    }

    private void initDate(List<PieDate> pieList) {
        if (pieList == null && pieList.size() <= 0) {
            return;
        }
        float sumValue = 0;
        for (int i = 0; i < pieList.size(); i++) {
            PieDate pieDate = pieList.get(i);
            sumValue += pieDate.getPieValue();
            int j = i % mColors.length;
            pieDate.setPieColor(mColors[j]);
        }

        for (int i = 0; i < pieList.size(); i++) {
            PieDate pieDate = pieList.get(i);

            float percent = pieDate.getPieValue() / sumValue; //计算百分比

            float angle = percent * 360 ;// 计算角度

            pieDate.setPieAngle(angle);
            pieDate.setPiePercent(percent);
        }
    }




















}
