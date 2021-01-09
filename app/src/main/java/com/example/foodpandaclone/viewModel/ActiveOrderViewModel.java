package com.example.foodpandaclone.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ActiveOrderViewModel extends AndroidViewModel {

    public static final String TAG="ActiveOrderViewModel";

    private Repository mRepo; private ScheduledExecutorService service=Executors.newSingleThreadScheduledExecutor(); private boolean serivceRunning=false;
    private int userID; private Runnable trackOrder;

    public ActiveOrderViewModel(@NonNull Application application) {
        super(application);
        mRepo=new Repository(application);
    }

    public LiveData<List<User>> getCurrentUser() { return mRepo.getUserListFromLocal(); }

    public LiveData<List<Order>> getCurrentOrders() { return mRepo.getPendingOrderlist(); }

    public LiveData<List<Rider>> getCurrentRider(){ return mRepo.getCurrentRider();}

    public void cancelOrder(String s) {
        mRepo.cancelOrder(s);
    }

    public void getUserOrders(int userID) {
        mRepo.getPendingOrdersFromFirebase(userID);
    }

    public void findRiderForOrder(final Order order) {

        new Thread(new Runnable() {
               @Override
               public void run() {
                   mRepo.getAvailableRiders(order);
               }
           }).start();
    }

    public void updateOrder(int riderID, int orderID) {
        mRepo.updateSenderIDinFirebase(riderID,orderID);
    }

    public void downloadRiderInformation(final int riderID) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mRepo.downloadRiderInfo(riderID);
            }
        }).start();
    }

    public void trackOrder(final Order order){

        if(!serivceRunning ){
            serivceRunning=true;
            trackOrder=new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"Tracking order");
                    getUserOrders(userID);
                }
            };
            service.schedule(trackOrder,6,TimeUnit.SECONDS);
        }
    }

    public void stopTracker(){
        if(serivceRunning){
            serivceRunning=false;
            service.shutdown();
        }
    }

    public void setUserID(int userID) {
        this.userID=userID;
    }
}
