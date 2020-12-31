package com.example.foodpandaclone.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodpandaclone.database.LocalDatabaseHelper;
import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivityRiderViewModel extends AndroidViewModel {

    public static final String TAG="MainActivityRiderViewModel";

    //Repository
    private Repository mRepo;

    //Mutable LiveData
    private MutableLiveData<List<User>> userList; private MutableLiveData<List<Rider>> riderList; private MutableLiveData<List<Order>> orderList;

    //Other Variables
    private boolean riderInfoIsDownLoading=false; private boolean riderOrderDownloading=false; private int orderCounter=0;
    private Handler handler=new Handler(Looper.getMainLooper());

    public MainActivityRiderViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);
        Log.d(TAG,"init");

    }

    public LiveData<List<User>> getCurrentUser() {

        return mRepo.getUserListFromLocal();
    }


    public LiveData<List<Rider>> getCurrentRider(){

        return mRepo.getCurrentRider();
    }

    public LiveData<List<Order>> getPendingOrders(){

        return mRepo.getPendingOrderlist();
    }

    public void downloadThisRidersOrder(int riderID) {

        Log.d(TAG,"getThisRidersOrder");
        mRepo.getRidersCurrentOrder(riderID);

    }

    public void logoutUser(final User user) {
        mRepo.logoutCurrentUser(user);
    }

    public void downloadRiderInformation(final int userID){

        mRepo.downloadRiderInfo(userID);
        Log.d(TAG,"downloadedRiderInformation");
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
