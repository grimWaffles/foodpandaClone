package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Restaurant;

import java.util.List;

public class MyCartViewModel extends AndroidViewModel {

    private Repository mRepo;

    public MyCartViewModel(@NonNull Application application) {
        super(application);
        mRepo=new Repository(application);
    }

    //add functions here

    public LiveData<List<Item>> getOrderItemsFromLocal(){
        return mRepo.getOrderItemsFromLocal();
    }

    public void decreaseItemFromOrder(int itemID, int restaurantID) {
        mRepo.decreaseItemQuantity(itemID,restaurantID);
    }


    public LiveData<List<Restaurant>> getRestaurantData() {
        return mRepo.getRestaurantFromLocal();
    }
}