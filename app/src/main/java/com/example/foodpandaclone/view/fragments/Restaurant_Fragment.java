package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodpandaclone.R;

public class Restaurant_Fragment extends Fragment {

    private TextView category_name;
    private RecyclerView restaurant_items;

    public Restaurant_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_restaurant_, container, false);

        category_name=rootView.findViewById(R.id.category_name);
        restaurant_items=rootView.findViewById(R.id.restaurant_items);




        return rootView;
    }
}