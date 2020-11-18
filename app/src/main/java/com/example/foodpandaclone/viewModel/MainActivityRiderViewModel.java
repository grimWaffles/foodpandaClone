package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.User;

import java.util.List;

public class MainActivityRiderViewModel extends AndroidViewModel {

    private Repository mRepo;

    public MainActivityRiderViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);
    }

    public LiveData<List<Order>> getPendingOrdersFromFirebase(){
        return  mRepo.getOrderlist();
    }

    public void checkForPendingOrders() {
        mRepo.checkForPendingOrders();
    }

    public LiveData<List<User>> getCurrentUser() {
        return mRepo.getUserListFromLocal();
    }

    public void logoutUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mRepo.logoutCurrentUser();
            }
        }).start();
    }
}
