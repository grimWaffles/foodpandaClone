package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodpandaclone.R;

public class Shop_Fragment extends Fragment {

    private RecyclerView stores;

    public Shop_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_shop_,container,false);

        stores=(RecyclerView) rootView.findViewById(R.id.stores);

        return rootView;
    }
}