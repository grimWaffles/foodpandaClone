package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.RestaurantItemAdapter;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.viewModel.RestaurantActivityViewModel;

import java.util.List;

public class Restaurant_Item_Fragment extends Fragment {

    private TextView category_name;
    private RecyclerView restaurant_items;
    private List<Item> itemsR;
    private RestaurantActivityViewModel raVM;

    public Restaurant_Item_Fragment(List<Item> itemsR) {
        this.itemsR=itemsR;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_restaurant_, container, false);

        raVM=new ViewModelProvider(getActivity()).get(RestaurantActivityViewModel.class);

        category_name=rootView.findViewById(R.id.category_name);
        restaurant_items=rootView.findViewById(R.id.restaurant_items);
        final RestaurantItemAdapter resItemAdapter=new RestaurantItemAdapter(itemsR);

        resItemAdapter.setListener(new RestaurantItemAdapter.Listerner() {
            @Override
            public void onClick(final int position) {
                Toast.makeText(getActivity(), "Added to cart!", Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        raVM.increaseItemQuantity(itemsR.get(position));

                    }
                }).start();

                //resItemAdapter.notifyDataSetChanged();
            }
        });

        restaurant_items.setAdapter(resItemAdapter);
        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        restaurant_items.setLayoutManager(llm);

        return rootView;
    }


}