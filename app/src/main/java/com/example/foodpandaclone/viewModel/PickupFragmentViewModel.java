package com.example.foodpandaclone.viewModel;

import android.text.style.ReplacementSpan;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.foodpandaclone.databases.Repository;
import com.example.foodpandaclone.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class PickupFragmentViewModel extends ViewModel {

    List<Restaurant> restaurantList=new ArrayList<>(); Repository repository;

    public PickupFragmentViewModel(){
        repository=new Repository();
    }

    public List<Restaurant> getRestaurantList(){

        restaurantList=repository.restaurantData;
        Log.d("Size of list in VM",Integer.toString(restaurantList.size()));

        return restaurantList;
    }

}
