package com.example.javajobschedulerone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

// https://viblo.asia/p/jobscheduler-924lJY4mZPM

// setPeriodic bị lỗi trên android 7.0
//https://www.it-swarm.dev/vi/android/bo-lap-lich-cong-viec-khong-chay-tren-android-n/825545744/
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void scheduleJob(View v) {
        ComponentName componentName = new ComponentName(this, ExampleJobService.class);
        JobInfo info = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            info = new JobInfo.Builder(123, componentName)
                    .setRequiresCharging(true)
                    .setMinimumLatency(500)
                    .setOverrideDeadline(1000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setPersisted(true)
//                    .setPeriodic(15 * 60 * 1000)
//                    .setPeriodic( 5 * 1000)
                    .build();

            JobScheduler scheduler = (JobScheduler) getApplication().getSystemService(Context.JOB_SCHEDULER_SERVICE);
            int resultCode = scheduler.schedule(info);
            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                Log.d(TAG, "Job scheduled");
            } else {
                Log.d(TAG, "Job scheduling failed");
            }
        }
    }

    public void cancelJob(View v) {
        JobScheduler scheduler = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.cancel(123);
            Log.d(TAG, "Job cancelled");
        }
    }
}
