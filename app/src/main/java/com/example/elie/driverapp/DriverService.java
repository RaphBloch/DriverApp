package com.example.elie.driverapp;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.Model.Entities.Driver;

import java.util.List;

public class DriverService extends Service


{
    public DriverService()
    {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FireBase_DSManager.notifyToClientList(new FireBase_DSManager.NotifyDataChange<List<ClientRequest>>() {
            @Override
            public void OnDataChanged(List<ClientRequest> obj)
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
