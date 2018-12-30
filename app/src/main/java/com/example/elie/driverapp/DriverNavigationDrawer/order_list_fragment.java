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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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


public class order_list_fragment extends Fragment
{

    ArrayList<ClientRequest> clientslist = new ArrayList<ClientRequest>(FireBase_DSManager.WaitingClients());

    private OrderAdapter myadapter;


    /*DriverActivity essai=(DriverActivity)getActivity();


    double longitude= essai.getlongitude();
    double latitude=essai.getlatitude();*/



    View myview;
   EditText filter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        myview=inflater.inflate(R.layout.order_list,container,false);
        RecyclerView listView = (RecyclerView) myview.findViewById(R.id.listorder);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        myadapter=new OrderAdapter(clientslist);
        setHasOptionsMenu(true);
        myadapter.notifyDataSetChanged();
        listView.setAdapter(myadapter);

        filter=(EditText) myview.findViewById(R.id.filtre);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    myadapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return myview;
    }





    public class MyViewHolder extends RecyclerView.ViewHolder{


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

        //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
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



    public class OrderAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable
    {

        @Override
        public long getItemId(int position) {
            return 0;
        }

        ArrayList<ClientRequest> list;
        ArrayList<ClientRequest> copylist;

        //ajouter un constructeur prenant en entrée une liste
        public OrderAdapter(ArrayList<ClientRequest> list) {
            this.list = list;
            copylist=new ArrayList<>(list);
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
        public int getItemCount() {
            return list.size();
        }

        @Override
        public Filter getFilter() {
            return exampleFilter;
        }

        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<ClientRequest> filteredList = new ArrayList<ClientRequest>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(copylist);
                } else {
                    String filterPattern = constraint.toString();

                    for (ClientRequest item : copylist) {
                        if (item.getDestination().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                copylist.clear();
                copylist.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };

    }



}
