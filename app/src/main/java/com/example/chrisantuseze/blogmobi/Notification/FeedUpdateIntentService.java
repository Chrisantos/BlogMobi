package com.example.chrisantuseze.blogmobi.Notification;

import android.app.IntentService;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by CHRISANTUS EZE on 3/26/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class FeedUpdateIntentService extends IntentService {
    public FeedUpdateIntentService() {
        super("FeedUpdateIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            NotificationUtils.feedUpdate(getApplicationContext());
        }

    }
}
