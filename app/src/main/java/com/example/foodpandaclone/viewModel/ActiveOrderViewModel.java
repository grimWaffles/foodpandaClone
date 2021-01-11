package com.example.foodpandaclone.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
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

    public void trackOrder(final int orderID){

        Handler handler=new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(serivceRunning){
                    Log.d(TAG,"Tracking order");
                    mRepo.getOrderFromFirebase(orderID);
                }
            }
        }, 5000);
    }

    public void stopTracker(){
        serivceRunning=false;
    }

    public void setUserID(int userID) {
        this.userID=userID;
    }

    public void clearRider() {
        mRepo.deleteAllRiders();
    }
}
