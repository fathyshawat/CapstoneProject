package com.example.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.firebase.database.FirebaseDatabase;

public class StartApplication extends Application {

    public static final String CHANNEL_ID="id";
    private final static String CHANNEL_NAME = "channel's name ";
    private static final String CHANNEL_DESC="description";


    @Override
    public void onCreate() {
        super.onCreate();

        //cashing firebase database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
