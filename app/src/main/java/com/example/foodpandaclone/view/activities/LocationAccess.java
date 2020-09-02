package com.example.foodpandaclone.view.activities;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.viewModel.LocationAccessViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationAccess extends AppCompatActivity implements View.OnClickListener {

    private ImageView logo; private Button use_current_loc,select_another;
    private static final int LOCATION_PERMISSION=6969;

    private LocationAccessViewModel mLAVM; FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location__access);

        logo=findViewById(R.id.logo_img);
        use_current_loc=findViewById(R.id.use_current_loc);
        select_another=findViewById(R.id.select_another);

        use_current_loc.setOnClickListener(this);
        select_another.setOnClickListener(this);

        mLAVM=new ViewModelProvider(this).get(LocationAccessViewModel.class);

        client= LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.use_current_loc){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION);
            }
            else{
                doTheVM();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode==LOCATION_PERMISSION) {

            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            doTheVM();
        }
    }

    private void doTheVM() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {
                    mLAVM.updateLocalUser(location);
                    startActivity(new Intent(LocationAccess.this,MainActivity.class));
                }

            });
        }
    }
}