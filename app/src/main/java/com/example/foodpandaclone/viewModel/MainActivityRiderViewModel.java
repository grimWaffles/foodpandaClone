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

public class MainActivityRiderViewModel extends AndroidViewModel {

    public static final String TAG="MainActivityRiderViewModel";

    private Repository mRepo;

    public MainActivityRiderViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);
        Log.d(TAG,"init");
    }

    public LiveData<List<Order>> getPendingOrders(){
        Log.d(TAG,"getPendingOrders()");
        return  mRepo.getPendingOrderlist();

    }

    public void getThisRidersOrder(int riderID) {
        mRepo.getRidersCurrentOrder(riderID);
        Log.d(TAG,"getThisRidersOrder");
    }

    public LiveData<List<User>> getCurrentUser() {
        return mRepo.getUserListFromLocal();
    }

    public void logoutUser(final User user) {
        mRepo.logoutCurrentUser(user);
    }

    public void getRiderInformation(int userID){
        mRepo.downloadRiderInfo(userID);
        Log.d(TAG,"downloadRiderInformation");
    }

    public LiveData<List<Rider>> getCurrentRider(){
        return mRepo.getCurrentRider();
    }

    public void downloadAllPendingOrdersFromFirebase(){
        mRepo.downloadAllPendingOrdersFromFirebase();
        Log.d(TAG,"downloadAllPendingOrdersFromFirebase");
    }

    public void downLoadUserInformationFromFirebase(int userID){
        mRepo.downloadUserInformationFromFirebase(userID);
        Log.d(TAG,"downloadUserInformation");
    }
}
