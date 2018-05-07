package com.example.chrisantuseze.blogmobi.FirebaseJobDispatcher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.example.chrisantuseze.blogmobi.ListArticle;
import com.example.chrisantuseze.blogmobi.Notification.NotificationUtils;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by CHRISANTUS EZE on 5/3/2018.
 */

public class MyService extends JobService {
    private NotifAsyncTask notifAsyncTask = null;
    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters job) {
        notifAsyncTask = new NotifAsyncTask(this){
            @Override
            protected void onPostExecute(String[] strings) {
                super.onPostExecute(strings);
                jobFinished(job, false);
            }
        };
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (notifAsyncTask != null){
            notifAsyncTask.cancel(true);
        }
        return true;
    }
}
