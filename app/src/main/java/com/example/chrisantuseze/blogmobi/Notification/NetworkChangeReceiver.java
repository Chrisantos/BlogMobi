package com.example.chrisantuseze.blogmobi.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by CHRISANTUS EZE on 3/26/2018.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case "android.net.wifi.STATE_CHANGE":
                FeedUpdateUtilities.scheduleFeedUpdate(context);
        }
    }
}
