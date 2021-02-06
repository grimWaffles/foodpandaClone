package com.example.foodpandaclone.view.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.ViewPagerMainActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    DrawerLayout drawerLayout; private MainActivityViewModel mMAVM; private ViewPager sectionPager; private TabLayout tabLayout;  NavigationView navigationView;
    Button btn_login,btn_logout; View navView;
    TextView user_email,user_name, user_address,loading_message;
    final int LOGIN_ACTIVITY=1; ProgressBar pbmain; private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Bhook Lagi?");

        mMAVM=new ViewModelProvider(this).get(MainActivityViewModel.class);

        sectionPager=findViewById(R.id.section_pager);
        tabLayout=findViewById(R.id.tablayout_main);

        pbmain=findViewById(R.id.pbmain);

        //seting visibility of progressbar and viewpager
        pbmain.setVisibility(View.VISIBLE);
        sectionPager.setVisibility(View.GONE);

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

        navigationView=findViewById(R.id.nav_view);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.nav_menu);
        navigationView.setNavigationItemSelectedListener(this);

        //navHeader Items
        navView=navigationView.getHeaderView(0);

        btn_login=(Button) navView.findViewById(R.id.login_main);
        btn_login.setOnClickListener(this);
        
        btn_logout=(Button) navView.findViewById(R.id.logout_main);
        btn_logout.setOnClickListener(this);
        btn_logout.setVisibility(View.GONE);

        user_email=(TextView) navView.findViewById(R.id.useremail_main); user_name=(TextView)navView.findViewById(R.id.username_main);
        user_address =(TextView) navView.findViewById(R.id.userlatitude); loading_message=findViewById(R.id.tv_loading_message);

        //Initialized ViewPager and the tabView:
    }


    @Override
    protected void onStart() {
        super.onStart();

        mMAVM.getTheData().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {

                if(restaurants==null || restaurants.size()==0){
                    mMAVM.loadData();
                }

                else if (restaurants.size()!=0 && restaurants.size()>=2){

                    ViewPagerMainActivity pagerAdapter=new ViewPagerMainActivity(getSupportFragmentManager(), restaurants);

                    sectionPager.setAdapter(pagerAdapter);
                    tabLayout.setupWithViewPager(sectionPager);

                    sectionPager.setVisibility(View.VISIBLE);
                    pbmain.setVisibility(View.GONE);
                    loading_message.setVisibility(View.GONE);
                }
            }
        });

        mMAVM.getCurrentUser().observe(MainActivity.this, new Observer<List<User>>() {

            @Override
            public void onChanged(List<User> users) {

                if(users.size()!=0){
                    updateUIvalues(users.get(0));
                    MainActivity.this.mUser =users.get(0);
                    //mMAVM.downloadUserOrders(users.get(0).getUserID());
                }
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
                intent=new Intent(this, MyOrder.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.mapsOrder:
                intent=new Intent(this, ActiveOrder.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.developerSettings:
                startActivity(new Intent(MainActivity.this,DevMode.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.riderOrders:
                startActivity(new Intent(MainActivity.this, MyOrder.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            /**case R.id.mapsRider:
                startActivity(new Intent(MainActivity.this, RiderOrder.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;**/

            //do something about this^
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==btn_login.getId()){
            startActivityForResult(new Intent(this,Login.class),LOGIN_ACTIVITY);return;

        }
        if(v.getId()==btn_logout.getId()){

            mMAVM.logoutCurrentUser(this.mUser);
            Toast.makeText(this, "Logging out", Toast.LENGTH_LONG).show();
            btn_logout.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
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

    public void updateUIvalues(User currUser){

        //If user is an admin or a normal customer
        if(currUser.getType().equals("User") || currUser.getType().equals("Admin") ){

            //If not a blank user
            if(currUser.getUserID()!=1){
                user_name.setText(currUser.getUsername(currUser.getEmail()));
                user_email.setText(currUser.getEmail());
                btn_login.setVisibility(View.GONE);
                btn_logout.setVisibility(View.VISIBLE);

                user_address.setText(getAddressFromLocation(currUser.getLatitude(),currUser.getLongitude()));

                //Navbar menu changes
                if(currUser.getType().equals("Admin")){
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.nav_menu_dev);
                }

                else{
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.nav_menu);
                }
            }

            //If a blank user
            else{
                user_name.setText("Current User");
                user_email.setText("User not logged in");
                btn_login.setVisibility(View.VISIBLE);
                btn_logout.setVisibility(View.GONE);

                user_address.setText("No location set");

                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.nav_menu);
            }

        }

        //If he a rider
        else{
            startActivity(new Intent(MainActivity.this,MainActivity_Rider.class));
            finish();
        }

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