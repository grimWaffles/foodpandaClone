package com.example.foodpandaclone.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.RestaurantAdapter;
import com.example.foodpandaclone.model.Restaurant;

import java.util.List;

public class MainRestaurant_Fragment extends Fragment {

    private RecyclerView treat_hobe,all_restaurants;
    private CardView panda_favorites;

    private List<Restaurant> restaurantList;

    private RestaurantAdapter dra;  private RestaurantAdapter ara;

    public MainRestaurant_Fragment(List<Restaurant> restaurants) {
        // Required empty public constructor
        this.restaurantList=restaurants;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        final View rootView= inflater.inflate(R.layout.fragment_delivery_,container,false);

        //initializing the recycler views
        //panda_favorites=rootView.findViewById(R.id.panda_favorites);

        treat_hobe=rootView.findViewById(R.id.treat_hobe);
        all_restaurants=rootView.findViewById(R.id.all_restaurants);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        //adding adapters
        dra= new RestaurantAdapter(restaurantList,0);
        treat_hobe.setAdapter(dra); Log.d(" adapter","Adapter set");

        ara = new RestaurantAdapter(restaurantList,1);
        all_restaurants.setAdapter(ara);

        //notify data changed
        dra.notifyDataSetChanged();
        ara.notifyDataSetChanged();

        //onClick functionality and layout managers
        treat_hobe.setLayoutManager(linearLayoutManager);  all_restaurants.setLayoutManager(new LinearLayoutManager(getActivity()));

       /** panda_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();
            }
        });**/

        return rootView;
    }
}