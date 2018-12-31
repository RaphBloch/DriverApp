package com.example.elie.driverapp.DriverNavigationDrawer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.MyFragmentDialog;
import com.example.elie.driverapp.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>
{


    ArrayList<ClientRequest> list;
   // ArrayList<ClientRequest> copylist;
    private Context context;


    //ajouter un constructeur prenant en entrée une liste
    public OrderAdapter(ArrayList<ClientRequest> list,Context context) {
        this.list = list;
        this.context=context;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder 
    {

        private LinearLayout item_order;
        TextView Name ;
        TextView Destination;
        TextView Distance ;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            Name= (TextView) itemView.findViewById(R.id.NameLayout);
            Destination=(TextView) itemView.findViewById(R.id.DestinationLayout);
            Distance=(TextView) itemView.findViewById(R.id.DistanceLayout);
            item_order=(LinearLayout)itemView.findViewById(R.id.item_order_id);

        }




        public void bind(ClientRequest myObject){
            Name.setText(myObject.getName());
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









    public void FilterList(ArrayList<ClientRequest> filterlist)
    {
        this.list=filterlist;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.order_layout,viewGroup,false);
        final MyViewHolder myViewHolder=new MyViewHolder(view);

        final Dialog mydialog= new Dialog(context);
        mydialog.setContentView(R.layout.dialog_fragment_order);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        myViewHolder.item_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView dialogName = mydialog.findViewById(R.id.dialog_Name);
                TextView dialogDestination = mydialog.findViewById(R.id.dialog_destination_adresse);
                TextView dialogPosition = mydialog.findViewById(R.id.dialog_position_adresse);
                Button dialogCallBtn = mydialog.findViewById(R.id.dialog_button_call);
                Button dialogMessageBtn = mydialog.findViewById(R.id.dialog_button_Message);
                Button dialogMailBtn = mydialog.findViewById(R.id.dialog_button_Mail);
                Button dialogStartBtn = mydialog.findViewById(R.id.dialog_button_Start);
                Button dialogFinishBtn = mydialog.findViewById(R.id.dialog_button_Finish);

                Toast.makeText(context ,"item n :"+ String.valueOf(myViewHolder.getAdapterPosition())+" voila",
                        Toast.LENGTH_SHORT).show();
                //dialogName.setText(list.get(myViewHolder.getAdapterPosition()).getName());


                /*
                *TextView mActionOk = mydialog.findViewById(R.id.action_ok);
                TextView mActionCancel = mydialog.findViewById(R.id.action_cancel);

                * mActionOk = view.findViewById(R.id.action_ok);
        mActionCancel = view.findViewById(R.id.action_cancel);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydialog.dismiss();
                    }
                });

                mActionOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    mydialog.dismiss();
                    }
                });

                * */
                //Toast.makeText(context,list.get(myViewHolder.getAdapterPosition()).toString(),Toast.LENGTH_LONG).show();
                mydialog.show();
            }
        });


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
