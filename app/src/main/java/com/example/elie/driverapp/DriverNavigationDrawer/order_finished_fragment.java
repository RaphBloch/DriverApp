package com.example.elie.driverapp.DriverNavigationDrawer;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
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


    Backend_Factory backend_factory=new Backend_Factory();
    FireBase_DSManager f=(FireBase_DSManager)backend_factory.getfactory();
    ArrayList<ClientRequest> clientslistf = new ArrayList<ClientRequest>(f.FinishedClients());
    private FinishOrderAdapter2 myAdapter;

    View myview;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        myview=inflater.inflate(R.layout.order_finished,container,false);
        RecyclerView listView = (RecyclerView) myview.findViewById(R.id.finish_order);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new FinishOrderAdapter2 (clientslistf,getContext());
        myAdapter.notifyDataSetChanged();
        listView.setAdapter(myAdapter);


        return myview;
    }




}
