package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.PickupFragAdapter;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.viewModel.PickupFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class Pickup_Fragment extends Fragment {

    private RecyclerView pickup_shops; private PickupFragmentViewModel mPickupVM;
    private List<Restaurant> restaurantList;
    // TODO: 28-Jun-20

    public Pickup_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        mPickupVM=new PickupFragmentViewModel();
        restaurantList=new ArrayList<>();

        View rootView=inflater.inflate(R.layout.fragment_pickup_,container,false);

        PickupFragAdapter pfa= new PickupFragAdapter(restaurantList,"pickup");

        pfa.setListener(new PickupFragAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();
                /// TODO: 28-Jun-20
            }
        });

        pickup_shops=rootView.findViewById(R.id.pickup_shops);

        pickup_shops.setAdapter(pfa);

        LinearLayoutManager llm=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        pickup_shops.setLayoutManager(llm);

        return rootView;
    }
}