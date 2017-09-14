package com.sp.customviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.sp.customviewdemo.view.DiDiView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by songpeng on 2017/9/6.
 * <p>
 * Date 2017/9/6
 * <p>
 * Description
 */

public class DiDiActivity extends Activity {
    int i = 0;
    private DiDiView diDiView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_didi_view);
        diDiView = (DiDiView) findViewById(R.id.didi_view);

        final Timer timer = new Timer();
        TimerTask timeTask = new TimerTask() {
            @Override
            public void run() {
                if (i>360) {
                    i = 0;
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timeTask,1000,100);

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                diDiView.setData(i+=1);
            }
            super.handleMessage(msg);
        }
    };

}
