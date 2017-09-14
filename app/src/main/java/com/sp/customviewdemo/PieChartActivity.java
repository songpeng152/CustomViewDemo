package com.sp.customviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sp.customviewdemo.view.PieChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songpeng on 2017/8/31.
 * <p>
 * Date 2017/8/31
 * <p>
 * Description
 */

public class PieChartActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        pieChart();
    }



    private void pieChart(){
        PieChartView pieChartView = (PieChartView) findViewById(R.id.parentPanel);
        pieChartView.setDate(getPieDate());
    }

    private List<PieDate> getPieDate(){
        List<PieDate> pieDates = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PieDate pieDate = new PieDate("测试"+i,0.8f+i);
            pieDates.add(pieDate);
        }
        return pieDates;
    }
}
