package com.example.elie.driverapp.DriverNavigationDrawer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elie.driverapp.Controller.DriverActivity;
import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.Model.Entities.ClientRequestStatus;
import com.example.elie.driverapp.Model.Entities.Driver;
import com.example.elie.driverapp.MyFragmentDialog;
import com.example.elie.driverapp.R;

import java.util.ArrayList;

public class FinishOrderAdapter2 extends RecyclerView.Adapter<FinishOrderAdapter2.MyViewHolder>
{


    public ArrayList<ClientRequest> list;
    // ArrayList<ClientRequest> copylist;
    private Context context;

    DriverActivity d;

    //ajouter un constructeur prenant en entrée une liste
    public FinishOrderAdapter2(ArrayList<ClientRequest> list,Context context) {
        this.list = list;
        this.context=context;
        d=(DriverActivity) context;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        private LinearLayout item_order;
        TextView Name ;
        //TextView Destination;
        //TextView Distance ;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            Name= (TextView) itemView.findViewById(R.id.finish_name);
            //Destination=(TextView) itemView.findViewById(R.id.DestinationLayout);
            //Distance=(TextView) itemView.findViewById(R.id.DistanceLayout);
            item_order=(LinearLayout)itemView.findViewById(R.id.linear_finish_order);

        }




        public void bind(ClientRequest myObject){
            Name.setText(myObject.getName());
            //Destination.setText(myObject.getDestination());
            //Distance.setText(String.valueOf(getDistance(myObject) + "Km"));
        }

        private float getDistance(ClientRequest c) {
            float[] results = new float[2];
            Location.distanceBetween(c.getDepartureLatitude(),c.getDepartureLongitude(),
                    c.getArrivalLatitude(),c.getArrivalLongitude(),results);

            return (int)results[0]/1000;


        }
    }



    public void FilterList(ArrayList<ClientRequest> filterlist)
    {
        this.list=filterlist;
        notifyDataSetChanged();
    }

    Dialog mydialog;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.finished_layout,viewGroup,false);
        final MyViewHolder myViewHolder=new MyViewHolder(view);


        return new MyViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        ClientRequest myObject = list.get(position);
        myViewHolder.bind(myObject);





    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}






