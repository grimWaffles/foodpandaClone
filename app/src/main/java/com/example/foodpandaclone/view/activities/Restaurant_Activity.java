package com.example.foodpandaclone.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.ViewPagerResActivity;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.viewModel.RestaurantActivityViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class Restaurant_Activity extends AppCompatActivity {

    private String titleOfPage;
    private TextView res_name,res_location,res_delivery;
    private TabLayout  tabLayout; private ViewPager resItemView;
    private RestaurantActivityViewModel  mRAVM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        res_name=findViewById(R.id.res_name);
        res_location=findViewById(R.id.res_location);
        res_delivery=findViewById(R.id.res_delivery);
        tabLayout=findViewById(R.id.tab_layout);
        resItemView=findViewById(R.id.category_pager);

        //get data from the viewModel:
        mRAVM=new ViewModelProvider(this).get(RestaurantActivityViewModel.class);


        //add a try catch
        mRAVM.getSingleRestaurantData(getIntent().getExtras().getInt("restaurant")).observe(this, new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {

                res_name.setText(restaurant.getResName()); res_location.setText(restaurant.getLocation()); res_delivery.setText("30 minutes");

                mRAVM.getRestaurantItems(restaurant.getResID()).observe(Restaurant_Activity.this, new Observer<List<Item>>() {
                    @Override
                    public void onChanged(List<Item> items) {

                        //set up viewpager
                        ViewPagerResActivity adapter=new ViewPagerResActivity(getSupportFragmentManager(),items);
                        resItemView.setAdapter(adapter);
                        tabLayout.setupWithViewPager(resItemView);
                    }
                });
            }
        });

    }
}