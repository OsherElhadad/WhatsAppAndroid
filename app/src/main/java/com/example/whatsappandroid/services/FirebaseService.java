package com.example.whatsappandroid.services;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.utilities.Info;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {

    private NotificationManagerCompat notificationManagerCompat;
    private int contactsCounter;
    private int messagesCounter;

    public FirebaseService() {
        notificationManagerCompat = Info.notificationManagerCompat;
        contactsCounter = 1;
        messagesCounter = 1;
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String type = "type";
        String user = "User";

        // check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            String typeGot = remoteMessage.getData().get(type);
            String userGot = remoteMessage.getData().get(user);

            // if got a new contact message, then show a proper notification
            if (typeGot.compareTo("Contact") == 0) {
                if (remoteMessage.getNotification() != null &&
                        (Info.loggedUser == null || Info.loggedUser.length() == 0 ||
                                userGot.compareTo(Info.loggedUser) == 0)) {
                    NotificationCompat.Builder builder = new NotificationCompat
                            .Builder(this, "1")
                            .setSmallIcon(R.drawable.ic_whatsapp)
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    notificationManagerCompat.notify(contactsCounter++, builder.build());
                    Intent intent = new Intent("notifyContact");
                    LocalBroadcastManager.getInstance(getApplicationContext())
                            .sendBroadcast(intent);
                }
            } else if (typeGot.compareTo("Message") == 0) {

                // if got a new message message, then show a proper notification
                if (remoteMessage.getNotification() != null &&
                        (Info.loggedUser == null || Info.loggedUser.length() == 0 ||
                                userGot.compareTo(Info.loggedUser) == 0)) {
                    NotificationCompat.Builder builder = new NotificationCompat
                            .Builder(this, "2")
                            .setSmallIcon(R.drawable.ic_whatsapp)
                            .setContentTitle("New message")
                            .setContentText(remoteMessage.getNotification().getTitle())
                            .setStyle(new NotificationCompat.BigTextStyle().bigText
                                    (remoteMessage.getNotification().getBody()))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    notificationManagerCompat.notify(messagesCounter++, builder.build());
                    Intent intent = new Intent("notifyMessage");
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                }
            }
        }
    }
}