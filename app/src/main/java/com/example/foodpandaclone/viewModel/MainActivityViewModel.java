package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.User;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    LiveData<List<Restaurant>> mRestaurantList; private Repository mRepo;

    public MainActivityViewModel(Application application) {
        super(application);

        mRepo=new Repository(application);
    }

    public LiveData<List<Restaurant>> getTheData() {
        return mRepo.getRestaurantFromLocal();
    }

    public LiveData<List<User>> getCurrentUser(){ return mRepo.getUserListFromLocal();}

    public void logoutCurrentUser() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mRepo.logoutCurrentUser();
                mRepo.deleteOrders();
            }
        }).start();
    }
}
