package com.example.foodpandaclone.viewModel;

import com.example.foodpandaclone.adapters.DiscountResAdapter;
import com.example.foodpandaclone.databases.Repository;
import com.example.foodpandaclone.model.Restaurant;

import java.util.List;

public class DeliveryFragmentViewModel {

    private Repository mRepo;
    private List<Restaurant> restaurants;
    private DiscountResAdapter discountResAdapter;

    public DeliveryFragmentViewModel(){
    }

    public DiscountResAdapter getDataFromVM(){
        mRepo=new Repository();
        discountResAdapter=mRepo.loadRestaurantData();

        return discountResAdapter;
    }
}
