package com.example.elie.driverapp.Model.Backend;
import com.example.elie.driverapp.Model.DS.*;
import com.example.elie.driverapp.Model.Entities.ClientRequest;
import com.example.elie.driverapp.Model.Entities.Driver;


public   class Backend_Factory

{



    /***
     * Function : getFactory
     * @return a unique instance of Backend that is FireBase_DBManager
     */
    public    static   Backend getfactory()

    {
        return  FireBase_DSManager.getFireBase_dsManager();

    }
}
