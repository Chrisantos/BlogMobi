package com.example.chrisantuseze.blogmobi.AndroidJob;


import android.content.Intent;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.example.chrisantuseze.blogmobi.Notification.FeedUpdateIntentService;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by CHRISANTUS EZE on 4/27/2018.
 */

public class DemoSyncJob extends Job {

    public static final String TAG = "job_demo_tag";
    private static final long TARGET_HOUR = 2L;
    private static final long TARGET_MINUTE = 15;
    private static final long WINDOW_LENGTH = 60;

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {

        // run your job here
        Intent intent = new Intent(getContext(), FeedUpdateIntentService.class);
        getContext().startService(intent);

        return Result.SUCCESS;
    }

//    public static void scheduleJob() {
//
//        Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//
//        DailyExecutionWindow executionWindow =
//                new DailyExecutionWindow(hour, minute, TARGET_HOUR, TARGET_MINUTE, WINDOW_LENGTH);
//
//        new JobRequest.Builder(DemoSyncJob.TAG)
//                .setExecutionWindow(executionWindow.startMs, executionWindow.endMs)
//                .setBackoffCriteria(5_000L, JobRequest.BackoffPolicy.EXPONENTIAL)
//                .setRequiresCharging(false)
//                .setRequiresDeviceIdle(false)
//                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
//                .setRequirementsEnforced(true)
//                .setUpdateCurrent(true)
//                .build()
//                .schedule();
//    }

    public static void schedulePeriodicJob(){
        new JobRequest.Builder(DemoSyncJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }

    private void cancelJob(int jobId) {
        JobManager.instance().cancel(jobId);
    }
}
