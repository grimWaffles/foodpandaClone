package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Restaurant;

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

    public void increaseItemQuantity(Item item) {

        mRepo.increaseItemQuantity(item.getItemID(),item.getRestaurantID());

    }


}
