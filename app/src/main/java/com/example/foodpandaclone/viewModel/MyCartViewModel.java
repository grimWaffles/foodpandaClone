package com.example.foodpandaclone.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.OrderFirebase;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.view.activity.MyCart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyCartViewModel extends AndroidViewModel {

    public static final String TAG="MyCartViewModel";

    private Repository mRepo; private Handler handler;



    public MyCartViewModel(@NonNull Application application) {
        super(application);
        mRepo=new Repository(application);

        handler=new Handler(Looper.getMainLooper());
    }

    //add functions here

    public LiveData<List<Item>> getOrderItemsFromLocal(){
        return mRepo.getCartItemsFromLocal();
    }

    public LiveData<List<Restaurant>> getRestaurantData() {
        return mRepo.getRestaurantFromLocal();
    }

    public LiveData<List<User>> getCurrentUser() {
        return mRepo.getUserListFromLocal();
    }

    public LiveData<List<Order>> getOrderFromLocal(){
        return mRepo.getPendingOrderlist();
    }

    public void processOrder(User mUser,List<Item> itemList,int total,int t_discount){

        final Order currentOrder=new Order(1, mUser.getUserID(),0,"pending",
                total,t_discount,getCurrentDate());

        Log.d(TAG,Integer.toString(t_discount));

        final List<OrderItem> orderItems=convertItemsToOrderItems(currentOrder,itemList);

        insertOrderToLocal(currentOrder);
        insertOrderItemsToLocal(orderItems);

        OrderFirebase orderFirebase=new OrderFirebase(currentOrder, orderItems);

        uploadOrderToFirebase(orderFirebase);

    }

    private void printOrderFirebaseData(OrderFirebase orderFirebase) {

        Log.d(TAG,Integer.toString(orderFirebase.getTotal_cost()));
        Log.d(TAG,Integer.toString(orderFirebase.getDiscount()));
    }

    public void insertOrderToLocal(Order currentOrder) {
        mRepo.insertOrderToLocal(currentOrder);
    }

    public void insertOrderItemsToLocal(List<OrderItem> orderItems) {
        mRepo.insertOrderItemsToLocal(orderItems);
    }

    public void uploadOrderToFirebase(OrderFirebase currentOrder) {
        mRepo.insertOrderToFirebase(currentOrder);
    }

    public void decreaseItemFromOrder(int itemID, int restaurantID) {
        mRepo.decreaseItemQuantity(itemID,restaurantID);
    }

    public int findTotalDiscount(List<Restaurant> restaurantList) {

        int discount=0; int count=0;

        for(Restaurant r:restaurantList){
            discount+=r.getDiscount();
            count++;
        }

        return discount/count;

    }

    public String getCurrentDate(){

        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return simpleDateFormat.format(calendar.getTime());
    }

    public List<OrderItem> convertItemsToOrderItems(Order order,List<Item> items){

        List<OrderItem> theOrderItems=new ArrayList<>();

        for(Item i:items){
            theOrderItems.add(new OrderItem(order.getOrderID(),i));
        }

        return theOrderItems;
    }

    public void waitFor(int i){
        synchronized (this){
            try{
                Thread.currentThread().wait(i);
            }
            catch(Exception e){
                Log.d(TAG,"Thread wait not possible");
                e.printStackTrace();
            }
        }
    }
}
