package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.DiscountResAdapter;
import com.example.foodpandaclone.adapters.PickupFragAdapter;
import com.example.foodpandaclone.databases.Repository;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.viewModel.DeliveryFragmentViewModel;
import com.example.foodpandaclone.viewModel.PickupFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class Delivery_Fragment extends Fragment {

    private RecyclerView treat_hobe,all_restaurants;
    private CardView panda_favorites;

    private DeliveryFragmentViewModel dfVM;

    public Delivery_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_delivery_,container,false);

        dfVM=new DeliveryFragmentViewModel();

        treat_hobe=rootView.findViewById(R.id.treat_hobe); all_restaurants=rootView.findViewById(R.id.all_restaurants);

        DiscountResAdapter dra= dfVM.getDataFromVM();

/*
        PickupFragAdapter pfa= new PickupFragAdapter(dfVM.getDataFromVM(),"delivery");

        pfa.setListener(new PickupFragAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();

            }
        });
*/
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        treat_hobe.setAdapter(dra); Log.d(" adapter","Adapter set");

        treat_hobe.setLayoutManager(linearLayoutManager);

/*
        all_restaurants=rootView.findViewById(R.id.all_restaurants);
        all_restaurants.setAdapter(pfa);
        all_restaurants.setLayoutManager(new LinearLayoutManager(getActivity()));
*/
        panda_favorites=rootView.findViewById(R.id.panda_favorites);

        panda_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}