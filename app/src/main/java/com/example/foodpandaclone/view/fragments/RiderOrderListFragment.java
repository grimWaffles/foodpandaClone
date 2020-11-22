package com.example.foodpandaclone.view.fragments;

import android.app.Fragment;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.RiderOrderAdapter;
import com.example.foodpandaclone.model.Order;

import java.util.List;


public class RiderOrderListFragment extends Fragment {

    private RecyclerView recyclerView; private LinearLayoutManager linearLayoutManager; private RiderOrderAdapter adapter;
    private RiderOrderAdapter.OnOrderItemClick orderItemClick; private List<Order> orders;

    public RiderOrderListFragment(){

    }

    public RiderOrderListFragment(RiderOrderAdapter.OnOrderItemClick onOrderItemClick,List<Order> orders) {
        // Required empty public constructor

        this.orderItemClick=onOrderItemClick;
        this.orders=orders;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_rider_order_list, container, false);

        recyclerView=view.findViewById(R.id.recycler_view_rider);
        linearLayoutManager=new LinearLayoutManager(getContext());
        adapter=new RiderOrderAdapter(this.orderItemClick);
        adapter.setOrders(this.orders);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}