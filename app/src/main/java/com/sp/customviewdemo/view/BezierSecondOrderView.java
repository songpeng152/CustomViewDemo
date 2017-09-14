package com.sp.customviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sp.customviewdemo.R;

/**
 * Created by songpeng on 2017/9/5.
 * <p>
 * Date 2017/9/5
 * <p>
 * Description 二阶贝塞尔曲线
 */

public class BezierSecondOrderView extends View {
    private Paint assistPaint;//画辅助线的画笔

    private Paint pointPaint;//画点的画笔

    private Paint bezierPaint;//画二阶贝塞尔曲线的画笔

    private int mCenterX;//中心x坐标

    private int mCenterY;//中心y坐标

    private PointF start,end,control;
    public BezierSecondOrderView(Context context) {
        this(context,null);
    }

    public BezierSecondOrderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierSecondOrderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        assistPaint = new Paint();
        assistPaint.setAntiAlias(true);
        assistPaint.setColor(getResources().getColor(R.color.black));
        assistPaint.setStyle(Paint.Style.STROKE);
        assistPaint.setStrokeWidth(2);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(getResources().getColor(R.color.brown));
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setStrokeWidth(20);

        bezierPaint = new Paint();
        bezierPaint.setAntiAlias(true);
        bezierPaint.setColor(getResources().getColor(R.color.red));
        bezierPaint.setStyle(Paint.Style.STROKE);
        bezierPaint.setStrokeWidth(2);

        start = new PointF(0,0);
        end = new PointF(0,0);
        control = new PointF(0,0);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        mWidth = MeasureSpec.getSize(widthMeasureSpec);
//        mHeight = MeasureSpec.getSize(heightMeasureSpec);
//
//        setMeasuredDimension(mWidth,mHeight);
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w/2;
        mCenterY = h/2;

        start.x = mCenterX-200;
        start.y = mCenterY;
        end.x = mCenterX + 200;
        end.y = mCenterY;
        control.x = mCenterX;
        control.y = mCenterY+100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPoint(start.x,start.y,pointPaint);
        canvas.drawPoint(end.x,end.y,pointPaint);
        canvas.drawPoint(control.x,control.y,pointPaint);

        canvas.drawLine(start.x,start.y,control.x,control.y,assistPaint);
        canvas.drawLine(end.x,end.y,control.x,control.y,assistPaint);

        Path path = new Path();
        path.moveTo(start.x,start.y);
        path.quadTo(control.x,control.y,end.x,end.y);
        canvas.drawPath(path,bezierPaint);

    }






















}
