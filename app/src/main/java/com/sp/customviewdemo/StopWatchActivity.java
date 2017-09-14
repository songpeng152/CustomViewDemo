package com.sp.customviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.sp.customviewdemo.view.StopWatchView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by songpeng on 2017/9/5.
 * <p>
 * Date 2017/9/5
 * <p>
 * Description
 */

public class StopWatchActivity extends Activity {


    private StopWatchView stopWatchView;
    int i = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        stopWatchView = (StopWatchView) findViewById(R.id.stop_watch_view);
        final Timer timer = new Timer();
        TimerTask timeTask = new TimerTask() {
            @Override
            public void run() {
                if (i>300) {
                    timer.cancel();
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
                stopWatchView.setData(i+=1);
            }
            super.handleMessage(msg);
        }
    };


}
