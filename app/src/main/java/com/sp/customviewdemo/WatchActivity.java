package com.sp.customviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sp.customviewdemo.view.DrawWatch;

/**
 * Created by songpeng on 2017/8/31.
 * <p>
 * Date 2017/8/31
 * <p>
 * Description
 */

public class WatchActivity extends Activity {

    private DrawWatch draw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        initView();
    }

    private void initView(){
        draw = (DrawWatch) findViewById(R.id.draw_watch);
    }
}
