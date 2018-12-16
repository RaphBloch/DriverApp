package com.example.elie.driverapp.Controller;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elie.driverapp.Model.DS.FireBase_DSManager;
import com.example.elie.driverapp.Model.Entities.Driver;
import com.example.elie.driverapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity
{

    EditText Name;
    EditText ID;
    EditText Mail;
    EditText Phone;
    EditText Password1;
    EditText Password2;
    Button RegisterBtn;
    Button CancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FindViews();
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(Mail.getText().toString(),Password1.getText().toString());
                login();
                Driver d= new Driver( ID.getId(), Name.getText().toString().trim() ,Mail.getText().toString().trim() ,Phone.getText().toString().trim() );
                FireBase_DSManager fireBase_dsManager = new FireBase_DSManager();
                fireBase_dsManager.addDriver(d);
                ComponentName componentName = new ComponentName(RegisterActivity.this,DriverActivity.class);
                Intent myintent=new Intent();
                myintent.setComponent(componentName);
                startActivity(myintent);


            }
        });

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ComponentName componentName = new ComponentName(RegisterActivity.this,MainActivity.class);
                Intent myintent=new Intent();
                myintent.setComponent(componentName);
                startActivity(myintent);

            }
        });

    }

    private void FindViews()
    {
        Name=(EditText)findViewById(R.id.Name);
        Phone=(EditText) findViewById(R.id.Phone);
        ID=(EditText) findViewById(R.id.ID);
        Password1=(EditText)findViewById(R.id.Password1);
        Password2=(EditText)findViewById(R.id.Password2);
        Mail=(EditText)findViewById(R.id.Mail);
        RegisterBtn=(Button)findViewById(R.id.Register);
        CancelBtn=(Button)findViewById(R.id.Cancel);

    }


    private void login(){
        if (TextUtils.isEmpty(ID.getText().toString().trim())||TextUtils.isEmpty(Name.getText().toString().trim())||
                TextUtils.isEmpty(Phone.getText().toString().trim())||TextUtils.isEmpty(Mail.getText().toString().trim())||
                TextUtils.isEmpty(Password1.getText().toString().trim()) ||TextUtils.isEmpty(Mail.getText().toString().trim()))
        {
            ID.setError("Fields can't be Empty");
            Name.setError("Fields can't be Empty");
            Phone.setError("Fields can't be Empty");
            Mail.setError("Fields can't be Empty");
            Password1.setError("Fields can't be empty");
            Password2.setError("Fields can't be empty");
        }
        else if (!emailValidator(Mail.getText().toString()))
        {
            Mail.setError("Please Enter Valid Email Address");
        }
        else
        {
            Toast.makeText(this ,"Login Successful",Toast.LENGTH_SHORT).show();
        }

        if (Password1.getText().equals(Password2.getText()) == false)
            Password2.setError("The password are not the same");
    }

    //Email Validation Using Regex
    public boolean emailValidator (String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN ="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private Driver getDriver()
    {
        Driver d=new Driver();
        d.setPhone(Phone.getText().toString());
        d.setName(Name.getText().toString());
        d.setMail(Mail.getText().toString());
        d.setID(Integer.valueOf(ID.getText().toString()));

        return d;
    }


}
