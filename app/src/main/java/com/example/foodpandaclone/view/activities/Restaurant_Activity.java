package com.example.foodpandaclone.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        res_name=findViewById(R.id.res_name);  res_location=findViewById(R.id.res_location); res_delivery=findViewById(R.id.res_delivery);
        tabLayout=findViewById(R.id.tab_layout);
        resItemView=findViewById(R.id.category_pager);

        resObj= (Restaurant) getIntent().getExtras().get("restaurant");
        res_name.setText(resObj.getName()); res_location.setText(resObj.getLocation());
        //res_delivery not yet implemented

        titleOfPage= resObj.getName();
        this.setTitle(titleOfPage);
        this.setTitleColor(R.color.colorPrimary);

        ViewPagerResActivity  adapter=new ViewPagerResActivity(getSupportFragmentManager(), resObj.getItems(), resObj.getCategoriesOffered());

        resItemView.setAdapter(adapter);
        tabLayout.setupWithViewPager(resItemView);

    }
}