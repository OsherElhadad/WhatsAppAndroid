package com.example.whatsappandroid.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {
    public FirebaseService() {

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        int x = 0;
    }
}