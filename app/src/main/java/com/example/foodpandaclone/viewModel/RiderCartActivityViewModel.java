package com.example.foodpandaclone.viewModel;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodpandaclone.database.LocalDatabaseHelper;
import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Restaurant;
import com.google.firebase.database.core.Repo;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RiderCartActivityViewModel extends AndroidViewModel {

    public static final String TAG="RiderCartActivityViewModel";

    private MutableLiveData<List<OrderItem>> orderItemList;

    private Repository mRepo; private ScheduledExecutorService service;

    public RiderCartActivityViewModel(@NonNull Application application) {
        super(application);

        mRepo=new Repository(application);
        service= Executors.newScheduledThreadPool(2);
        orderItemList=new MutableLiveData<>();
    }

    public LiveData<List<OrderItem>> getOrderItems(){

        if(orderItemList==null){
            loadOrderItems();
        }

        return orderItemList;
    }

    public LiveData<List<Restaurant>> getRestaurantList(){
        return mRepo.getRestaurantFromLocal();
    }

    private void loadOrderItems() {

       final Runnable getingOrderItems=new Runnable() {
           @Override
           public void run() {

               List<OrderItem> items=mRepo.getOrderItemFromLocal();

                if(items.size()!=0){

                    try{
                        orderItemList.postValue(items);
                        Log.d(TAG,Integer.toString(items.size()));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
           }
       };

       service.schedule(getingOrderItems,3, TimeUnit.SECONDS);
    }

    public void getRestaurantIDs(List<OrderItem> orderItems) {

        List<Integer> restaurants=new ArrayList<>();

        for(int i=0;i<orderItems.size();i++){

            if(i!=0){

              if(orderItems.get(i).getRestaurantID()!=orderItems.get(i-1).getRestaurantID()){
                  restaurants.add(orderItems.get(i).getRestaurantID());
              }

            }
            else{
                restaurants.add(orderItems.get(i).getRestaurantID());
            }
        }

        mRepo.downloadSpecificRestaurantData(restaurants);
        Log.d(TAG,"downloading restaurant data");
    }
}
