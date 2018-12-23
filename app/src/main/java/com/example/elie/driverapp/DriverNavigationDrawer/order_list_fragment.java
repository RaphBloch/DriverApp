package com.example.elie.driverapp.DriverNavigationDrawer;


import com.example.elie.driverapp.Controller.DriverActivity;
import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import  com.example.elie.driverapp.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class order_list_fragment extends Fragment
{

    ArrayList<ClientRequest> clientslist = new ArrayList<ClientRequest>(FireBase_DSManager.ClientsList);
    /*DriverActivity essai=(DriverActivity)getActivity();


    double longitude= essai.getlongitude();
    double latitude=essai.getlatitude();*/



    View myview;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        myview=inflater.inflate(R.layout.order_list,container,false);
        RecyclerView listView = (RecyclerView) myview.findViewById(R.id.listorder);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        OrderAdapter myadapter=new OrderAdapter(clientslist);
        listView.setAdapter(myadapter);
        return myview;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{


        EditText Name ;
        EditText Destination;
        EditText Distance ;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            Name= (EditText) itemView.findViewById(R.id.NameLayout);
            Destination=(EditText) itemView.findViewById(R.id.DestinationLayout);
            Distance=(EditText) itemView.findViewById(R.id.DistanceLayout);

        }

        //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
        public void bind(ClientRequest myObject){
            Name.setText(myObject.getName());
            Destination.setText(myObject.getMail());
            Distance.setText("150 KM");
        }
    }

    public class OrderAdapter extends RecyclerView.Adapter<MyViewHolder>
    {

        @Override
        public long getItemId(int position) {
            return 0;
        }

        ArrayList<ClientRequest> list;

        //ajouter un constructeur prenant en entrée une liste
        public OrderAdapter(ArrayList<ClientRequest> list) {
            this.list = list;
        }

        //cette fonction permet de créer les viewHolder
        //et par la même indiquer la vue à inflater (à partir des layout xml)
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_layout,viewGroup,false);
            return new MyViewHolder(view);
        }

        //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            ClientRequest myObject = list.get(position);
            myViewHolder.bind(myObject);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

}
