package com.example.foodpandaclone.databases;

import android.app.Application;

import com.example.foodpandaclone.dao.ItemDao;
import com.example.foodpandaclone.dao.RestaurantDao;

public class Repository {

    private RestaurantDao mRestaurantDao; private ItemDao mItemDao; private LocalDatabaseHelper db;

    public Repository(Application application){
        db=LocalDatabaseHelper.getDatabase(application);
    }

    //Functions to get and receive data
    //todo
}
