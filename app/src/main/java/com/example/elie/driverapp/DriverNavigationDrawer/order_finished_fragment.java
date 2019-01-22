package com.example.elie.driverapp.DriverNavigationDrawer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.DialogInterface;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.example.elie.driverapp.Controller.DriverActivity;
import com.example.elie.driverapp.Fragment_order_information;
import com.example.elie.driverapp.Model.Backend.Backend_Factory;
import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import  com.example.elie.driverapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class order_finished_fragment extends Fragment
{

    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    Backend_Factory backend_factory=new Backend_Factory();
    FireBase_DSManager f=(FireBase_DSManager)backend_factory.getfactory();
    ArrayList<ClientRequest> clientslistf = new ArrayList<ClientRequest>(f.FinishedClients());

    private FinishOrderAdapter2 myAdapter;

    View myview;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        myview=inflater.inflate(R.layout.order_finished,container,false);
        RecyclerView listView = (RecyclerView) myview.findViewById(R.id.finish_order);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new FinishOrderAdapter2 (clientslistf,getContext());
        myAdapter.notifyDataSetChanged();
        listView.setAdapter(myAdapter);
        AccessContact();



        return myview;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void AccessContact()
    {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_MULTIPLE_PERMISSIONS);
            return;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);

            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void SaveContact(View view, ClientRequest clientRequest) {


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
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, "Get_Taxi") // Name of the person
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, clientRequest.getName()) // Name of the person
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

      /*//INSERT ADDRESS: FULL, STREET, CITY, REGION, POSTCODE, COUNTRY
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, m_szAddress)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.STREET, m_szStreet)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.CITY, m_szCity)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.REGION, m_szState)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE, m_szZip)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY, m_szCountry)
                //.withValue(StructuredPostal.TYPE, StructuredPostal.TYPE_WORK)
                .build());*/



        Uri newContactUri = null;
        //PUSH EVERYTHING TO CONTACTS
        try
        {
            ContentProviderResult[] res = getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
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
