package com.example.foodpandaclone.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.RiderOrderAdapter;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.view.fragment.CustomDialogFragment;
import com.example.foodpandaclone.view.fragment.MapsRider;
import com.example.foodpandaclone.view.fragment.RiderOrderListFragment;
import com.example.foodpandaclone.viewModel.MainActivityRiderViewModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MainActivity_Rider extends AppCompatActivity implements RiderOrderAdapter.OnOrderItemClick, CustomDialogFragment.OnPromptClick {

    //Widgets
    private Toolbar toolbar;

    //ViewModel
    private MainActivityRiderViewModel marVM;

    //Variables
    private FragmentTransaction ft; private FragmentManager fm=getFragmentManager(); private boolean riderBusy =false;
    private User mUser; private LatLng riderLocation; private LatLng userLocation;

    public static final String TAG="MainActivityRider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__rider);

        this.setTitle("Bhook lagi?");
        
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        marVM=new ViewModelProvider(this).get(MainActivityRiderViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        marVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                try{
                    if(users.get(0).getType().equals(("User"))){
                        startActivity(new Intent(MainActivity_Rider.this,MainActivity.class));
                        finish();
                    }

                    else if(users.size()!=0){
                        mUser=users.get(0);
                        riderLocation=new LatLng(mUser.getLatitude(),mUser.getLongitude());
                        // TODO: 19-Dec-20 Loading mapFragment causes problems
                        //loadMapFragment(riderLocation);
                        marVM.getRiderInformation(users.get(0).getUserID());
                    }
                    else if(users.size()>1){
                        Toast.makeText(MainActivity_Rider.this, "You have 1 order", Toast.LENGTH_LONG).show();
                    }
                }

                catch(Exception e){
                    Log.d(TAG,"Failed to get user");
                    e.printStackTrace();
                }

            }
        });

        marVM.getCurrentRider().observe(this, new Observer<List<Rider>>() {
            @Override
            public void onChanged(List<Rider> riders) {
                if(riders!=null  && riders.size()!=0){

                    try{
                        if(riders.get(0).getStatus().equals("Busy")){
                            riderBusy=true;
                            marVM.getThisRidersOrder(riders.get(0).getRiderID());

                        }
                        else{
                            riderBusy=false;
                            marVM.downloadAllPendingOrdersFromFirebase();
                        }
                    }
                    catch (Exception e){
                        Log.d(TAG,"Failed to get rider");
                        e.printStackTrace();
                    }
                }
            }
        });

        marVM.getPendingOrders().observe(this,new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {

               try{
                   if(orders.size()==0){
                       //loadDialogFragment("No orders are currently available");
                   }

                   else if(!riderBusy){
                       loadOrderListFragment(orders);
                   }
                   else{
                       marVM.downLoadUserInformationFromFirebase(orders.get(0).getUserID());
                   }
               }
               catch (Exception e){
                   Log.d(TAG,"Failed to get order");
                   e.printStackTrace();
               }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.toolbar_menu_rider,menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        switch(item.getItemId()){

            case R.id.my_cart:
                Toast.makeText(this, "Functionality not added yet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.my_orders:
                Toast.makeText(this, "Functionality not available yet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.my_account:
                Toast.makeText(this, "Functionality not available yet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout_btn:
                if(mUser!=null){
                    marVM.logoutUser(mUser);
                }
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOrderItemClick(int id) {
        loadDialogFragment("");
    }

    @Override
    public void onPromptClick(String message) {
        Toast.makeText(this, "Does Absolutely nothing", Toast.LENGTH_SHORT).show();
    }

    private void loadDialogFragment(String message) {

        CustomDialogFragment dialogFragment=new CustomDialogFragment(message);

        dialogFragment.show(fm,"My custom Dialog");
    }

    private void loadOrderListFragment(List<Order> orders) {
        ft=fm.beginTransaction();
        ft.replace(R.id.frame_layout,new RiderOrderListFragment(MainActivity_Rider.this,orders));
        ft.commit();
    }

    private void loadMapFragment(LatLng riderLocation) {
       getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new MapsRider(riderLocation)).commit();
    }

    private void updateUIMessages(String message){
        if(message.equals("on")){

        }
        else{

        }
    }
}