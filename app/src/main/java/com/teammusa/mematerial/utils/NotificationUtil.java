package com.teammusa.mematerial.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.teammusa.mematerial.R;


public class NotificationUtil {

    private static final String CHANNEL_ID = "com.spender.spender.ANDROID";
    private Context mContext;

    public NotificationUtil(Context context) {
        this.mContext = context;
        createChannel();

    }

    public void createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = "Spender";
            String description = "Spender Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            // Sets whether notification posted to this channel should vibrate.
            channel.enableVibration(true);
            // Sets the notification light color for notifications posted to this channel
            channel.setLightColor(mContext.getResources().getColor(R.color.colorAccent));
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // Register the channel with the system
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    public void hotspotActiveNotify() {
//
//        Intent homeIntent = new Intent(this.mContext, HomeActivity.class);
//        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent homePendingIntent = PendingIntent.getActivity(this.mContext, 0, homeIntent, 0);
//
//        Intent cancelHotspotIntent = new Intent(this.mContext, HomeActivity.NotificationBroadcastReceiver.class);
//        cancelHotspotIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        cancelHotspotIntent.setAction(Constant.CANCEL_HOTSPOT_ACTION);
//        cancelHotspotIntent.putExtra(Constant.HOTSPOT_NOTIFICATION_ID, Constant.HOTSPOT_NOTIFICATION);
//
//        PendingIntent cancelHotspotPendingIntent = PendingIntent.getActivity(this.mContext,
//                20, cancelHotspotIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.mContext, CHANNEL_ID);
//        builder.setContentTitle("You are receiving money");
//        builder.setContentText("Customers can now connect to you");
//        builder.setAutoCancel(false);
//        builder.setColor(mContext.getResources().getColor(R.color.colorPrimary));
//        builder.setPriority(1);
//        builder.setShowWhen(true);
//        builder.setSmallIcon(R.drawable.ic_transactions);
//        builder.setOngoing(true);
//        builder.setSound(RingtoneManager.getDefaultUri(2));
//        builder.setVisibility(1);
//        if (Build.VERSION.SDK_INT >= 21) {
//            builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
////            builder.setSound();
//        }
//        builder.setContentIntent(homePendingIntent);
//        builder.setWhen(System.currentTimeMillis());
//        builder.addAction(R.drawable.ic_cancel, "Cancel", cancelHotspotPendingIntent);
//        NotificationManagerCompat.from(this.mContext).notify(Constant.HOTSPOT_NOTIFICATION, builder.build());
////    }
//    }

}
