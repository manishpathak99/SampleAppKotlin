package com.sampleapp.notification.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sampleapp.R;
import com.sampleapp.module.SplashActivity;

import java.util.Map;

/**
 * Created by saveen_dhiman on 13-Aug-17.
 * To display push notifications to user
 */
public class PushMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MY_RITE_SERVICE";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        //Timber.e("FCM Data Message: " + remoteMessage.getData());
        //Timber.e("FCM Data Message 1: " + remoteMessage);
        if (!didCreateNotification(remoteMessage)){

        }

    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private boolean didCreateNotification(RemoteMessage messageBody) {
        if (messageBody == null)
            return false;

        Map<String, String> data = messageBody.getData();

        if (data == null)
            return false;

        String pm_title = data.get("pm_title");
        String pm_shouldSound = data.get("pm_sound");
        String pm_body = data.get("pm_body");

        if (pm_title == null || pm_shouldSound == null || pm_body == null) {
            return false; //
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);

        Intent intent = new Intent(getBaseContext(), SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.BigTextStyle bigtext = new NotificationCompat.BigTextStyle();

        if (pm_shouldSound.equals("true"))
            mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(0)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setContentText(pm_body)
                .setContentTitle(pm_title)
                .setColor(ContextCompat.getColor(this, R.color.colorFbButton))
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(001, notification);

        return true;
    }
//    /**
//     * Create and show a simple notification containing the received FCM message.
//     *
//     * @param messageBody FCM message body received.
//     */
//    private void sendNotification(RemoteMessage messageBody) {
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this);
//
//        Intent intent = new Intent(getBaseContext(), SplashActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//
//        inboxStyle.addLine(messageBody.getNotification().getBody());
//
//        Notification notification;
//        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(messageBody.getNotification().getTitle()).setWhen(0)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .setContentTitle(messageBody.getNotification().getTitle())
//                .setStyle(inboxStyle)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentText(messageBody.getNotification().getBody())
//                .build();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(001, notification);
//    }

}
