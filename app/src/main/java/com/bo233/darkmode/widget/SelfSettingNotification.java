package com.bo233.darkmode.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.bo233.darkmode.R;
import com.bo233.darkmode.util.MyBroadcastReceiver;

public class SelfSettingNotification {
    public SelfSettingNotification(Context context){

        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        intent.setAction("setNightModeView");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.id.app_icon, "233", pendingIntent).build();


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "bo233.darkmode")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("设置")
                        .setContentText("Hello World!")
                        .addAction(action);
//设置点击通知跳转的activity
//        Intent resultIntent = new Intent(this, ResultActivity.class);

//        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

//创建一个任务栈，用于处理在通知页面，返回时现实的页面
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(ResultActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        final Notification notification = mBuilder.build();

//这通知的其他属性，比如：声音和振动
//        notification.defaults |= Notification.DEFAULT_SOUND;
//        notification.defaults |= Notification.DEFAULT_VIBRATE;

        mNotificationManager.notify(2333, notification);

//        Intent intent = new Intent(context, MyBroadcastReceiver.class);
//        intent.setAction("setNightModeView");
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        Notification.Action action = new Notification.Action.Builder(R.id.app_icon, "233", pendingIntent).build();
//
//        Notification notification = new Notification.Builder(context)
//                // Show controls on lock screen even when user hides sensitive content.
//                .setVisibility(Notification.VISIBILITY_PUBLIC)
//                .setSmallIcon(R.mipmap.ic_launcher)
////HERE ARE YOUR BUTTONS
//                .addAction(action) // #0
//                // Apply the media style template
////                .setStyle(new Notification.MediaStyle()
////                        .setShowActionsInCompactView(1)
////                        .setMediaSession(mMediaSession.getSessionToken())
////                        .setContentTitle("Example for you")
////                        .setContentText("Example for you")
////                        .setLargeIcon(ButtonExampleIcon)
//                        .build();
//        notification.notify();
    }
}
