package com.example.foodpandaclone.databases;

import androidx.annotation.NonNull;

import com.example.foodpandaclone.model.Restaurant;
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

    private DatabaseReference ref; private List<Restaurant> restaurants; private String errorMessage;

    public FirebaseDatabaseHelper(){
        this.ref= FirebaseDatabase.getInstance().getReference();
        this.restaurants=new ArrayList<>();
        this.errorMessage="";
    }

    //interface
    public interface DataStatus{
        void dataInserted(String errorMessage);
        void dataLoaded(List<Restaurant> restaurants,String errorMessage);
        void dataUpdated(String errorMessage);
        void dataDeleted(String errorMessage);
    }

    public void insertRestaurantData(Restaurant restaurant, final DataStatus status){
        String key=ref.push().getKey();
        restaurant.setPrimaryKey(key);
        ref.child("Restaurant").child(key).setValue(restaurant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                errorMessage="Successfully inserted restaurant data";
                status.dataInserted(errorMessage);
            }
        });
    }

    public void loadRestaurantData(final DataStatus status){
        ref.child("Restaurant");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot snap: snapshot.getChildren()){
                        Restaurant res= snap.getValue(Restaurant.class);
                        restaurants.add(res);
                    }
                }
                errorMessage="Successfully loaded restaurant data";
                status.dataLoaded(restaurants,errorMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorMessage="Failed to load restaurants";
                status.dataLoaded(restaurants,errorMessage);
            }
        });
    }

    public void deleteRestaurantData(Restaurant restaurant,final DataStatus status){
        ref.child("Restaurant").child(restaurant.getPrimaryKey()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                errorMessage="Successfully deleted restaurant data";
                status.dataDeleted(errorMessage);
            }
        });
    }

    public void updateRestaurantData(Restaurant restaurant,final DataStatus status){
        ref.child("Restaurant").child(restaurant.getPrimaryKey()).setValue(restaurant).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                errorMessage="Successfully updated restaurant data";
                status.dataUpdated(errorMessage);
            }
        });
    }

    //add functions for stores and other shit




}
