package com.sp.customviewdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sp.customviewdemo.R;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

/**
 * Created by songpeng on 2017/9/6.
 * <p>
 * Date 2017/9/6
 * <p>
 * Description 滴滴等车时的loading
 */

public class DiDiView extends View {
    private String TAG = DiDiView.class.getSimpleName();
    private Paint mCirclePaint;//画最外边的圆画笔

    private Paint mTextPaint;//写字的画笔

    private Paint mTextCountPaint;//写车数量的画笔

    private Paint mTextUnitPaint;//写车单位的画笔

    private Paint mDrawArcPaint;//画弧的画笔

    private int mWidth;//宽

    private int mHeight;//高

    int colors[] = {Color.parseColor("#F1C200"),Color.parseColor("#FF9244")};

    private RectF rectF;

    private int sweepAngle = 0;

    private String unit = "辆";

    private String notification = "已通知出租车";

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作

    public DiDiView(Context context) {
        this(context,null);
    }

    public DiDiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DiDiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCirclePaint = new Paint();
        mCirclePaint.setStrokeWidth(5);
        mCirclePaint.setColor(Color.parseColor("#E3E4E7"));
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);


        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setColor(Color.parseColor("#8B8C8F"));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(20);
        mTextPaint.setStyle(Paint.Style.FILL);


        mTextCountPaint = new Paint();
        mTextCountPaint.setStrokeWidth(1);
        mTextCountPaint.setColor(Color.parseColor("#EC9B70"));
        mTextCountPaint.setAntiAlias(true);
        mTextCountPaint.setTextSize(40);
        mTextCountPaint.setStyle(Paint.Style.FILL);


        mTextUnitPaint = new Paint();
        mTextUnitPaint.setStrokeWidth(1);
        mTextUnitPaint.setColor(Color.parseColor("#EC9B70"));
        mTextUnitPaint.setAntiAlias(true);
        mTextUnitPaint.setTextSize(20);
        mTextUnitPaint.setStyle(Paint.Style.FILL);

        mDrawArcPaint = new Paint();
        mDrawArcPaint.setStrokeWidth(5);
        mDrawArcPaint.setAntiAlias(true);
        mDrawArcPaint.setStyle(Paint.Style.STROKE);

        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();//通过bitmapFactory获取图片资源
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.point,options);//获取资源
        mMatrix = new Matrix();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth,mHeight);

        SweepGradient sweepGradient = new SweepGradient(0,0,colors,null);
//        Matrix matrix = new Matrix();
//        matrix.setRotate(-180,0,0);
//        sweepGradient.setLocalMatrix(matrix);
        mDrawArcPaint.setShader(sweepGradient);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);//把画布移到屏幕的中心
        rectF = new RectF(-mWidth/2+50,-mWidth/2+50,mWidth/2-50,mWidth/2-50);

        Path path = new Path();
        path.addCircle(0,0,(mWidth-100)/2, Path.Direction.CW);//cw是顺时针
        canvas.drawPath(path,mCirclePaint);//顺时针的画一个圆


        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure,这是测试path的

        BigDecimal bigDecimal1 = BigDecimal.valueOf(sweepAngle);
        BigDecimal b2 = BigDecimal.valueOf(360);
        float rad = bigDecimal1.divide(b2,MathContext.DECIMAL32).floatValue();//通过bigdecimal来获取黄线在灰色圆上的位置，在放黄色的小球
        measure.getPosTan(measure.getLength() * rad +10, pos, tan);// 获取当前位置的坐标以及趋势

        mMatrix.reset();                                                        // 重置Matrix
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

        Rect rect = new Rect();
        mTextPaint.getTextBounds(notification,0,notification.length(),rect);
        canvas.drawText(notification,-(rect.left+rect.right)/2,rect.bottom,mTextPaint);//写通知的字

        Rect rectCount = new Rect();
        mTextCountPaint.getTextBounds(String.valueOf(sweepAngle),0,String.valueOf(sweepAngle).length(),rectCount);
        canvas.drawText(String.valueOf(sweepAngle),-(rect.left+rect.right)/2,rect.bottom-rect.top-rect.bottom-rectCount.top/*-rectCount.bottom*/,mTextCountPaint);//写车的数量

        canvas.drawText(unit,(rect.left-rect.right)/2+100,rect.bottom-rect.top-rect.bottom-rectCount.top/*-rectCount.bottom*/,mTextUnitPaint);//写车的单位
//        Log.d(TAG, "onDraw: y=  "+(-rect.top-rectCount.top-rectCount.bottom));
//        Log.d(TAG, "onDraw: rect.top "+rect.top);
//        Log.d(TAG, "onDraw: rectCount.top "+rectCount.top);
//        Log.d(TAG, "onDraw: rectCount.bottom "+rectCount.bottom);

        mMatrix.postRotate(-90);
        canvas.drawBitmap(mBitmap, mMatrix, mDrawArcPaint);
        canvas.drawArc(rectF,-90,sweepAngle,false, mDrawArcPaint);
    }


    public void setData(int sweepAngle){
        this.sweepAngle = sweepAngle;
        invalidate();
    }












}
