package com.example.whatsappandroid.utilities;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.db.UsersDB;

public class Info extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static String loggedUser;
    public static String loggerUserToken;
    public static boolean isLogged;
    public static String contactId;
    public static UsersDB usersDB;
    public static NotificationManagerCompat notificationManagerCompat;
    public static String baseUrlServer;
    public static String serverPort;

    @Override
    public void onCreate() {
        super.onCreate();
        baseUrlServer = this.getString(R.string.basicServerUrl);
        serverPort = this.getString(R.string.myServerPort);
        context = getApplicationContext();
        loggedUser = null;
        loggerUserToken = null;
        isLogged = false;
        usersDB = Room.databaseBuilder(getApplicationContext(), UsersDB.class, "user")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        createNotificationChannel();
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // channel for contacts
            CharSequence name = "API contacts channel";
            String description = "channel with the api server for new contacts";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            // channel for messages
            name = "API messages channel";
            description = "channel with the api server for new messages";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            channel = new NotificationChannel("2", name, importance);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);
        }
    }
}
