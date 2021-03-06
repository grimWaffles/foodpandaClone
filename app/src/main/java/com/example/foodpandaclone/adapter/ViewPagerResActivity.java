package com.example.foodpandaclone.adapter;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.view.fragment.Restaurant_Item_Fragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerResActivity extends FragmentPagerAdapter {

    List<Item> items; List<String> itemCategory;

    public ViewPagerResActivity(FragmentManager fm, List<Item> resItems){
        super(fm);
        items=resItems;
        populateItemCategories();
    }

    @Override
    public Fragment getItem(int position) {

        ArrayList<Item> itemsToSend=new ArrayList<>();

        Log.d("Arraylist initialized","Yes");

        String category=itemCategory.get(position);

        Log.d("Item category: ",category);

        for(Item i:items){

            if(i.getItemType().equals(category)){
                Log.d("Item type",i.getItemType());
                itemsToSend.add(i);
                Log.d("Item added","Yes");
            }
            else
            {

                Log.d("Item added","No");
            }
        }
        
        return new Restaurant_Item_Fragment(itemsToSend);
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

    private void populateItemCategories() {

        itemCategory=new ArrayList<>();

        for(int i=0;i<items.size();i++){

            if(i==0){
                itemCategory.add(items.get(i).getItemType());
            }

            else{
                if(!items.get(i).getItemType().equals(items.get(i-1).getItemType())){
                    itemCategory.add(items.get(i).getItemType());
                }
            }
        }
    }
}
