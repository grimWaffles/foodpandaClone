package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodpandaclone.R;

public class Delivery_Fragment extends Fragment {

    private RecyclerView homemade,top_rated,all_restaurants;
    private CardView panda_favorites;

    public Delivery_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_delivery_,container,false);

        homemade=(RecyclerView)rootView.findViewById(R.id.homemade);
        top_rated=(RecyclerView)rootView.findViewById(R.id.top_rated);
        all_restaurants=(RecyclerView)rootView.findViewById(R.id.all_restaurants);
        panda_favorites=(CardView)rootView.findViewById(R.id.panda_favorites);

        //todo

        return rootView;
    }
}