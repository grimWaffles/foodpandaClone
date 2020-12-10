package com.example.foodpandaclone.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.service.LocationService;
import com.example.foodpandaclone.view.activity.ActiveOrder;

import java.util.List;

public class ActiveOrderViewModel extends AndroidViewModel {

    private Repository mRepo;

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
}
