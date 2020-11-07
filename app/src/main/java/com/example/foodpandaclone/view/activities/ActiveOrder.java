package com.example.foodpandaclone.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.foodpandaclone.viewModel.ActiveOrderViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class ActiveOrder extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap; private ActiveOrderViewModel aoVM; private Toolbar toolbar;
    private TextView message,sender_name,sender_phone,orderID,total_cost; private Button call_sender,cancel_order;
    private CardView cardView; private ProgressBar progressBar; private Order order;
    private Handler handler; private boolean currentOrderExists=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_order);

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
        cancel_order=cardView.findViewById(R.id.cancel_order);
        handler=new Handler();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        aoVM=new ViewModelProvider(this).get(ActiveOrderViewModel.class);

        aoVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                if(users!=null && users.size()!=0){
                    updateUserUI(users.get(0));
                }
            }
        });

        aoVM.getCurrentOrders().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {

               if(orders!=null && orders.size()!=0){
                   currentOrderExists=true;
                   ActiveOrder.this.order=orders.get(0);
                   updateOrderUI(orders.get(0));
               }
            }
        });

        aoVM.getCurrentRider().observe(this, new Observer<List<Rider>>() {
            @Override
            public void onChanged(List<Rider> riders) {

                if(riders!=null && riders.size()!=0) {
                    call_sender.setVisibility(View.VISIBLE);
                    updateRiderUI(riders.get(0));
                }

            }
        });

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
                    sender_name.setText("");
                    sender_phone.setText("");
                    orderID.setText("");
                    total_cost.setText("0Tk");

                    mMap.clear();
                    aoVM.cancelOrder(orderID.getText().toString());
                }
                else{
                    Toast.makeText(ActiveOrder.this, "You didn't order anything!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**handler.post(new Runnable() {
            @Override
            public void run() {

                if(currentOrderExists){
                    aoVM.processOrder(ActiveOrder.this.order.getOrderID());
                }

                handler.postDelayed(this,5000); Log.d("Order refreshed","Yes");
            }
        });**/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    private void updateUserUI(User user) {

        LatLng currentLocation = new LatLng(user.getLatitude(), user.getLongitude());
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("You"));

        CameraPosition cameraPosition=new CameraPosition.Builder().target(currentLocation)
                .zoom(15)
                .bearing(0)
                .tilt(30)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    private void updateOrderUI(Order order) {

       if(currentOrderExists){
           //update order information and UI
           call_sender.setVisibility(View.VISIBLE);
           cancel_order.setVisibility(View.VISIBLE);

           orderID.setText(Integer.toString(order.getOrderID()));
           total_cost.setText(Integer.toString(order.getTotal_cost()) +"Tk");
       }
       else{
           message.setText("You do not have current orders");
           call_sender.setVisibility(View.GONE);
           cancel_order.setVisibility(View.GONE);
       }

    }

    private void updateRiderUI(Rider rider) {

        //get rid of the progress bar and everything if a rider is found or  else do nothing

        sender_name.setText(rider.getUsername(rider.getEmail()));
        sender_phone.setText(Integer.toString(rider.getPhone()));
        message.setText("Rider found!");
    }

}