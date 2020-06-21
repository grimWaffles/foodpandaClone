package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodpandaclone.R;


public class Pickup_Fragment extends Fragment {

    private RecyclerView pickup_shops;

    public Pickup_Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_pickup_,container,false);

        pickup_shops=(RecyclerView)rootView.findViewById(R.id.pickup_shops);

        //todo

        return rootView;
    }
}