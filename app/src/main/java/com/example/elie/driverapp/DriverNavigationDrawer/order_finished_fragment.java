package com.example.elie.driverapp.DriverNavigationDrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import  com.example.elie.driverapp.R;

public class order_finished_fragment extends Fragment
{
    View myview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        myview=inflater.inflate(R.layout.order_finished,container,false);
        return myview;
    }
}
