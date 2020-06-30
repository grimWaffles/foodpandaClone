package com.example.foodpandaclone.viewModel;

import com.example.foodpandaclone.databases.Repository;
import com.example.foodpandaclone.model.Restaurant;

import java.util.List;

public class DeliveryFragmentViewModel {

    private Repository mRepo;
    private List<Restaurant> restaurants;

    public DeliveryFragmentViewModel(){
        mRepo=new Repository();
        restaurants=mRepo.getRestaurantData();
    }

    public List<Restaurant> getDataFromVM(){



        return restaurants;
    }
}
