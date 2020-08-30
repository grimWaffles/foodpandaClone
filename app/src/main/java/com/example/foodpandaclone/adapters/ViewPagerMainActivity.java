package com.example.foodpandaclone.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.foodpandaclone.view.fragments.Delivery_Fragment;
import com.example.foodpandaclone.view.fragments.Shop_Fragment;

public class ViewPagerMainActivity extends FragmentPagerAdapter {

    public ViewPagerMainActivity(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new Delivery_Fragment();
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
