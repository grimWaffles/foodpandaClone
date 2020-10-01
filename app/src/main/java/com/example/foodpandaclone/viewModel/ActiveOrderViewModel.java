package com.example.foodpandaclone.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.User;

import java.util.List;

public class ActiveOrderViewModel extends AndroidViewModel {

    private Repository mRepo;

    public ActiveOrderViewModel(@NonNull Application application) {
        super(application);
        mRepo=new Repository(application);
    }

    public LiveData<List<User>> getCurrentUser() {

        return mRepo.getUserListFromLocal();
    }

    public LiveData<List<Order>> getCurrentOrders() {
        return mRepo.getOrderlist();
    }

    public void getAvailableRiders() {
        mRepo.getAvailableRiders();
    }

    public void cancelOrder(String s) {
        mRepo.cancelOrder(s);
    }
}
