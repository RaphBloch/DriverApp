package com.example.elie.driverapp.Model.DS;


import android.support.annotation.NonNull;
import java.util.*;

import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.Model.Entities.Driver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class FireBase_DSManager

{
    /***
     * Interface Action
     * @param <T> template : type of object for the implementation of the interface
     *      OnSuccess : It receives the name of the client that is the key
     *      OnFailure : It tells us if there is a failure in the loading and throws exception
     *      OnProgress : It tells us the progress of the load of the data with a message
     *
     */
    public interface Action<T>
    {
        void OnSuccess(T obj);

        void OnProgress(String status,double percent);

        void OnFailure(Exception exception);
    }


    public interface NotifyDataChange<T>
    {
        void OnDataChanged(T obj);

        void OnFailure(Exception exception);
    }

    // creation of my databaseReference
    static DatabaseReference ClientsRef;
    static DatabaseReference DriversRef;
    static List<ClientRequest> ClientsList;
    static List<Driver> DriversList;
    public static ChildEventListener clientRefChildEventListener;
    public static ChildEventListener driverRefChildEventListener;

    static
    {
        FirebaseDatabase data=FirebaseDatabase.getInstance();
        //The reference of my data of clients is Clients
        ClientsRef= data.getReference("Clients");
        DriversRef=data.getReference("Drivers");
        ClientsList=new ArrayList<ClientRequest>();
        DriversList = new ArrayList<Driver>();
    }


    /***
     *
     * @param driver
     */
    public   void addDriver(final  Driver  driver)
    {
        addDriverToFireBase(driver, new Action<String>() {
            @Override
            public void OnSuccess(String obj) {

            }

            @Override
            public void OnProgress(String status, double percent) {

            }

            @Override
            public void OnFailure(Exception exception) {

            }
        });
    }


    private static void addDriverToFireBase(final Driver driver,final Action<String> action)
    {
        String key=String.valueOf(driver.getID());
        DriversRef.child(key).setValue(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.OnSuccess(driver.getName());
                action.OnProgress("Load Driver data",100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.OnFailure(e);
                action.OnProgress("Error loading data",100);
            }
        });
    }



    public static void notifyToClientList(final NotifyDataChange<List<ClientRequest>> notifyDataChange)

    {
        if(notifyDataChange !=  null)
        {
            if(clientRefChildEventListener != null)
            {
                notifyDataChange.OnFailure(new Exception("No change"));
                return;
            }

            ClientsList.clear();

        clientRefChildEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                ClientRequest c = dataSnapshot.getValue(ClientRequest.class);
                String ID = dataSnapshot.getKey();
                c.setId(Integer.parseInt(ID));
                ClientsList.add(c);
                notifyDataChange.OnDataChanged(ClientsList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                notifyDataChange.OnFailure(databaseError.toException());
            }
        };

            ClientsRef.addChildEventListener(clientRefChildEventListener);
        }
    }




   public static void notifyToDriverList(final NotifyDataChange<List<Driver>> notifyDataChange)

    {
        if(notifyDataChange !=  null)

            if(driverRefChildEventListener != null)
            {
                notifyDataChange.OnFailure(new Exception("No change"));
                return;
            }

            DriversList.clear();


            driverRefChildEventListener= new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s)
                {
                    Driver d = dataSnapshot.getValue(Driver.class);
                    String ID= dataSnapshot.getKey();
                    d.setID(Integer.parseInt(ID));
                    DriversList.add(d);
                    notifyDataChange.OnDataChanged(DriversList);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyDataChange.OnFailure(databaseError.toException());

                }
            };

            DriversRef.addChildEventListener(driverRefChildEventListener);
        }


        public static void   stopNotifyToClientList()
         {

                    if  (clientRefChildEventListener  !=  null )
                            ClientsRef.removeEventListener(clientRefChildEventListener);
                    clientRefChildEventListener=null;

        }

    }









