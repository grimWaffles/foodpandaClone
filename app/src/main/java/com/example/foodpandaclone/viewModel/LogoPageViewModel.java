package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foodpandaclone.database.Repository;

public class LogoPageViewModel extends AndroidViewModel {

    private Repository mRepo;

    public LogoPageViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);
    }

    public void loadFirebaseData(){

        mRepo.clearAllDataLocal();
        mRepo.getFirebaseData();
    }
}
