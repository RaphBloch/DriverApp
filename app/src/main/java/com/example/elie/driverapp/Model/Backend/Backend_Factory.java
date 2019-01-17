package com.example.elie.driverapp.Model.Backend;
import com.example.elie.driverapp.Model.DS.*;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.Model.Entities.Driver;


public class Backend_Factory

{
    Backend instance=new FireBase_DSManager();
    /***
     * Function : getFactory
     * @return a unique instance of Backend that is FireBase_DBManager
     */
    public Backend getfactory()
    {
        if (instance == null)
            instance = new FireBase_DSManager();

        return instance;
    }
}
