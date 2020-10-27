package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.User;

import java.util.List;

public class MyOrderViewModel extends AndroidViewModel {

    private Repository mRepo;

    public MyOrderViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);
    }

    public LiveData<List<User>> getCurrentUser(){
       return mRepo.getUserListFromLocal();
    }

    public LiveData<List<Order>> getUserOrders(){
        return mRepo.getOrderlist();
    }
}
