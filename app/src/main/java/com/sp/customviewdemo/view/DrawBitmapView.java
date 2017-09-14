package com.sp.customviewdemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by songpeng on 2017/9/6.
 * <p>
 * Date 2017/9/6
 * <p>
 * Description
 */

public class DrawBitmapView extends View {
    public DrawBitmapView(Context context) {
        this(context,null);
    }

    public DrawBitmapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawBitmapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
