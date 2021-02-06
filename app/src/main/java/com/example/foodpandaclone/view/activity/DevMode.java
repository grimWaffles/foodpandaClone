package com.example.foodpandaclone.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodpandaclone.R;
import com.google.android.material.navigation.NavigationView;

public class DevMode extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_mode);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle("Admin Portal");

        //Initialized nav drawer
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);

        ///Nav drawer toggle
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.drawer_open,R.string.drawer_close);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));

        toggle.syncState();

        drawerLayout.addDrawerListener(toggle);

        //Navigation Menu

        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.nav_menu_admin);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



        return false;
    }
}