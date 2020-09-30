package com.example.foodpandaclone.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.Order;
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
    private TextView message,sender_name,sender_phone,orderID,total_cost;
    private CardView cardView; private Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_order);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.setTitle("Active Order");

        cardView=findViewById(R.id.cart_order);
        message=cardView.findViewById(R.id.message);
        sender_name=cardView.findViewById(R.id.sender_name);
        sender_phone=cardView.findViewById(R.id.sender_phone);
        orderID=cardView.findViewById(R.id.order_id);
        total_cost=cardView.findViewById(R.id.total_cost);

        aoVM=new ViewModelProvider(this).get(ActiveOrderViewModel.class);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        aoVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                LatLng currentLocation = new LatLng(users.get(0).getLatitude(), users.get(0).getLongitude());
                mMap.addMarker(new MarkerOptions().position(currentLocation).title("You"));

                CameraPosition cameraPosition=new CameraPosition.Builder().target(currentLocation)
                        .zoom(15)
                        .bearing(0)
                        .tilt(30)
                        .build();

                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });

        aoVM.getCurrentOrders().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                
                if(orders.size()==0){
                    message.setText("You do not have current orders");
                }
                else{
                    sender_name.setText(sender_name.getText().toString()+" "+"Searching");
                    sender_phone.setText(sender_phone.getText().toString()+" "+"Searching");
                    orderID.setText(Integer.toString(orders.get(0).getOrderID()));
                }
            }
        });
    }
}