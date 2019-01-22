package com.example.elie.driverapp.Model.Backend;
import com.example.elie.driverapp.Model.Entities.*;



public interface Backend
{
        public void addDriver(Driver d);

        public interface Action<T>
        {
                void OnSuccess(T obj);

                void OnProgress(String status,double percent);

                void OnFailure(Exception exception);
        }


        public interface NotifyDataChange<T>
        {
                void OnDataChanged(T obj);


                void OnDataAdded(T obj);

                void OnFailure(Exception exception);
        }


        public  void notifyToClientList(final NotifyDataChange<ClientRequest> notifyDataChange);



        public void   stopNotifyToClientList();

       // public   void notifyToDriverList(final NotifyDataChange<Driver> notifyDataChange);


}
