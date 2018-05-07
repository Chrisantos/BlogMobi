package com.example.chrisantuseze.blogmobi.AndroidJob;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.example.chrisantuseze.blogmobi.Notification.FeedUpdateIntentService;

import java.util.concurrent.TimeUnit;

/**
 * Created by CHRISANTUS EZE on 5/2/2018.
 */

public class MyDailyJob extends DailyJob {
    public static final String TAG = "MyDailyJob";

    public static void schedule() {
        // schedule between 1 and 6 AM
        DailyJob.schedule(new JobRequest.Builder(TAG), TimeUnit.HOURS.toMillis(16), TimeUnit.HOURS.toMillis(17));
    }

    @NonNull
    @Override
    protected DailyJobResult onRunDailyJob(@NonNull Params params) {
        Intent intent = new Intent(getContext(), FeedUpdateIntentService.class);
        getContext().startService(intent);
        return DailyJobResult.SUCCESS;
    }
}
