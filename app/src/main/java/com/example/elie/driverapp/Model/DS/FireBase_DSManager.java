package com.example.elie.driverapp.Model.DS;


import android.support.annotation.NonNull;

import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.Model.Entities.Driver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    static ArrayList<ClientRequest> ClientsList;
    static ArrayList<Driver> DriversList;

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
     * Function : addClientRequest
     * @param Driver  the driver  to add
     * Meaning : calls the function that add the driver  to the firebase
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

    /***
     * Function : addDriverToFirebaseFireBase
     *
     * @param Driver A driver
     * @param action The interface of the firebase
     *
     *  The function must add a driver  to my firebase.
     *
     *  Explication :    There is the implementation of the the 3 functions of the interface.
     *      OnSuccess : It receives the ID of the driver that is the key
     *      OnFailure : It tells us if there is a failure in the loading and throws exception
     *      OnProgress : It tells us the progress of the load of the data with a message

     */
    private static void addDriverToFireBase(final Driver driver,final Action<String> action)
    {
        String key=String.valueOf(driver.getID());
        DriversRef.child(key).setValue(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.OnSuccess(driver.getName());
                action.OnProgress("Load Clientrequest data",100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.OnFailure(e);
                action.OnProgress("Error loading data",100);
            }
        });
    }











}
