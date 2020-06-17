package com.example.foodpandaclone.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodpandaclone.R;

public class Shop_Fragment extends Fragment {

    public Shop_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_shop_,container,false);
    }
}