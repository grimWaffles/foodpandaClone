package com.example.foodpandaclone.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.view.fragments.Restaurant_Fragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerResActivity extends FragmentPagerAdapter {

    List<Item> items; List<String> itemCategory;  List<Item> itemsToSend;

    public ViewPagerResActivity(FragmentManager fm, List<Item> resItems,List<String> itemCategory){
        super(fm);
        items=resItems;
        this.itemCategory=itemCategory;
        itemsToSend=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        for(Item item:items){
            if(item.getItemType().equals(itemCategory.get(position))){
                itemsToSend.add(item);
            }
        }
        return  new Restaurant_Fragment(itemsToSend);
    }

    @Override
    public int getCount() {
        return itemCategory.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return  itemCategory.get(position);
    }
}
