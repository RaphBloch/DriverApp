package com.example.elie.driverapp.Model.DS;


import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.*;

import com.example.elie.driverapp.Model.Backend.Backend;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.Model.Entities.ClientRequestStatus;
import com.example.elie.driverapp.Model.Entities.Driver;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.*;

import java.util.ArrayList;


public class FireBase_DSManager implements Backend

{
    /***
     * Interface Action
     * @param <T> template : type of object for the implementation of the interface
     *      OnSuccess : It receives the name of the client that is the key
     *      OnFailure : It tells us if there is a failure in the loading and throws exception
     *      OnProgress : It tells us the progress of the load of the data with a message
     *
     */


    // creation of my databaseReference
    public static DatabaseReference ClientsRef;
    public static Driver CurrentDriver;
    public static DatabaseReference DriversRef;
    public static List<ClientRequest> ClientsList;
    public static List<Driver> DriversList;
    public static ChildEventListener clientRefChildEventListener;
    public static ChildEventListener driverRefChildEventListener;



    static
    {
        FirebaseDatabase data=FirebaseDatabase.getInstance();
        FirebaseAuth  auth=FirebaseAuth.getInstance();
        //The reference of my data of clients is Clients
        DriversList = new ArrayList<Driver>();
        ClientsRef= data.getReference("Clients");
        DriversRef=data.getReference("Drivers");
        ClientsList=new ArrayList<ClientRequest>();

    }

    public FireBase_DSManager()

    {
        if (ClientsRef!=null)
        {
            Log.d("ESSAI", "test");
        }
        if (DriversRef!=null)
        {
            Log.d("ESSAI", "test");
        }

        CurrentDriver=new Driver();
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


    private  void addDriverToFireBase(final Driver driver,final Action<String> action)
    {
        String key=driver.getUID();
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



    public  void notifyToClientList(final NotifyDataChange<ClientRequest> notifyDataChange)

    {




       ClientsRef.orderByChild("dataTime").startAt(Calendar.getInstance().getTime().getTime()).addChildEventListener(  new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                ClientRequest c = dataSnapshot.getValue(ClientRequest.class);
                String ID = dataSnapshot.getKey();
                c.setId(Integer.parseInt(ID));
                ClientsList.add(c);
                notifyDataChange.OnDataAdded(c);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)

            {

                ClientRequest c=dataSnapshot.getValue(ClientRequest.class);
                String ID = dataSnapshot.getKey();
                c.setId(Integer.parseInt(ID));
                for (int i=0; i < ClientsList.size() ; i++)
                {
                    if ( ClientsList.get(i).getId() == c.getId() )
                    {
                        ClientsList.set(i,c);
                        break;
                    }
                }
                notifyDataChange.OnDataChanged(c);

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
        });
 //ClientsRef.addChildEventListener(clientRefChildEventListener);

    }



    public  void notifyToDriverList(final NotifyDataChange<Driver> notifyDataChange)

    {


        driverRefChildEventListener=  new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                 Driver d  = dataSnapshot.getValue(Driver.class);
                String ID = dataSnapshot.getKey();
                d.setID(Integer.parseInt(ID));
                DriversList.add(d);
                notifyDataChange.OnDataAdded(d);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)

            {
                Driver d=dataSnapshot.getValue(Driver.class);
                String ID = dataSnapshot.getKey();
                d.setID(Integer.parseInt(ID));
                for (int i=0; i < DriversList.size() ; i++)
                {
                    if ( DriversList.get(i).getID() == d.getID() )
                    {
                        DriversList.set(i,d);
                        break;
                    }
                }
                notifyDataChange.OnDataChanged(d);

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

        DriversRef.addChildEventListener(driverRefChildEventListener);

    }



    public   ArrayList<ClientRequest> WaitingClients()
    {

        ArrayList<ClientRequest> mylist=new ArrayList<>();

        for (ClientRequest  c : ClientsList )
        {
            if(c.getStatus()== ClientRequestStatus._Waiting)
                mylist.add(c);
        }


        return mylist;

    }

    public   ArrayList<Driver> drivers()
    {

        return (ArrayList)DriversList;

    }



    public ArrayList<ClientRequest> FinishedClients()
    {

        ArrayList<ClientRequest> mylist=new ArrayList<>();

        for (ClientRequest  c : ClientsList )
        {
            if(c.getStatus()== ClientRequestStatus._Finished )
                mylist.add(c);
        }


        return mylist;

    }





        public void   stopNotifyToClientList()
         {

                    if  (clientRefChildEventListener  !=  null )
                            ClientsRef.removeEventListener(clientRefChildEventListener);
                    clientRefChildEventListener=null;

        }





    }









