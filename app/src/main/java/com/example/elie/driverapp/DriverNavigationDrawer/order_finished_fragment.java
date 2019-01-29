package com.example.elie.driverapp.DriverNavigationDrawer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.DialogInterface;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.example.elie.driverapp.Controller.DriverActivity;
import com.example.elie.driverapp.Fragment_order_information;
import com.example.elie.driverapp.Model.Backend.Backend_Factory;
import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import  com.example.elie.driverapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class order_finished_fragment extends Fragment
{

    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    Backend_Factory backend_factory=new Backend_Factory();
    FireBase_DSManager f=(FireBase_DSManager)backend_factory.getfactory();
    ArrayList<ClientRequest> clientslistf = new ArrayList<ClientRequest>(f.ClientsF());
    ArrayList<ClientRequest> clientslistc = new ArrayList<ClientRequest>(f.ClientsC());
    ArrayList<ClientRequest> myclientslistf = new ArrayList<ClientRequest>();
    ArrayList<ClientRequest> myclientslistc = new ArrayList<ClientRequest>();

    private FinishOrderAdapter2 myAdapter;
    private FinishOrderAdapter2 myAdapter2;
    DriverActivity d;

    View myview;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        myview=inflater.inflate(R.layout.order_finished,container,false);
        RecyclerView listView = (RecyclerView) myview.findViewById(R.id.finish_order);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        d=(DriverActivity)getContext();
        for (ClientRequest item : clientslistf)
        {
            if ( item.getDriverId() == (d.getD().getID()))
            {
                myclientslistf.add(item);
            }
        }

        myAdapter = new FinishOrderAdapter2 (myclientslistf,getContext());
        myAdapter.notifyDataSetChanged();
        listView.setAdapter(myAdapter);
        AccessContact();

        RecyclerView listView2 = (RecyclerView) myview.findViewById(R.id.Current_order);
        listView2.setLayoutManager(new LinearLayoutManager(getContext()));
        for (ClientRequest item : clientslistc)
        {
            if ( item.getDriverId() == (d.getD().getID()))
            {
                myclientslistc.add(item);
            }
        }
        myAdapter2 =new FinishOrderAdapter2(myclientslistc,getContext());
        myAdapter2.notifyDataSetChanged();
        listView2.setAdapter(myAdapter2);




        return myview;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void AccessContact()
    {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_MULTIPLE_PERMISSIONS);
            return;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);

            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



}
