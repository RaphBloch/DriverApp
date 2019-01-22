package com.example.elie.driverapp;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.annotation.SuppressLint;

import android.annotation.TargetApi;

import android.app.Activity;

import android.app.Notification;

import android.support.v4.app.NotificationCompat;

import android.app.NotificationChannel;

import android.app.NotificationManager;

import android.app.PendingIntent;

import android.content.Context;

import android.content.Intent;

import android.graphics.Color;

import android.net.Uri;

import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.elie.driverapp.Controller.DriverActivity;
import com.example.elie.driverapp.Controller.MainActivity;
import com.example.elie.driverapp.Model.Backend.Backend;
import com.example.elie.driverapp.Model.Backend.Backend_Factory;
import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.Model.Entities.Driver;

import java.util.List;

public class DriverService extends Service


{
    int MY_NOTFICATION_ID;

    Backend_Factory backend_factory=new Backend_Factory();

    final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    //final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    @SuppressLint("WrongConstant")

    NotificationChannel notificationChannel;

    NotificationManager notificationManager;

    long LoginTime;

    public DriverService()
    {

    }

    @Override
    public void onCreate() {

        super.onCreate();
        LoginTime=(System.currentTimeMillis()/1000);
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {



        FireBase_DSManager f=(FireBase_DSManager) backend_factory.getfactory();




       f.notifyToClientList(new FireBase_DSManager.NotifyDataChange<ClientRequest>() {

            public void OnDataAdded(ClientRequest obj)
            {


                    Intent myintent=new Intent("New order");
                    myintent.putExtra("dest",obj.getDestination());
                    sendBroadcast(myintent);

                //notifs(obj.getDestination());

            }

            @Override
            public void OnDataChanged(ClientRequest obj)
            {


            }
            @Override
            public void OnFailure(Exception exception)
            {


            }
        });


        return START_STICKY ;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //  throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }



}
