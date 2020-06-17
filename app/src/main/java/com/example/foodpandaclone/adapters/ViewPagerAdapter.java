package com.example.foodpandaclone.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.foodpandaclone.view.Delivery_Fragment;
import com.example.foodpandaclone.view.Pickup_Fragment;
import com.example.foodpandaclone.view.Shop_Fragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new Delivery_Fragment();
            case 1:
                return new Pickup_Fragment();
            case 2:
                return new Shop_Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Delivery";
            case 1:
                return "Pickup";
            case 2:
                return "Stores";
        }
        return null;
    }

}
