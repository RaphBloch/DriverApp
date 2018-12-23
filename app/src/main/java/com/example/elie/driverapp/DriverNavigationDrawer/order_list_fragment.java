package com.example.elie.driverapp.DriverNavigationDrawer;


import com.example.elie.driverapp.Model.Entities.ClientRequest;
import  com.example.elie.driverapp.R;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class order_list_fragment extends Fragment
{
    View myview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        myview=inflater.inflate(R.layout.order_list,container,false);
        ListView listView = (ListView) myview.findViewById(R.id.listorder);
        return myview;
    }

    public class OrderAdapter extends BaseAdapter
    {

        Context context;
        @Override
        public int getCount() {
            return 0;
        }

        @Nullable
        @Override
        public ClientRequest getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)


        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=  inflater.inflate(R.layout.order_layout, null);

            EditText Name = (EditText) convertView.findViewById(R.id.NameLayout);
            EditText Destination = (EditText) convertView.findViewById(R.id.DestinationLayout);
            EditText Distance = (EditText) convertView.findViewById(R.id.DistanceLayout);





            return convertView;


        }

    }

}
