package com.example.foodpandaclone.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private TextView res_location,res_delivery;
    private TabLayout  tabLayout; private ViewPager resItemView;
    private Button btn_to_cart; private boolean cartIsFull=false;
    private RestaurantActivityViewModel  mRAVM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        res_location=findViewById(R.id.res_location);
        res_delivery=findViewById(R.id.res_delivery);
        tabLayout=findViewById(R.id.tab_layout);
        resItemView=findViewById(R.id.category_pager);
        btn_to_cart=findViewById(R.id.btn_to_cart);
        btn_to_cart.setVisibility(View.GONE);

        //get data from the viewModel:
        mRAVM=new ViewModelProvider(this).get(RestaurantActivityViewModel.class);

        mRAVM.getSingleRestaurantData(getIntent().getExtras().getInt("restaurant")).observe(this, new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {

                res_delivery.setText("Delivery: "+"30 minutes");

                Restaurant_Activity.this.setTitle(restaurant.getResName());

                res_location.setText("Location: "+getAddressFromLocation(restaurant.getLatitude(),restaurant.getLongitude()));

                mRAVM.getRestaurantItems(restaurant.getResID()).observe(Restaurant_Activity.this, new Observer<List<Item>>() {
                    @Override
                    public void onChanged(List<Item> items) {

                        for(Item i:items){ if(i.getQuantity()>0){ if(!cartIsFull){cartIsFull=true;} } }

                        if(!cartIsFull){
                            btn_to_cart.setVisibility(View.GONE);
                        }
                        else{
                            btn_to_cart.setVisibility(View.VISIBLE);
                        }

                        //set up viewpager
                        ViewPagerResActivity adapter=new ViewPagerResActivity(getSupportFragmentManager(),items);
                        resItemView.setAdapter(adapter);
                        tabLayout.setupWithViewPager(resItemView);
                    }
                });
            }
        });

        btn_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Restaurant_Activity.this,MyCart.class));
            }
        });

    }

    private String getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder=new Geocoder(this);

        try{
            List<Address> addressList=geocoder.getFromLocation(latitude,longitude,1);
            return addressList.get(0).getAddressLine(0);
        }
        catch(Exception e){
            return "Address not found";
        }
    }
}