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
    private TextView message,sender_name,sender_phone,orderID,total_cost;private Button call_sender,cancel_order;
    private CardView cardView; private Order order; private User rider; private ProgressBar progressBar;
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
        call_sender=cardView.findViewById(R.id.call_sender);
        cancel_order=cardView.findViewById(R.id.cancel_order);

        aoVM=new ViewModelProvider(this).get(ActiveOrderViewModel.class);

        call_sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = sender_phone.getText().toString();

                if(!phone.equals("")){

                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                            "tel", phone, null));
                    startActivity(phoneIntent);
                }
                else{
                    Toast.makeText(ActiveOrder.this, "No available sender found yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        aoVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

               if(users.size()==1){
                   LatLng currentLocation = new LatLng(users.get(0).getLatitude(), users.get(0).getLongitude());
                   mMap.addMarker(new MarkerOptions().position(currentLocation).title("You"));

                   CameraPosition cameraPosition=new CameraPosition.Builder().target(currentLocation)
                           .zoom(15)
                           .bearing(0)
                           .tilt(30)
                           .build();

                   mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
               }

               if(users.size()==2){

                   rider=users.get(1);

                   LatLng currentLocation = new LatLng(users.get(1).getLatitude(), users.get(1).getLongitude());
                   mMap.addMarker(new MarkerOptions().position(currentLocation).title(rider.getUsername(rider.getEmail())));
               }

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
                    total_cost.setText(Integer.toString(orders.get(0).getTotal_cost()) +"Tk");

                   if(rider==null){
                       findARider();
                   }
                   else{
                       updateUIValues();
                   }
                }
            }
        });
    }

    private void updateUIValues() {

        sender_name.setText(rider.getUsername(rider.getEmail()));
        sender_phone.setText(Integer.toString(rider.getPhone()));
        message.setText("Rider found!");

    }

    public void findARider() {
        aoVM.getAvailableRiders();
    }
}