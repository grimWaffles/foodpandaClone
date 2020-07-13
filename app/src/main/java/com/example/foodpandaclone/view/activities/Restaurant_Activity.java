package com.example.foodpandaclone.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.ViewPagerResActivity;
import com.example.foodpandaclone.model.Restaurant;
import com.google.android.material.tabs.TabLayout;

public class Restaurant_Activity extends AppCompatActivity {

    private String titleOfPage; private Restaurant resObj;
    private TextView res_name,res_location,res_delivery;
    private TabLayout  tabLayout; private ViewPager resItemView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        res_name=findViewById(R.id.res_name);  res_location=findViewById(R.id.res_location); res_delivery=findViewById(R.id.res_delivery);
        tabLayout=findViewById(R.id.tab_layout);
        resItemView=findViewById(R.id.category_pager);


        if(savedInstanceState!=null){

            Log.d("Fetching data resActivity ","from bundle");
            resObj=(Restaurant) savedInstanceState.getParcelable("RestaurantSaved");
        }
        else{

            Log.d("Fetching data resActivity ","from intent");
            resObj= (Restaurant) getIntent().getExtras().get("restaurant");
        }

        Log.d("Reached restaurant activity","True");

        res_name.setText(resObj.getName()); res_location.setText(resObj.getLocation());

        Log.d("Restaurant name", resObj.getName());
        //res_delivery not yet implemented

        titleOfPage= resObj.getName();
        this.setTitle(titleOfPage);
        this.setTitleColor(R.color.colorPrimary);

        Log.d("Size of 2resViewpager", Integer.toString(resObj.getCategoriesOffered().size()));
        Log.d("Size of resViewpager", Integer.toString(resObj.getItems().size()));


        ViewPagerResActivity  adapter=new ViewPagerResActivity(getSupportFragmentManager(), resObj.getItems(), resObj.getCategoriesOffered());

        resItemView.setAdapter(adapter);
        tabLayout.setupWithViewPager(resItemView);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("RestaurantSaved",resObj);
        super.onSaveInstanceState(outState);
    }


}