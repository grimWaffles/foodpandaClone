package com.example.foodpandaclone;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm){
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
                    return getResources().getString(R.string.delivery_tab);
                case 1:
                    return getResources().getString(R.string.pickup_tab);
                case 2:
                    return getResources().getString(R.string.store_tab);
            }
            return null;
        }
    }

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialized toolbar:
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ActionBar actionBar=getSupportActionBar(toolbar);

        //Initialized NavDrawer:
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close);

        toggle.syncState();

        drawerLayout.addDrawerListener(toggle);

        //Initialized ViewPager and the tabView:
        SectionsPagerAdapter pagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager sectionPager=findViewById(R.id.section_pager);
        sectionPager.setAdapter(pagerAdapter);

        TabLayout tabLayout=findViewById(R.id.tablayout_main);
        tabLayout.setupWithViewPager(sectionPager);

    }

    //toolbar methods start
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch(item.getItemId()){
            //todo
            default:

                return super.onOptionsItemSelected(item);
        }

    }
    //toolbar methods end

    //Drawer layout back button toggle
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }
    //drawerLayout toggle end

    //navigation view onClick start
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch(menuItem.getItemId()){
            //todo

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return  true;

    }
    //navigation view onClick  end


}