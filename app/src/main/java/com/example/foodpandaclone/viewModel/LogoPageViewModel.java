package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.User;

import java.util.List;

public class LogoPageViewModel extends AndroidViewModel{

    private Repository mRepo;

    public LogoPageViewModel(@NonNull Application application) {
        super(application);
        mRepo=new Repository(application);
    }

    public void clearLocalStorage(){

        mRepo.clearAllDataLocal();
    }


}
