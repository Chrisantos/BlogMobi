package com.example.chrisantuseze.blogmobi.Notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.chrisantuseze.blogmobi.ListArticle;
import com.firebase.jobdispatcher.JobParameters;

/**
 * Created by CHRISANTUS EZE on 3/25/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FeedUpdateFirebaseJobService extends com.firebase.jobdispatcher.JobService {
    private static BackgroundTask mBTask;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters job) {
        mBTask = new BackgroundTask(){
            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job, false);
            }
        };
        mBTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Context context = FeedUpdateFirebaseJobService.this;
            NotificationUtils.feedUpdate(context);
            return null;
        }
    }

}
