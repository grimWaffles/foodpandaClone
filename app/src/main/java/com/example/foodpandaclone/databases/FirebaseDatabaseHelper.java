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


    public FirebaseDatabaseHelper(){}

    //Restaurant data API- called from repository class

    public void insertRestaurantData(RestaurantFirebase restaurant){

        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant").child(Integer.toString(restaurant.getRestaurantID()));
        ref.setValue(restaurant);
    }



    public void loadRestaurantData(){
        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant");

        Log.d("Database navigated","True");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap: snapshot.getChildren()){
                    RestaurantFirebase res= snap.getValue(RestaurantFirebase.class);
                    restaurants.add(res);
                    Log.d("Size of list",Integer.toString(restaurants.size()));
                }
                Log.d("Size of list fetched",Integer.toString(restaurants.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
