package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.PickupFragAdapter;


public class Pickup_Fragment extends Fragment {

    private RecyclerView pickup_shops; private String[] array;

    public Pickup_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        array=null;

        PickupFragAdapter pfa= new PickupFragAdapter(array);

        View rootView=inflater.inflate(R.layout.fragment_pickup_,container,false);
        pickup_shops=(RecyclerView)rootView.findViewById(R.id.pickup_shops);

        pickup_shops.setAdapter(pfa);

        LinearLayoutManager llm=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        pickup_shops.setLayoutManager(llm);

        return rootView;
    }
}