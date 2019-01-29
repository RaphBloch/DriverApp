package com.example.elie.driverapp.DriverNavigationDrawer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elie.driverapp.Controller.DriverActivity;
import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.Model.Entities.ClientRequestStatus;
import com.example.elie.driverapp.R;

import java.util.ArrayList;

public class FinishOrderAdapter extends RecyclerView.Adapter<FinishOrderAdapter.MyViewHolder>
{


    public ArrayList<ClientRequest> list;
    // ArrayList<ClientRequest> copylist;
    private Context context;

    DriverActivity d;

    //ajouter un constructeur prenant en entrée une liste
    public FinishOrderAdapter(ArrayList<ClientRequest> list, Context context) {
        this.list = list;
        this.context=context;
        d=(DriverActivity) context;
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        private LinearLayout item_order;
        TextView Name ;
        TextView Status;
        Button CP;
        //TextView Distance ;

        //itemView est la vue correspondante à 1 cellule
        public MyViewHolder(View itemView) {
            super(itemView);

            Name= (TextView) itemView.findViewById(R.id.finish_name);
            Status= (TextView) itemView.findViewById(R.id.Status);
            CP=(Button) itemView.findViewById(R.id.CP);
            item_order=(LinearLayout)itemView.findViewById(R.id.linear_finish_order);

        }




        public void bind(ClientRequest myObject){
            Name.setText(myObject.getName());

            if(myObject.getStatus() == ClientRequestStatus._Current)
                Status.setText(" Current Trip");
            else
                Status.setText("Finished Order");
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
        myViewHolder.CP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"The contact is Added",Toast.LENGTH_LONG).show();

                SaveContact(list.get(position));


            }
        });

        if (myObject.getStatus() == ClientRequestStatus._Current)
        {


            mydialog= new Dialog(context);
            mydialog.setContentView(R.layout.dialog_fragment_order);
            mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



             myViewHolder.item_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView dialogName = mydialog.findViewById(R.id.dialog_Name);
                TextView dialogDestination = mydialog.findViewById(R.id.dialog_destination_adresse);
                //TextView dialogPosition = mydialog.findViewById(R.id.dialog_position_adresse);
                Button dialogCallBtn = mydialog.findViewById(R.id.dialog_button_call);
                Button dialogMessageBtn = mydialog.findViewById(R.id.dialog_button_Message);
                Button dialogMailBtn = mydialog.findViewById(R.id.dialog_button_Mail);
                Button dialogStartBtn = mydialog.findViewById(R.id.dialog_button_Start);
                Button dialogFinishBtn = mydialog.findViewById(R.id.dialog_button_Finish);


                //Toast.makeText(context ,"item n :"+ list.get(myViewHolder.getAdapterPosition()).getName()+" voila",
                //Toast.LENGTH_SHORT).show();
                dialogName.setText(list.get(position).getName());
                dialogDestination.setText(list.get(position).getDestination());
                dialogStartBtn.setText("Cancel");

                dialogStartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setCancelable(false);
                        builder.setIcon(R.drawable.logo7_hdpi);
                        builder.setTitle("Order");
                        builder.setMessage("Are you sure you want to cancel the order ");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FireBase_DSManager.ClientsRef.child(String.valueOf(list.get(position).getId())).child("driverId").setValue(0);
                                FireBase_DSManager.ClientsRef.child(String.valueOf(list.get(position).getId())).child("status").setValue(ClientRequestStatus._Waiting);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // finish();

                            }
                        });
                        builder.create().show();



                    }
                });

                dialogFinishBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        FireBase_DSManager.ClientsRef.child(String.valueOf(list.get(position).getId())).child("status").setValue(ClientRequestStatus._Finished);

                        Toast.makeText(context,"The order has Finished !",Toast.LENGTH_SHORT).show();
                        mydialog.dismiss();
                    }
                });

                dialogMessageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String num = list.get(position).getPhone();
                        int permissionCheck = ContextCompat.checkSelfPermission(context,Manifest.permission.SEND_SMS);
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(num, null, "Your order is start", null, null);
                            Toast.makeText(context, "you have send an SMS to : \n" + list.get(position).getName(), Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            //Toast.makeText(context,"you haven't permission",Toast.LENGTH_SHORT).show();
                            ActivityCompat.requestPermissions((Activity)context,new String[]{Manifest.permission.SEND_SMS},0);
                        }

                    }

                });

                dialogCallBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent (Intent.ACTION_CALL);

                        String num  = list.get(position).getPhone();
                        if (num.trim().isEmpty()){
                            Toast.makeText(context,"The client haven't a phone number !",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            i.setData(Uri.parse("tel:"+num));
                            if (ActivityCompat.checkSelfPermission((Activity)context,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                            {
                                ActivityCompat.requestPermissions((Activity)context,new String[]{Manifest.permission.CALL_PHONE},1);
                            }
                            else
                            {
                                context.startActivity(i);
                            }
                        }
                    }
                });

                dialogMailBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setData(Uri.parse("email"));
                        String mail = list.get(position).getMail();
                        String[] s={mail,"e@gmail.com"};
                        i.putExtra(Intent.EXTRA_EMAIL,s);
                        i.putExtra(Intent.EXTRA_SUBJECT,"Get Taxi Confirmaton");
                        i.putExtra(Intent.EXTRA_TEXT,"Your order is started");
                        i.setType("message/rfc822");
                        Intent chooser = Intent.createChooser(i,"Launch Email");
                        context.startActivity(chooser);
                    }
                });



                //Toast.makeText(context,list.get(myViewHolder.getAdapterPosition()).toString(),Toast.LENGTH_LONG).show();
                mydialog.show();
            }
        });
        }

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void SaveContact(ClientRequest clientRequest) {


        //Create a new contact entry!
        String szFullname = clientRequest.getName();
        //https://developer.android.com/reference/android/provider/ContactsContract.RawContacts
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        //INSERT NAME
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, szFullname) // Name of the person
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, clientRequest.getName()) // Name of the person
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, "Get_Taxi") // Name of the person
                .build());
        //INSERT PHONE
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,   rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, clientRequest.getPhone()) // Number of the person
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build()); //


        //INSERT EMAIL
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,   rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, clientRequest.getMail())
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build()); //





        Uri newContactUri = null;
        //PUSH EVERYTHING TO CONTACTS
        try
        {
            ContentProviderResult[] res = context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            if (res!=null && res[0]!=null) {
                newContactUri = res[0].uri;
            }
        }
        catch (RemoteException e)
        {
            // error
            newContactUri = null;
        }
        catch (OperationApplicationException e)
        {
            // error
            newContactUri = null;
        }



    }



}






