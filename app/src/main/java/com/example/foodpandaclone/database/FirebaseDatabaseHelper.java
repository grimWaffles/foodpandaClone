package com.example.foodpandaclone.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.dao.ItemDao;
import com.example.foodpandaclone.dao.RestaurantDao;
import com.example.foodpandaclone.dao.UserDao;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.RestaurantFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//this version only gets and receives restaurant objects for now
public class FirebaseDatabaseHelper {

    private DatabaseReference ref; private List<RestaurantFirebase> restaurants=new ArrayList<>();

    private RestaurantDao mRestaurantDao; private ItemDao mItemDao; private LocalDatabaseHelper db;

    public FirebaseDatabaseHelper(Application application){

        db=LocalDatabaseHelper.getDatabase(application);
        mRestaurantDao=db.restaurantDao();
        mItemDao=db.itemDao();
    }

    public void insertRestaurantData(RestaurantFirebase restaurant){

        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant").child(Integer.toString(restaurant.getRestaurantID()));
        ref.setValue(restaurant);
    }

    public void loadRestaurantDataFromFirebase(){

        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant");

        Log.d("Database navigated","True");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap: snapshot.getChildren()){

                    RestaurantFirebase restaurantFirebase= snap.getValue(RestaurantFirebase.class);
                    restaurants.add(restaurantFirebase);

                    Log.d("Size of list",Integer.toString(restaurants.size()));

                    insertToLocalDB(restaurantFirebase);
                }
                Log.d("Size of list fetched",Integer.toString(restaurants.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }

        });
    }

    private void insertToLocalDB(RestaurantFirebase rf) {

        Restaurant restaurant=new Restaurant(rf.getRestaurantID(),rf.getName(),rf.getPhoneNumber(),rf.getLatitude(),rf.getLongitude(),rf.getLocation(),rf.getDeliveryCost(),rf.getDiscount(),rf.getReviews(),rf.getRating());

        //Insert to Room Database
        mRestaurantDao.insertRestaurantToLocal(restaurant);

        for (Item item:rf.getItems()){
            mItemDao.insertItemToLocal(item);
        }

    }
}
