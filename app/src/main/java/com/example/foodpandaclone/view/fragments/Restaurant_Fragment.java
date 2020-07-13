package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.ResItemAdapter;
import com.example.foodpandaclone.model.Item;

import java.util.List;

public class Restaurant_Fragment extends Fragment {

    private TextView category_name;
    private RecyclerView restaurant_items;
    private List<Item> itemsR;

    public Restaurant_Fragment(List<Item> itemsR) {
        this.itemsR=itemsR;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_restaurant_, container, false);

        category_name=rootView.findViewById(R.id.category_name);
        restaurant_items=rootView.findViewById(R.id.restaurant_items);
        ResItemAdapter resItemAdapter=new ResItemAdapter(itemsR);

        resItemAdapter.setListener(new ResItemAdapter.Listerner() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(), "Card function not implemented yet", Toast.LENGTH_SHORT).show();
                //add this later
            }
        });

        restaurant_items.setAdapter(resItemAdapter);
        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        restaurant_items.setLayoutManager(llm);

        return rootView;
    }


}