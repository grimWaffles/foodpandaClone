package com.example.foodpandaclone.viewModel;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class LocationAccessViewModel extends AndroidViewModel {

    private Repository mRepo;

    public LocationAccessViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);
    }

    public void updateLocalUser(Location location){

        try{
            List<User> mUserList= mRepo.getUserListFromLocal();

            if(mUserList==null){
                User user=new User("Current user","null","null","null","User");
                user.setLatitude(location.getLatitude());
                user.setLongitude(location.getLongitude());

                mRepo.insertUserToLocal(user);
            }
            else{
                User user=mUserList.get(0);
                user.setLatitude(location.getLatitude()); user.setLongitude(location.getLongitude());

                mRepo.insertUserToLocal(user);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadData() {

        mRepo.getFirebaseData();
    }
}
