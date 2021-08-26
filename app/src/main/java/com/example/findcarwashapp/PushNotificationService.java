package com.example.findcarwashapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.Service;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import android.app.NotificationManager;

public class PushNotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        String Channel_id = "Heads up notification";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    Channel_id,
                    "Heads up nofication",
                    NotificationManager.IMPORTANCE_HIGH
            );

            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder builder = new Notification.Builder(this,Channel_id)
                    .setContentTitle(title)
                    .setContentTitle(message)
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                    .setAutoCancel(true);

            NotificationManagerCompat.from(this).notify(1,builder.build());
            super.onMessageReceived(remoteMessage);
        }
    }
}
