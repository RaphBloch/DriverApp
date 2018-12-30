package com.example.elie.driverapp.DriverNavigationDrawer;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import  com.example.elie.driverapp.R;

import java.util.ArrayList;

public class order_finished_fragment extends Fragment
{
    View myview;

    ArrayList<ClientRequest> clientslist = new ArrayList<ClientRequest>(FireBase_DSManager.FinishedClients());
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        myview=inflater.inflate(R.layout.order_finished,container,false);
        RecyclerView listView = (RecyclerView) myview.findViewById(R.id.listorder);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        order_finished_fragment.OrderAdapter myadapter=new order_finished_fragment.OrderAdapter(clientslist);
        myadapter.notifyDataSetChanged();
        listView.setAdapter(myadapter);
        return myview;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        EditText Name ;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            Name= (EditText) itemView.findViewById(R.id.NameFinished);


        }

        //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
        public void bind(ClientRequest myObject){
            Name.setText(myObject.getName());
        }
    }

    public class OrderAdapter extends RecyclerView.Adapter<order_finished_fragment.MyViewHolder>
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
        public order_finished_fragment.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.finished_layout,viewGroup,false);
            return new order_finished_fragment.MyViewHolder(view);
        }

        //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
        @Override
        public void onBindViewHolder(order_finished_fragment.MyViewHolder myViewHolder, int position) {
            ClientRequest myObject = list.get(position);
            myViewHolder.bind(myObject);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}
