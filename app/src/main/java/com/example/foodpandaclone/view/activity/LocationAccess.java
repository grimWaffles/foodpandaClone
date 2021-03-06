package com.example.foodpandaclone.view.activity;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.viewModel.LocationAccessViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationAccess extends AppCompatActivity implements View.OnClickListener {

    private ImageView logo;
    private Button use_current_loc;
    private static final int LOCATION_PERMISSION = 6969;

    private LocationAccessViewModel mLAVM;
    FusedLocationProviderClient client;

    LocationRequest locationRequest;
    LocationCallback locationCallback;

    String type="Not specific";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location__access);

        Intent intent=getIntent();

        type=intent.getStringExtra("type");

        logo = findViewById(R.id.logo_img);
        use_current_loc = findViewById(R.id.use_current_loc);
        //select_another=findViewById(R.id.select_another);

        use_current_loc.setOnClickListener(this);
        //select_another.setOnClickListener(this);

        mLAVM = new ViewModelProvider(this).get(LocationAccessViewModel.class);

        //fused location provider used to get location
        client = LocationServices.getFusedLocationProviderClient(this);

        //LocationRequest to get location updates
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);//5 seconds
        locationRequest.setFastestInterval(3000); //3 seconds

        /**if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            findUserLocation();

            if(type.equals("Rider")){
                startActivity(new Intent(LocationAccess.this,MainActivity_Rider.class));
                finish();
            }
            else{
                startActivity(new Intent(LocationAccess.this,MainActivity.class));
                finish();
            }
        }**/
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.use_current_loc) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
            }
            else {

               findUserLocation();

                if(type.equals("Rider")){
                    startActivity(new Intent(LocationAccess.this,MainActivity_Rider.class));
                    finish();
                }
                else{
                    startActivity(new Intent(LocationAccess.this,MainActivity.class));
                    finish();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == LOCATION_PERMISSION) {

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Click button again to continue", Toast.LENGTH_LONG).show();

        }
        else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
        }
    }

    public void findUserLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {

                    Log.d("Location  fetched:", "Yes");
                   if(location!=null){
                       mLAVM.updateLocalUser(location);
                   }

                   /** if (ActivityCompat.checkSelfPermission(LocationAccess.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationAccess.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    //Location callback
                    locationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);

                            mLAVM.updateLocalUser(locationResult.getLastLocation());
                        }
                    };
                    client.requestLocationUpdates(locationRequest, locationCallback, null);**/
                }

            });
        }
    }
}