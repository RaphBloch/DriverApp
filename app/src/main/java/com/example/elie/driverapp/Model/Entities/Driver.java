package com.example.elie.driverapp.Model.Entities;

import java.io.Serializable;

public class Driver implements Serializable
{

    //region ***** Fields *****

    private String Name;
    private int ID;
    private String UID;
    private String Phone;
    private String Mail;
    //endregion




    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    //region ***** GET/SET *****
    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    //endregion



    //region ***** CTOR *****
    public Driver()
    {
        Name="";
        Phone="06";
        ID=0;
        Mail="@";
    }

    public Driver(int i)
    {
        Name="meir";
        Phone="065";
        ID=i;
        Mail="@";
    }

    public  Driver(Driver driver)
    {
        this.Name=driver.getName();
        this.Phone=driver.getPhone();
        this.ID=driver.getID();
        this.Mail=driver.getMail();
    }


    public Driver(int id,String name,String mail,String phone)
    {
        ID=id;
        Name=name;
        Phone=phone;
        Mail=mail;
    }
    //endregion


    //region ***** Methods *****
    @Override
    public String toString() {
        return "Mr "+getName()+" ID: "+getID()+ "\n Your request  is being processed ";
    }

    //endregion
}
