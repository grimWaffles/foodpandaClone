package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.User;

import java.util.List;

public class LoginViewModel  extends AndroidViewModel {

    Repository mRepo;User user;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);
        user=new User();
    }


    public LiveData<List<User>> loginUser(String phone, String password){

        return mRepo.getUserListFromFirebase(phone,password);
    }
}
