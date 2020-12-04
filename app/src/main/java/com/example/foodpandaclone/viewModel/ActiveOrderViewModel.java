package com.example.foodpandaclone.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.service.LocationService;
import com.example.foodpandaclone.view.activity.ActiveOrder;

import java.util.List;

public class ActiveOrderViewModel extends AndroidViewModel {

    private Repository mRepo;  private Context mContext; private Order mOrder;

    public ActiveOrderViewModel(@NonNull Application application) {
        super(application);
        mRepo=new Repository(application);
    }

    public LiveData<List<User>> getCurrentUser() { return mRepo.getUserListFromLocal(); }

    public LiveData<List<Order>> getCurrentOrders() { return mRepo.getPendingOrderlist(); }

    public LiveData<List<Rider>> getCurrentRider(){ return mRepo.getCurrentRider();}

    public void getAvailableRiders() {
        mRepo.getAvailableRiders();
    }

    public void cancelOrder(String s) {
        mRepo.cancelOrder(s);
    }

    public void processOrder(int orderID) {
        mRepo.getOrderFromFirebase(orderID);
    }

    public void getUserOrders(int userID) {
        mRepo.getPendingOrdersFromFirebase(userID);
    }

    public void trackOrder(Order order) {

        mOrder=order;

        if(!order.getStatus().equals("completed") && order.getSenderID()==0){
            Log.d("aoVM","Called mRepo");
            mRepo.getAvailableRiders();
        }
    }

    //Starts the  Foreground service
    public void startService(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new  Intent(mContext, LocationService.class);
                intent.putExtra("inputExtra","Fuck me");

                ContextCompat.startForegroundService(mContext,intent);
            }
        }).start();
    }

    //Stop the Foreground Service
    public void stopTrackerService(){
        Intent intent=new Intent(mContext,LocationService.class);
        stopTrackerService();
    }

    public void updateOrder(int riderID) {
        mRepo.updateSenderIDinFirebase(riderID,mOrder.getOrderID());
    }
}
