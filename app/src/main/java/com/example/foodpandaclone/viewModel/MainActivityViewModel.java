package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Restaurant;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    LiveData<List<Restaurant>> mRestaurantList; private Repository mRepo;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);
    }

    public LiveData<List<Restaurant>> getTheData() {

        mRestaurantList=mRepo.getRestaurantFromLocal();

        return mRestaurantList;
    }

    // TODO: 8/31/2020
    //Update the ViewPager adapter
}
