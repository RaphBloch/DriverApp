package com.example.elie.driverapp.Controller;


import com.example.elie.driverapp.DriverNavigationDrawer.*;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;

import com.example.elie.driverapp.Model.Entities.ClientRequest;
import  com.example.elie.driverapp.R;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.*;
import com.example.elie.driverapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class DriverActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public double longitude;
    public double latitude;
    LocationManager locationManager;
    LocationListener locationListener;
    int MY_NOTFICATION_ID;

    final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    //final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    @SuppressLint("WrongConstant")

    NotificationChannel notificationChannel;

    NotificationManager notificationManager;


    public BroadcastReceiver myReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context,Intent intent){
            System.out.println("inside onReceive");

            notifs();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerReceiver(
                new MyBroadcastReceiver(),
                new IntentFilter(Intent.ACTION_TIME_TICK));

        setContentView(R.layout.activity_driver2);
        locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        } ;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getLocation();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_order_list)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new order_list_fragment()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_order_finished)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new order_finished_fragment()).commit();
        } else if (id == R.id.nav_exit)
        {

            ComponentName componentName = new ComponentName(DriverActivity.this,MainActivity.class);
            Intent myintent=new Intent();
            myintent.setComponent(componentName);
            startActivity(myintent);


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

  }


  public double getlongitude()
  {
      return longitude;
  }

    public double getlatitude()
    {
        return latitude;
    }

    //region ***** GoogleMaps *****
    private void getLocation()
    {

        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 5);
        }

        else
        {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);

        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
            }
            else {


                Toast.makeText(this, "Until you grant the permission, we cannot display the location",
                        Toast.LENGTH_SHORT).show();

            }

        }

    }


    private void notifs()
    {

        final PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, DriverActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Channel description");

            notificationChannel.enableLights(true);

            notificationChannel.setLightColor(Color.CYAN);

            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});

            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        final NotificationCompat.Builder b = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);




        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.logo7_hdpi)
                .setContentTitle("New Order")
                .setContentText("You have a  new Order")
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");


        notificationManager.notify(1, b.build());
    }




}
