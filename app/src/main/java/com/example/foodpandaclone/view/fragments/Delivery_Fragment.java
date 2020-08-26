package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.DiscountResAdapter;
import com.example.foodpandaclone.adapters.PickupFragAdapter;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.RestaurantFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Delivery_Fragment extends Fragment {

    private RecyclerView treat_hobe,all_restaurants;
    private CardView panda_favorites;

    private List<RestaurantFirebase> restaurantList=new ArrayList<>();

    private DatabaseReference ref;

    private ProgressBar progressBar;

    private DiscountResAdapter dra;  private PickupFragAdapter pfa;

    public Delivery_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        final View rootView= inflater.inflate(R.layout.fragment_delivery_,container,false);

        //initializing the recycler views
        progressBar=rootView.findViewById(R.id.progress);
        panda_favorites=rootView.findViewById(R.id.panda_favorites);
        treat_hobe=rootView.findViewById(R.id.treat_hobe);
        all_restaurants=rootView.findViewById(R.id.all_restaurants);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);



        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                restaurantList.clear();
                RestaurantFirebase res; final List<Item> iList=new ArrayList<>();

                for(DataSnapshot snap: snapshot.getChildren()){
                    res=snap.getValue(RestaurantFirebase.class);
                    restaurantList.add(res);
                }

                //adding adapters
                dra= new DiscountResAdapter(restaurantList);
                treat_hobe.setAdapter(dra); Log.d(" adapter","Adapter set");


                //adding adapters
                pfa= new PickupFragAdapter(restaurantList,"delivery");
                all_restaurants.setAdapter(pfa);

                //notify data changed
                dra.notifyDataSetChanged();
                pfa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progressBar.setVisibility(View.GONE);


        //onClick functionality and layout managers

        treat_hobe.setLayoutManager(linearLayoutManager);  all_restaurants.setLayoutManager(new LinearLayoutManager(getActivity()));

        panda_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}