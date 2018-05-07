package com.example.chrisantuseze.blogmobi.AndroidJob;

import android.app.Application;

import com.evernote.android.job.JobManager;

/**
 * Created by CHRISANTUS EZE on 4/27/2018.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new DemoJobCreator());
        DemoSyncJob.schedulePeriodicJob();
    }
}
