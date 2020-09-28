package com.example.foodpandaclone.view.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.viewModel.MainActivityViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.ViewPagerMainActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    DrawerLayout drawerLayout; private MainActivityViewModel mMAVM; private ViewPager sectionPager; private TabLayout tabLayout;
    Button btn_login; View navView;
    TextView user_email,user_name,user_latitude,user_longitude;
    final int LOGIN_ACTIVITY=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Foodpanda Clone");

        mMAVM=new ViewModelProvider(this).get(MainActivityViewModel.class);

        sectionPager=findViewById(R.id.section_pager);
        tabLayout=findViewById(R.id.tablayout_main);

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

        //navHeader Items
        navView=navigationView.getHeaderView(0);

        btn_login=(Button) navView.findViewById(R.id.login_main);
        btn_login.setOnClickListener(this);

        user_email=(TextView) navView.findViewById(R.id.useremail_main); user_name=(TextView)navView.findViewById(R.id.username_main);
        user_latitude=(TextView) navView.findViewById(R.id.userlatitude);
        user_longitude=(TextView) navView.findViewById(R.id.userlongitude);

        //Initialized ViewPager and the tabView:

        mMAVM.getTheData().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {

                ViewPagerMainActivity pagerAdapter=new ViewPagerMainActivity(getSupportFragmentManager(), restaurants);

                sectionPager.setAdapter(pagerAdapter);
                tabLayout.setupWithViewPager(sectionPager);
            }
        });

        //todo
        //get  user name and populate nav header

        mMAVM.getCurrentUser().observe(MainActivity.this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                //get first and only from list
                User currUser = users.get(0);

                if(currUser.getUserID()==1 && currUser.getEmail().equals("1")){
                    user_name.setText("Current User");
                    user_email.setText("noemailadded@currentuser.app");
                }
                else{
                    user_name.setText(currUser.getUsername(currUser.getEmail()));
                    user_email.setText(currUser.getEmail());
                    btn_login.setVisibility(View.GONE);
                }

                user_latitude.setText("Latitude: "+String.valueOf(currUser.getLatitude()));
                user_longitude.setText("Longitude: "+String.valueOf(currUser.getLongitude()));

            }
        });
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
               startActivity(new Intent(MainActivity.this,MyCart.class));
                return true;

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

    @Override
    public void onClick(View v) {

        if(v.getId()==btn_login.getId()){
            startActivityForResult(new Intent(this,Login.class),LOGIN_ACTIVITY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==LOGIN_ACTIVITY){
            if(resultCode==RESULT_OK){
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            }
        }
    }
}