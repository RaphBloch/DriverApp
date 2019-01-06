package com.example.elie.driverapp.DriverNavigationDrawer;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.elie.driverapp.Controller.DriverActivity;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.R;

import java.util.ArrayList;

public class FinishOrderAdapter extends RecyclerView.Adapter<FinishOrderAdapter.MyViewHolder> {



    public ArrayList<ClientRequest> list;
    private Context context;
    DriverActivity d;

    //ajouter un constructeur prenant en entrée une liste
    public FinishOrderAdapter (ArrayList<ClientRequest> list,Context context) {
        this.list = list;
        this.context=context;
        d=(DriverActivity) context;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private LinearLayout item_finish_order;
        TextView Name ;
        TextView Destination;
        TextView Position;
        TextView Distance ;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            Name= (TextView) itemView.findViewById(R.id.finish_name);
            Destination=(TextView) itemView.findViewById(R.id.finish_destination);
            Distance=(TextView) itemView.findViewById(R.id.finish_distance);
            Position=(TextView)itemView.findViewById(R.id.finish_position) ;
            item_finish_order=(LinearLayout)itemView.findViewById(R.id.list_finish_order_layout);

        }




        public void bind(ClientRequest myObject){
            Name.setText(myObject.getName());
            //Position.setText(d.);
            Destination.setText(myObject.getDestination());
            Distance.setText(String.valueOf(getDistance(myObject) + "Km"));
        }

        private float getDistance(ClientRequest c) {
            float[] results = new float[2];
            Location.distanceBetween(c.getDepartureLatitude(),c.getDepartureLongitude(),
                    c.getArrivalLatitude(),c.getArrivalLongitude(),results);

            return (int)results[0]/1000;


        }
    }


    Dialog mydialog;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.finished_layout,parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public int getItemCount() {
        return 0;
    }


}
