package com.example.foodpandaclone.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodpandaclone.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsRider extends Fragment {

    private GoogleMap mMap; private LatLng riderLocation;

    public MapsRider(LatLng rider){
        riderLocation=rider;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_maps_rider,container,false);

        final SupportMapFragment mapFragment=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap=googleMap;

                loadRiderLocationOnMap();
            }
        });

        return view;
    }

    private void loadRiderLocationOnMap() {

        if(riderLocation!=null){
            mMap.addMarker(new MarkerOptions().position(riderLocation).title("You")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.woman)));

            CameraPosition cameraPosition=new CameraPosition.Builder().target(riderLocation)
                    .zoom(15)
                    .bearing(0)
                    .tilt(0)
                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}