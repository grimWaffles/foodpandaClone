package com.example.foodpandaclone.databases;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodpandaclone.model.RestaurantFirebase;
import com.google.android.gms.tasks.OnSuccessListener;
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


    public FirebaseDatabaseHelper(){
    }

    //interface
    public interface DataStatus{
        void dataInserted();
        void dataLoaded(List<RestaurantFirebase> restaurants);
        void dataUpdated();
        void dataDeleted();
    }

    //Restaurant data API- called from repository class

    public void insertRestaurantData(RestaurantFirebase restaurant, final DataStatus status){

        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant");
        String key=ref.push().getKey();
        restaurant.setRestaurantID(key);
        ref.child(key).setValue(restaurant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                status.dataInserted();
            }
        });
    }

    public void loadRestaurantData(final DataStatus status){
        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant");

        Log.d("Database navigated","True");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap: snapshot.getChildren()){
                    String key=snap.getKey();
                    RestaurantFirebase res= snap.getValue(RestaurantFirebase.class);
                    res.setRestaurantID(key);
                    restaurants.add(res);
                    Log.d("Size of list",Integer.toString(restaurants.size()));
                }
                Log.d("Size of list fetched",Integer.toString(restaurants.size()));
                status.dataLoaded(restaurants);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteRestaurantData(RestaurantFirebase restaurant, final DataStatus status){
        ref.child("Restaurant").child(restaurant.getRestaurantID()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                status.dataDeleted();
            }
        });
    }

    public void updateRestaurantData(RestaurantFirebase restaurant, final DataStatus status){
        ref.child("Restaurant").child(restaurant.getRestaurantID()).setValue(restaurant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            status.dataUpdated();
            }
        });
    }

    //add functions for stores and other shit




}
