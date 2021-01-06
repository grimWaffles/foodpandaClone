package com.example.foodpandaclone.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.RiderOrderAdapter;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.view.fragment.CustomDialogFragment;
import com.example.foodpandaclone.view.fragment.MapsRider;
import com.example.foodpandaclone.view.fragment.RiderOrderListFragment;
import com.example.foodpandaclone.viewModel.MainActivityRiderViewModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MainActivity_Rider extends AppCompatActivity implements RiderOrderAdapter.OnOrderItemClick, CustomDialogFragment.OnPromptClick {

    public static final String TAG="MainActivityRider";

    //Widgets
    private Toolbar toolbar; private ProgressBar progressBar;

        //Rider
        TextView rider_name,rider_status;
        //Order
        TextView orderID,order_status,order_items_loaded,total_cost;
        //User
        TextView customer_name,customer_phone,customer_address;

    //ViewModel
    private MainActivityRiderViewModel marVM;

    //Variables
    private FragmentTransaction ft; private FragmentManager fm=getFragmentManager(); private boolean riderBusy =false;
    private User mUser; private LatLng riderLocation;
    private boolean orderItemsAreDownloaded=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__rider);

        this.setTitle("Bhook lagi?");
        
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        marVM=new ViewModelProvider(this).get(MainActivityRiderViewModel.class);

        progressBar=findViewById(R.id.pb_main);
        progressBar.setVisibility(View.GONE);

        rider_name=findViewById(R.id.rider_name); rider_status=findViewById(R.id.rider_name);
        orderID=findViewById(R.id.order_id);order_status=findViewById(R.id.order_status);order_items_loaded=findViewById(R.id.order_items_loaded);total_cost=findViewById(R.id.total_cost);
        customer_name=findViewById(R.id.customer_name);customer_address=findViewById(R.id.customer_address);customer_phone=findViewById(R.id.customer_phone);
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(View.VISIBLE);

        //For login purposes
        marVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                if(users.size()!=0){

                    try{
                        //multiple entries
                        if(users.size()>1){

                            User loggedInUser=marVM.getLoggedInUser(users);
                            User customer=marVM.getCustomer(users);

                            if(loggedInUser.getType().equals(("User"))){
                                startActivity(new Intent(MainActivity_Rider.this,MainActivity.class));
                                finish();
                            }
                            else{

                                riderLocation=new LatLng(loggedInUser.getLatitude(),loggedInUser.getLongitude());

                                if(!marVM.isRiderInformationDownloading()){
                                    marVM.downloadRiderInformation(loggedInUser.getUserID());
                                }

                                updateCustomerUI(customer);
                            }
                        }

                        //Single entry
                        else{

                            if(users.get(0).getType().equals(("User"))){
                                startActivity(new Intent(MainActivity_Rider.this,MainActivity.class));
                                finish();
                            }
                            else{
                                mUser=users.get(0);
                                riderLocation=new LatLng(mUser.getLatitude(),mUser.getLongitude());

                                if(!marVM.isRiderInformationDownloading()){
                                    marVM.downloadRiderInformation(users.get(0).getUserID());
                                }
                            }
                        }
                    }
                    catch(Exception e){
                        Log.d(TAG,"Failed to get user"); // TODO: 04-Jan-21
                        e.printStackTrace();
                    }
                }
            }
        });

        //The actual fun bit
        marVM.getCurrentRider().observe(this, new Observer<List<Rider>>() {
            @Override
            public void onChanged(List<Rider> riders) {
                if(riders.size()!=0){

                    try{
                        if(riders.get(0).getStatus().equals("Busy")){
                            riderBusy=true;

                            if(!marVM.isRiderOrderDownloading()){
                                marVM.downloadThisRidersOrder(riders.get(0).getRiderID());
                            }
                        }
                        /*else{
                            riderBusy=false;

                            if(!marVM.downloadingAllPendingOrders()){
                                marVM.downloadAllPendingOrdersFromFirebase();
                            }
                        }

                         */

                        updateRiderUI(riders);
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

               if(orders.size()!=0){
                   try{

                       if(!riderBusy){
                           loadOrderListFragment(orders);
                       }
                       else{
                           if(!marVM.downloadUserInformation()){

                               marVM.downLoadUserInformationFromFirebase(orders.get(0).getUserID());
                           }
                       }

                       updateOrderUI(orders);

                       progressBar.setVisibility(View.GONE);
                   }
                   catch (Exception e){
                       Log.d(TAG,"Failed to get order");
                       e.printStackTrace();
                   }
               }
            }
        });

        marVM.getOrderItemsList().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {

                if(orderItems.size()!=0){

                    orderItemsAreDownloaded=true;
                    marVM.downloadSpecificRestaurantData(orderItems);
                }
            }
        });
    }

    private void updateRiderUI(List<Rider> riders) {

        if(riders!=null || riders.size()!=0){
            rider_name.setText(riders.get(0).getUsername(riders.get(0).getEmail()));
            rider_status.setText(riders.get(0).getStatus());

            if(riders.get(0).getStatus().equals("Available")){
                rider_status.setTextColor(ContextCompat.getColor(this,R.color.otherAccent3));
            }
            else if(riders.get(0).getStatus().equals("Busy")){
                rider_status.setTextColor(ContextCompat.getColor(this,R.color.redcolor));
            }
        }
    }

    private void updateOrderUI(List<Order> orders) {

        if(orders!=null || orders.size()!=0){
            Order order=orders.get(0);

            orderID.setText(order.getOrderID());
            order_status.setText(order.getStatus());

            if(orderItemsAreDownloaded){
                order_items_loaded.setText("Yes"); order_items_loaded.setTextColor(ContextCompat.getColor(this,R.color.otherAccent3));
            }
            else{
                order_items_loaded.setText("No"); order_items_loaded.setTextColor(ContextCompat.getColor(this,R.color.redcolor));
            }

            total_cost.setText(order.getTotal_cost()); total_cost.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        }
    }

    private void updateCustomerUI(User customer) {

        if(customer!=null){
            customer_name.setText(customer.getUsername(customer.getEmail()));
            customer_address.setText(getAddressFromLocation(customer.getLatitude(),customer.getLongitude()));
            customer_phone.setText(customer.getPhone());
        }
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

                if(riderBusy){
                    startActivity(new Intent(MainActivity_Rider.this, RiderCartActivity.class));
                }
                else{
                    loadDialogFragment("You do not have current order");
                }

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