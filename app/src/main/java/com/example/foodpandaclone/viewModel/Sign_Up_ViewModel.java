package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.User;

public class Sign_Up_ViewModel extends AndroidViewModel {

    Repository mRepo;

    public Sign_Up_ViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);

    }


    public void addUserToFirebase(User user) {

        mRepo.insertCurrentUserToFirebase(user);

    }
}
