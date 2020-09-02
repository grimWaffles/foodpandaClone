package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Restaurant;
import com.google.firebase.database.core.Repo;

import java.util.List;


public class RestaurantActivityViewModel extends AndroidViewModel {

    private Repository mRepo;
    
    public RestaurantActivityViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);
    }

    public LiveData<Restaurant> getSingleRestaurantData(int resID){

        return mRepo.getSingleRestaurant(resID);
    }

    public LiveData<List<Item>> getRestaurantItems(int id){

        return mRepo.getItemsFromLocal(id);
    }

    // TODO: 8/31/2020
    //Get restaurant according to intent
}
