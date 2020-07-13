package com.example.foodpandaclone.view.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodpandaclone.view.DeveloperSettings;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.ViewPagerMainActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("");

        //Initialized toolbar:
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Initialized NavDrawer:
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));

        toggle.syncState();

        drawerLayout.addDrawerListener(toggle);

        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Initialized ViewPager and the tabView:
        ViewPagerMainActivity pagerAdapter=new ViewPagerMainActivity(getSupportFragmentManager());

        ViewPager sectionPager=findViewById(R.id.section_pager);
        sectionPager.setAdapter(pagerAdapter);

        TabLayout tabLayout=findViewById(R.id.tablayout_main);
        tabLayout.setupWithViewPager(sectionPager);

    }

    //toolbar methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch(item.getItemId()){
            case R.id.my_cart:
                Toast.makeText(this, "Functionality not added yet", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    //NavDrawer back button toggle
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    //NavDrawer onClick
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        Intent intent;

        switch(menuItem.getItemId()){
            case R.id.my_orders:
                intent=new Intent(this, AppSettings.class);
                intent.putExtra("message","Clicked my_orders");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return  true;

            case R.id.help:
                intent=new Intent(this,AppSettings.class);
                intent.putExtra("message","Clicked help");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return  true;

            case R.id.settings:
                intent=new Intent(this,AppSettings.class);
                intent.putExtra("message","Clicked settings");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return  true;

            case R.id.terms_and_conditions:
                intent=new Intent(this,AppSettings.class);
                intent.putExtra("message","Clicked T&C");
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return  true;

            case R.id.dev_options:
                intent=new Intent(this, DeveloperSettings.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return  true;
        }
        return true;
    }

}