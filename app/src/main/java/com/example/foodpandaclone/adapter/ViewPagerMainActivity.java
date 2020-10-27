package com.example.foodpandaclone.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.view.fragments.MainRestaurant_Fragment;
import com.example.foodpandaclone.view.fragments.Shop_Fragment;

import java.util.List;

public class ViewPagerMainActivity extends FragmentPagerAdapter {

    private List<Restaurant> restaurants;

    public ViewPagerMainActivity(FragmentManager fm, List<Restaurant> restaurants) {
        super(fm);
        this.restaurants=restaurants;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new MainRestaurant_Fragment(restaurants);
            case 1:
                return new Shop_Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Delivery";
            case 1:
                return "Stores";
        }
        return null;
    }

}
