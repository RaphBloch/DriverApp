package com.example.elie.driverapp.DriverNavigationDrawer;

import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    ArrayList<ClientRequest> copylist;
    private Context context;

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        LinearLayout linearLayout;
        TextView Name ;
        TextView Destination;
        TextView Distance ;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            Name= (TextView) itemView.findViewById(R.id.NameLayout);
            Destination=(TextView) itemView.findViewById(R.id.DestinationLayout);
            Distance=(TextView) itemView.findViewById(R.id.DistanceLayout);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.Listlayout);

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






    //ajouter un constructeur prenant en entrée une liste
    public OrderAdapter(ArrayList<ClientRequest> list,Context context) {
        this.list = list;
        this.context=context;
    }


    public void FilterList(ArrayList<ClientRequest> filterlist)
    {
        this.list=filterlist;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_layout,viewGroup,false);
        final Dialog mydialog= new Dialog(context);
        mydialog.setContentView(R.layout.fragment_order_action_layout);

        final MyViewHolder myViewHolder=new MyViewHolder(view);

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView mActionOk = mydialog.findViewById(R.id.action_ok);
                TextView mActionCancel = mydialog.findViewById(R.id.action_cancel);
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
