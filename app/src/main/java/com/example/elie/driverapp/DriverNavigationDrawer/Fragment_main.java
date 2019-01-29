package com.example.elie.driverapp.DriverNavigationDrawer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.elie.driverapp.R;

public class Fragment_main extends Fragment
{

    private Button order_btn;
    private Button gallery_btn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,true);

        order_btn = (Button) view.findViewById(R.id.main_order_btn);
        gallery_btn = (Button) view.findViewById(R.id.main_gallery_btn);

        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity()," fr 1",Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.replace(R.id.content_frame, new order_list_fragment());
                ft.commit();


            }
        });
        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity()," fr 1",Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.replace(R.id.content_frame, new order_finished_fragment());
                ft.commit();


            }
        });


        return view;

    }
}
