package com.example.elie.driverapp.DriverNavigationDrawer;


import  com.example.elie.driverapp.R;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class order_list_fragment extends Fragment
{
    View myview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        myview=inflater.inflate(R.layout.order_list,container,false);
        return myview;
    }
}
