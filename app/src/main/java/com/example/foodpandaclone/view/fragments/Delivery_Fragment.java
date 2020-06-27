package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.DiscountResAdapter;
import com.example.foodpandaclone.adapters.PickupFragAdapter;
import com.example.foodpandaclone.model.Restaurant;

import java.util.List;

public class Delivery_Fragment extends Fragment {

    private RecyclerView treat_hobe,all_restaurants;
    private CardView panda_favorites;
    private List<Restaurant> mRestaurants;

    public Delivery_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_delivery_,container,false);

        DiscountResAdapter dra=new DiscountResAdapter(mRestaurants);
        dra.setListener(new DiscountResAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();
                /// TODO: 28-Jun-20
            }
        });

        PickupFragAdapter pfa= new PickupFragAdapter(mRestaurants,"delivery");

        pfa.setListener(new PickupFragAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();
                /// TODO: 28-Jun-20
            }
        });

        treat_hobe=rootView.findViewById(R.id.treat_hobe);
        treat_hobe.setAdapter(dra);


        all_restaurants=(RecyclerView)rootView.findViewById(R.id.all_restaurants);
        all_restaurants.setAdapter(pfa);

        panda_favorites=(CardView)rootView.findViewById(R.id.panda_favorites);
        panda_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();
                /// TODO: 28-Jun-20
            }
        });

        // TODO: 28-Jun-20

        return rootView;
    }
}