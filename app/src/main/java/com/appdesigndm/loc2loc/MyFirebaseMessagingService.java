package com.appdesigndm.loc2loc;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

/**
 * Created by alexfernandez on 13/2/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final  String TAG = "NOTICIAS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d(TAG, "Recibido de..." + from);

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notificación" + remoteMessage.getNotification().getBody());

            mostarNotificacion(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
    }

    private void mostarNotificacion(String title, String body) {

        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0, intent1,
                PendingIntent.FLAG_ONE_SHOT);


        //int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.mipmap.ic_notification_lollypop : R.mipmap.ic_launcher;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = "Loc2Loc";


            NotificationChannel channel = new NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(body);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);

            Notification notification = new Notification.Builder(this, channelId)
                    .setContentTitle(title)
                    .setContentText(body)
                    //.setSmallIcon(icon)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent1)
                    .build();
            notificationManager.notify(m, notification);

        } else {

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager1 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}


