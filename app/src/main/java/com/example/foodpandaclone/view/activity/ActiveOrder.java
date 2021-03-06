package com.example.foodpandaclone.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.view.fragment.CustomDialogFragment;
import com.example.foodpandaclone.viewModel.ActiveOrderViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ActiveOrder extends AppCompatActivity implements OnMapReadyCallback, CustomDialogFragment.OnPromptClick {

    private static final String TAG="ActiveOrder";

    //ViewModel
    private ActiveOrderViewModel aoVM;

    //Widgets
    private GoogleMap mMap;  private Toolbar toolbar;  private TextView message,sender_name,sender_phone,orderID,total_cost;
    private Button call_sender,cancel_order;  private CardView cardView; private ProgressBar progressBar;

    //Variables
    private boolean mCurrentOrderExists =false; private Order mOrder; private Rider mRider;
    private FragmentManager fm=getFragmentManager(); boolean paymentPrompted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_order);

        try{
            toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionBar=getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            progressBar=findViewById(R.id.progress);
            progressBar.setVisibility(View.GONE);

            this.setTitle("Active Order");

            cardView=findViewById(R.id.cart_order);
            message=cardView.findViewById(R.id.message);
            sender_name=cardView.findViewById(R.id.sender_name);
            sender_phone=cardView.findViewById(R.id.sender_phone);
            orderID=cardView.findViewById(R.id.order_id);
            total_cost=cardView.findViewById(R.id.total_cost);
            call_sender=cardView.findViewById(R.id.call_sender); call_sender.setVisibility(View.GONE);
            cancel_order=cardView.findViewById(R.id.cancel_order); cancel_order.setVisibility(View.GONE);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_layout, mapFragment)
                    .commit();

            mapFragment.getMapAsync(this);
        }
        catch (Exception e){
            Log.d(TAG,"Error on line 83");
            e.printStackTrace();
        }

        aoVM=new ViewModelProvider(this).get(ActiveOrderViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        call_sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone =sender_phone.getText().toString();

                if(!phone.equals("")){

                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                            "tel", "0"+phone, null));
                    startActivity(phoneIntent);
                }
                else{
                    Toast.makeText(ActiveOrder.this, "No available sender found yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!orderID.getText().toString().equals("")){

                    loadDialogFragment("Cancel this order?");

                }
                else{
                    Toast.makeText(ActiveOrder.this, "You didn't order anything!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        aoVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                if(users!=null && users.size()!=0){

                    try{

                        aoVM.getUserOrders(users.get(0).getUserID());
                        aoVM.setUserID(users.get(0).getUserID());
                        updateUserUI(users.get(0));
                    }
                    catch(Exception e){
                        Log.d(TAG,"Failed to get current user");
                        e.printStackTrace();
                    }
                }
            }
        });

        aoVM.getCurrentOrders().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {

                if(orders!=null && orders.size()!=0){

                    try{
                        mOrder=orders.get(0);mCurrentOrderExists =true;

                        if(orders.get(0).getSenderID()==0 && !orders.get(0).getStatus().equals("payment pending")){
                            aoVM.findRiderForOrder(orders.get(0));
                        }
                        else {
                            if(mRider==null){
                                aoVM.downloadRiderInformation(orders.get(0).getSenderID());
                            }
                        }

                        if(!orders.get(0).getStatus().equals("payment pending")){

                            aoVM.trackOrder(mOrder.getOrderID());


                            //progressBar.setVisibility(View.GONE);
                        }
                        updateOrderUI(orders.get(0));
                    }

                    catch(Exception e){

                        Log.d(TAG,"Failed to get order");
                        e.printStackTrace();
                    }
                }
                else{
                    message.setText("No current Order");
                }


            }
        });

        aoVM.getCurrentRider().observe(this, new Observer<List<Rider>>() {
            @Override
            public void onChanged(List<Rider> riders) {

                if(riders!=null && riders.size()!=0) {
                    try{
                        mRider=riders.get(0);
                        updateRiderUI(riders.get(0));
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void updateUserUI(User user) {

        LatLng currentLocation = new LatLng(user.getLatitude(), user.getLongitude());
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("You")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.woman)));

        CameraPosition cameraPosition=new CameraPosition.Builder().target(currentLocation)
                .zoom(15)
                .bearing(0)
                .tilt(30)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void updateRiderUI(Rider rider) {

        //get rid of the progress bar and everything if a rider is found or  else do nothing
        if(rider!=null){

            call_sender.setVisibility(View.VISIBLE);
            sender_name.setText(rider.getUsername(rider.getEmail()));
            sender_phone.setText(Integer.toString(rider.getPhone()));
            //message.setText("Rider found!");

            LatLng currentLocation = new LatLng(rider.getLatitude(), rider.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentLocation)
                    .title(rider.getUsername(rider.getEmail()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.scooter_2)));
        }
    }


    private void updateOrderUI(Order order) {

       if(mCurrentOrderExists && order!=null){

           //update order information and UI
           cancel_order.setVisibility(View.VISIBLE);

           orderID.setText(Integer.toString(order.getOrderID()));
           total_cost.setText(Integer.toString(order.getTotal_cost()) +"Tk");
           message.setText(order.getStatus());

           if(order.getStatus().equals("payment pending") && !paymentPrompted){
               paymentPrompted=true;
               loadDialogFragment("Please pay "+"Tk."+total_cost.getText());
           }

           if(!order.getStatus().equals("pending") || !order.getStatus().equals("Rider found")){
               cancel_order.setVisibility(View.GONE);
           }
       }

       else{
           message.setText("You do not have an active order");
           orderID.setText("");
           total_cost.setText("");
           call_sender.setVisibility(View.GONE);
           cancel_order.setVisibility(View.GONE);
       }

    }
    private void clearOrderUI() {
        message.setText("You do not have an active order");
        orderID.setText("");
        total_cost.setText("");
        call_sender.setVisibility(View.GONE);
        cancel_order.setVisibility(View.GONE);

       aoVM.clearRider();

       synchronized (this){
           try{
               wait(2000);
           }
           catch (Exception e){
               e.printStackTrace();
           }
       }

       finish();
    }

    private void loadDialogFragment(String message) {

        CustomDialogFragment dialogFragment=new CustomDialogFragment(message);

        dialogFragment.show(fm,"My custom Dialog");
    }

    @Override
    public void onPromptClick(String message) {

       if(message.startsWith("Please")){
            mCurrentOrderExists=false;
            loadDialogFragment("Order Complete!");

       }
       else if(message.equals("Order Complete!")){
           clearOrderUI();
       }
       else{
           mCurrentOrderExists=false;
           updateOrderUI(mOrder);
           aoVM.cancelOrder(orderID.getText().toString());

           mMap.clear();
       }
    }
}