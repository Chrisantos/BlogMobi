package com.example.chrisantuseze.blogmobi.Notification;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * Created by CHRISANTUS EZE on 3/25/2018.
 */

public class FeedUpdateUtilities {

    private static final String UPDATE_JOB_TAG = "feed_update_tag";

    private static boolean sInitialized;

    static Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.M)
    synchronized public static void scheduleFeedUpdate(@NonNull final Context context) {

        if (sInitialized) return;

        ComponentName serviceComponent = new ComponentName(context, NewsUpdateFirebaseJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY); // require unmetered network
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());

        mContext = context;

        sInitialized = true;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static class NewsUpdateFirebaseJobService extends JobService {
        private static AsyncTask mAsyncTask;

        @SuppressLint("StaticFieldLeak")
        @Override
        public boolean onStartJob(final JobParameters params) {
            mAsyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
//                    Context context = com.example.chrisantuseze.blogmobi.Notification.FeedUpdateFirebaseJobService.this;
                    NotificationUtils.feedUpdate(mContext);
                    return null;
                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                protected void onPostExecute(Object o) {
                    jobFinished(params, false);
                }
            };
            mAsyncTask.execute();

            return true;
        }

        @Override
        public boolean onStopJob(JobParameters params) {
            if (mAsyncTask != null) mAsyncTask.cancel(true);
            return true;
        }

    }

}
