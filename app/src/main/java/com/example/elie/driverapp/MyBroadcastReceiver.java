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

    //Context context=;
    @Override
    public void onReceive(Context context, Intent intent) {
        int MY_NOTFICATION_ID;

        final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        //final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        @SuppressLint("WrongConstant")

        NotificationChannel notificationChannel;

        NotificationManager notificationManager;



            final PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, DriverActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notification {s", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("Channel description");

                notificationChannel.enableLights(true);

                notificationChannel.setLightColor(Color.CYAN);

                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});

                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);

            }

            final NotificationCompat.Builder b = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);


        if(intent.getAction().equals("New Order"))
        {
            String destination= intent.getStringExtra("Destination");
            b.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.logo7_hdpi)
                    .setContentTitle("New Waiting Order")
                    .setContentText("You have a  new Waiting Order !!!" + destination)
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setContentIntent(contentIntent)
                    .setContentInfo("Info");


            notificationManager.notify(1, b.build());
        }
        if(intent.getAction().equals("Finished Order"))
        {
            b.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.logo7_hdpi)
                    .setContentTitle("New Waiting Order")
                    .setContentText("The order is now taken !!!")
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setContentIntent(contentIntent)
                    .setContentInfo("Info");


            notificationManager.notify(1, b.build());
        }

        }



}




















