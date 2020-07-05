package com.example.foodpandaclone.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.DiscountResAdapter;
import com.example.foodpandaclone.adapters.PickupFragAdapter;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.view.activities.Restaurant_Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Delivery_Fragment extends Fragment {

    private RecyclerView treat_hobe,all_restaurants;
    private CardView panda_favorites;

    private List<Restaurant> restaurantList;

    private DatabaseReference ref;

    public Delivery_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        final View rootView= inflater.inflate(R.layout.fragment_delivery_,container,false);

        restaurantList=new ArrayList<>();

        ref= FirebaseDatabase.getInstance().getReference().child("Restaurant");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap: snapshot.getChildren()){
                    Restaurant res=new Restaurant();
                    res=snap.getValue(Restaurant.class);
                    res.setRestaurantID(snap.getKey());
                    restaurantList.add(res);
                }

                treat_hobe=rootView.findViewById(R.id.treat_hobe);
                DiscountResAdapter dra= new DiscountResAdapter(restaurantList);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                treat_hobe.setAdapter(dra); Log.d(" adapter","Adapter set");
                treat_hobe.setLayoutManager(linearLayoutManager);

                dra.setListener(new DiscountResAdapter.Listener() {
                    @Override
                    public void onClick(int position) {
                        //Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();
                        onCardClick(position);
                        Log.d("position of Card",Integer.toString(position));
                    }
                });

                PickupFragAdapter pfa= new PickupFragAdapter(restaurantList,"delivery");

                pfa.setListener(new PickupFragAdapter.Listener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();

                    }
                });

                all_restaurants=rootView.findViewById(R.id.all_restaurants);
                all_restaurants.setAdapter(pfa);
                all_restaurants.setLayoutManager(new LinearLayoutManager(getActivity()));

                dra.notifyDataSetChanged();

                pfa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        panda_favorites=rootView.findViewById(R.id.panda_favorites);

        panda_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    public void onCardClick(int position){
        Intent  intent=new Intent(getActivity(),Restaurant_Activity.class);
        intent.putExtra("restaurant",restaurantList.get(position));
        startActivity(intent);
    }
}