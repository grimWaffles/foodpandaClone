package com.example.foodpandaclone.databases;

import android.util.Log;

import com.example.foodpandaclone.adapters.DiscountResAdapter;
import com.example.foodpandaclone.model.RestaurantFirebase;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    public List<RestaurantFirebase> restaurantData=new ArrayList<>(); FirebaseDatabaseHelper fireDB; String error;
    private DiscountResAdapter dra;

    public Repository(){

    }

    //FIREBASE QUERIES

    public List<RestaurantFirebase> loadRestaurantData(){

        fireDB=new FirebaseDatabaseHelper();

        fireDB.loadRestaurantData(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void dataInserted() {

            }

            @Override
            public void dataLoaded(List<RestaurantFirebase> restaurants) {

                //populates this  list  of  restaurants
                restaurantData=restaurants;
                Log.d("Size of list in REPO","Got adapter");

            }

            @Override
            public void dataUpdated() {

            }

            @Override
            public void dataDeleted() {

            }
        });

        return restaurantData;
    }
    
    public void addRestaurantData(RestaurantFirebase restaurant){

        fireDB=new FirebaseDatabaseHelper();

        fireDB.insertRestaurantData(restaurant,new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void dataInserted() {
            }

            @Override
            public void dataLoaded(List<RestaurantFirebase> restaurants) {

            }

            @Override
            public void dataUpdated() {

            }

            @Override
            public void dataDeleted() {

            }
        });
    }

    public void deleteRestaurantData(RestaurantFirebase restaurant){

        fireDB.deleteRestaurantData(restaurant, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void dataInserted() {

            }

            @Override
            public void dataLoaded(List<RestaurantFirebase> restaurants) {

            }

            @Override
            public void dataUpdated() {

            }

            @Override
            public void dataDeleted(){}
        });
    }

    public void updateRestaurantData(RestaurantFirebase restaurant){

        fireDB.updateRestaurantData(restaurant, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void dataInserted() {

            }

            @Override
            public void dataLoaded(List<RestaurantFirebase> restaurants) {

            }

            @Override
            public void dataUpdated() {

            }

            @Override
            public void dataDeleted() {

            }
        });
    }

    //LOCAL QUERIES

}
