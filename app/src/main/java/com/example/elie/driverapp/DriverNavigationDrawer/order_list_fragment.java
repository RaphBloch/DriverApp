package com.example.elie.driverapp.DriverNavigationDrawer;


import com.example.elie.driverapp.Controller.DriverActivity;
import com.example.elie.driverapp.Fragment_order_information;
import com.example.elie.driverapp.Model.Backend.Backend_Factory;
import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import  com.example.elie.driverapp.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class order_list_fragment extends Fragment  implements TextWatcher
{
    Backend_Factory backend_factory=new Backend_Factory();
    FireBase_DSManager f=(FireBase_DSManager)backend_factory.getfactory();

    ArrayList<ClientRequest> clientslist = new ArrayList<ClientRequest>(f.WaitingClients());

    private OrderAdapter myadapter;





    View myview;
    EditText myfilter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        myview=inflater.inflate(R.layout.order_list,container,false);
        RecyclerView listView = (RecyclerView) myview.findViewById(R.id.listorder);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        myadapter=new OrderAdapter(clientslist,getContext());
        myadapter.notifyDataSetChanged();
        listView.setAdapter(myadapter);



        myfilter = (EditText) myview.findViewById(R.id.filtre);
        myfilter.addTextChangedListener(this);





        return myview;
    }
    private void filter(String text)
    {
        ArrayList<ClientRequest> filteredList = new ArrayList<>();

        for (ClientRequest item : clientslist) {
            if (item.getDestination().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        myadapter.FilterList(filteredList);
    }


        @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        filter(s.toString());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        filter(s.toString());
    }

}



