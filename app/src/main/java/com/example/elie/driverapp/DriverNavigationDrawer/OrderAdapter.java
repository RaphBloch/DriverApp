package com.example.elie.driverapp.DriverNavigationDrawer;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>
{


    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);

    }

    private  static OnItemClickListener clickListener;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {


        TextView Name ;
        TextView Destination;
        TextView Distance ;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            Name= (TextView) itemView.findViewById(R.id.NameLayout);
            Destination=(TextView) itemView.findViewById(R.id.DestinationLayout);
            Distance=(TextView) itemView.findViewById(R.id.DistanceLayout);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v,getAdapterPosition());
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


    ArrayList<ClientRequest> list;
    ArrayList<ClientRequest> copylist;

    //ajouter un constructeur prenant en entrée une liste
    public OrderAdapter(ArrayList<ClientRequest> list) {
        this.list = list;
    }


    public void FilterList(ArrayList<ClientRequest> filterlist)
    {
        this.list=filterlist;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener myclickListener) {
        clickListener = myclickListener;
    }


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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
