package com.example.whatsappandroid.utilities;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.whatsappandroid.db.UsersDB;

public class Info extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static String loggedUser;
    public static String loggerUserToken;
    public static boolean isLogged;
    public static String contactId;
    public static UsersDB usersDB;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        loggedUser = null;
        loggerUserToken = null;
        isLogged = false;
        usersDB = Room.databaseBuilder(getApplicationContext(), UsersDB.class, "user")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }
}
