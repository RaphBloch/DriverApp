package com.example.elie.driverapp.Controller;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import  android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elie.driverapp.DriverService;
import com.example.elie.driverapp.R;
import com.example.elie.driverapp.Model.DS.FireBase_DSManager;

import com.example.elie.driverapp.Model.Entities.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class MainActivity extends AppCompatActivity
{

    public static final String mypreference = "myKeyWords";
    public static final String Mail= "Mail";
    public static final String Pass = "Password";

    //region ***** Fields *****





    //public static int i=4;

    TextView SignIn;
    Button Login;
    EditText MyMail;
    EditText Password;
    SharedPreferences sharedPreferences;
     private FirebaseAuth auth = FirebaseAuth.getInstance();


    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindViews();



        SignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ComponentName componentName = new ComponentName(MainActivity.this,RegisterActivity.class);
                Intent myintent=new Intent();
                myintent.setComponent(componentName);
                startActivity(myintent);
            }
        });

        sharedPreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        if(sharedPreferences.contains(Mail))
            MyMail.setText(sharedPreferences.getString( Mail,""));
        if(sharedPreferences.contains(Pass))
            Password.setText(sharedPreferences.getString(Pass,""));

        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Store(v);

                /*if(MyMail.getText().toString().equals(sharedPreferences.getString(Mail,""))
                        && Password.getText().toString().equals(sharedPreferences.getString(Pass,"")))
                    GoToDriver();
                else*/
                    SignIn(MyMail.getText().toString().trim(),Password.getText().toString().trim());
            }
        });


    }

    public void Store(View view)
    {
        String mail=MyMail.getText().toString();
        String password=Password.getText().toString();

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Mail,mail);
        editor.putString(Pass,password);
        editor.commit();
        Toast.makeText(getBaseContext(),"DataStoredSuccessfully",Toast.LENGTH_SHORT).show();

    }
    /*public void Fetch(View view)
    {
       sharedPreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
       if(sharedPreferences.contains(Name))
           Pseudo.setText(sharedPreferences.getString(Name,""));
        if(sharedPreferences.contains(Pass))
            Password.setText(sharedPreferences.getString(Pass,""));
    }*/

    private void FindViews()
    {
        SignIn = (TextView) findViewById(R.id.SignIn);
        Login=(Button)findViewById(R.id.Login);
        Password=(EditText)findViewById(R.id.Password);
        MyMail=(EditText)findViewById(R.id.Pseudo);

    }


    private void clickme()
    {
        //startService(new Intent(MainActivity.this,DriverService.class));
        NotificationCompat.Builder myBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getResources().getString(R.string.Title_Notification))
                        .setContentText(getResources().getString(R.string.Describle_Notification));
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,myBuilder.build());
    }

    private void GoToDriver()
    {
        ComponentName componentName = new ComponentName(MainActivity.this,DriverActivity.class);
        Intent intent=new Intent();
        intent.putExtra("mail",MyMail.getText().toString());
        intent.setComponent(componentName);
        startActivity(intent);


    }

    private void SignIn(String mail,String password)
    {
        auth.signInWithEmailAndPassword(mail,password).
    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful())
            {
                GoToDriver();
                FirebaseUser user=auth.getCurrentUser();

        }
            else
                Toast.makeText(getBaseContext(),
                        "Authentification failed",Toast.LENGTH_SHORT).show();

        }
    });

    }

 }