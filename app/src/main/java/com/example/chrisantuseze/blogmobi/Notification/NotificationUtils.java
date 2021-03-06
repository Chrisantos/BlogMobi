package com.example.chrisantuseze.blogmobi.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.chrisantuseze.blogmobi.ListArticle;
import com.example.chrisantuseze.blogmobi.RSSService;
import com.example.chrisantuseze.blogmobi.Library.RSSConverterFactory;
import com.example.chrisantuseze.blogmobi.Library.RSSFeed;
import com.example.chrisantuseze.blogmobi.Library.RSSItem;
import com.example.chrisantuseze.blogmobi.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by CHRISANTUS EZE on 3/25/2018.
 */

public class NotificationUtils {
    private static final int NOTIFICATION_ID = 1138;
    private static final int PENDING_INTENT_ID = 3417;
    private static final String NOTIFICATION_CHANNEL_ID = "InfoscopeMedia";
    private static String mFeedUrl = "http://www.infoscopemedia.com.ng/feeds/posts/default?alt=rss";

    public static void feedUpdate(final Context context) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://github.com")
                .addConverterFactory(RSSConverterFactory.create()).build();

        RSSService service = retrofit.create(RSSService.class);
        service.getRss(mFeedUrl).enqueue(new Callback<RSSFeed>() {
            @Override
            public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
                List<RSSItem> items = response.body().getItems();
                final RSSItem item = items.get(0);
                String title = item.getTitle();
                String description = item.getDescription();
                notif(context, title, description);
            }

            @Override
            public void onFailure(Call<RSSFeed> call, Throwable t) {
            }
        });

    }

    private static void notif(Context context, String title, String description){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.infoscope)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(title)
                .setContentText(description)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }


    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, ListArticle.class);
        return PendingIntent.getActivity(
                context,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.infoscope);
        return largeIcon;
    }
}