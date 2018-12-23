package com.example.elie.driverapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.example.elie.driverapp.Controller.DriverActivity;

public class MyBroadcastReceiver extends BroadcastReceiver

{
    @Override
    public void onReceive(Context context, Intent intent) {

    }

    private int MY_NOTFICATION_ID;

    final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    //final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    @SuppressLint("WrongConstant")

    NotificationChannel notificationChannel;

    NotificationManager notificationManager;















 }
