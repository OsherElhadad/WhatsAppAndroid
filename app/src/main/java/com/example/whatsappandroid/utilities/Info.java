package com.example.whatsappandroid.utilities;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class Info extends Application {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static String loggedUser;
    public static int contactId;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
