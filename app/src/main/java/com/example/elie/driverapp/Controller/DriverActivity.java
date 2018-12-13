package com.example.elie.driverapp.Controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.elie.driverapp.R;

public class DriverActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
    }

    /*@Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder myBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.Icon)
                        .setContentTitle(getResources().getString(R.string.Title_Notification))
                        .setContentText(getResources().getString(R.string.Describle_Notification));
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,myBuilder.build());

    }*/
}
