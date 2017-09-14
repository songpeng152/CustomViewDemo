package com.sp.customviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends Activity implements View.OnClickListener {

    private TextView tvPieChart;
    private TextView tvWatch;
    private TextView tvRadar;
    private TextView tvScanRadar;
    private TextView tvBezierSecond;
    private TextView tvStopWatch;
    private TextView tvDidi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }


    private void initView(){
        tvPieChart = (TextView) findViewById(R.id.tv_pieChart);
        tvWatch = (TextView) findViewById(R.id.tv_watch);
        tvRadar = (TextView) findViewById(R.id.tv_radar);
        tvScanRadar = (TextView) findViewById(R.id.tv_scan_radar);
        tvBezierSecond = (TextView) findViewById(R.id.tv_bezier_second);
        tvStopWatch = (TextView) findViewById(R.id.tv_stop_watch);
        tvDidi = (TextView) findViewById(R.id.tv_didi);
    }

    private void setListener(){
        tvPieChart.setOnClickListener(this);
        tvWatch.setOnClickListener(this);
        tvRadar.setOnClickListener(this);
        tvScanRadar.setOnClickListener(this);
        tvBezierSecond.setOnClickListener(this);
        tvStopWatch.setOnClickListener(this);
        tvDidi.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pieChart:
                startActivity(new Intent(this,PieChartActivity.class));
                break;
            case R.id.tv_watch:
                startActivity(new Intent(this,WatchActivity.class));
                break;
            case R.id.tv_radar:
                startActivity(new Intent(this,RadarMapActivity.class));
                break;
            case R.id.tv_scan_radar:
                startActivity(new Intent(this,ScanRadarActivity.class));
                break;
            case R.id.tv_bezier_second:
                startActivity(new Intent(this,BezierSecondOrderActivity.class));
                break;
            case R.id.tv_stop_watch:
                startActivity(new Intent(this,StopWatchActivity.class));
                break;
            case R.id.tv_didi:
                startActivity(new Intent(this,DiDiActivity.class));
                break;
        }
    }
}
