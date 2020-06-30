package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.PickupFragAdapter;
import com.example.foodpandaclone.model.Restaurant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Pickup_Fragment extends Fragment {

    private RecyclerView pickup_shops;
    private List<Restaurant> restaurantList; private DatabaseReference ref;

    public Pickup_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {


        restaurantList=new ArrayList<>();

        final View rootView=inflater.inflate(R.layout.fragment_pickup_,container,false);

        ref= FirebaseDatabase.getInstance().getReference().child("Restaurant");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                restaurantList.clear();

                for(DataSnapshot snap: snapshot.getChildren()){
                    Restaurant res=new Restaurant();
                    res=snap.getValue(Restaurant.class);
                    res.setRestaurantID(snap.getKey());
                    restaurantList.add(res);
                }

                PickupFragAdapter pfa= new PickupFragAdapter(restaurantList,"pickup");

                pfa.setListener(new PickupFragAdapter.Listener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();

                    }
                });

                pickup_shops=rootView.findViewById(R.id.pickup_shops);
                pickup_shops.setAdapter(pfa);
                pickup_shops.setLayoutManager(new LinearLayoutManager(getActivity()));

                pfa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return rootView;
    }
}