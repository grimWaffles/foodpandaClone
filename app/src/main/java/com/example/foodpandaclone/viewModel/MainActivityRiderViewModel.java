package com.example.foodpandaclone.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.database.Repository;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivityRiderViewModel extends AndroidViewModel {

    public static final String TAG="MainActivityRiderViewModel";

    //Repository
    private Repository mRepo;

    //Other Variables
    private boolean isRiderInformationDownloading=false;
    private boolean isRiderOrderDownloading=false; private boolean downloadingAllPendingOrders=false;
    private boolean downloadingUserInformation=false;

    //Lists
    private List<OrderItem> orderItemList;

    public MainActivityRiderViewModel(@NonNull Application application) {
        super(application);

        orderItemList=new ArrayList<>();

        mRepo=new Repository(application);
        Log.d(TAG,"init");

    }

    public LiveData<List<User>> getCurrentUser() {

        return mRepo.getUserListFromLocal();
    }


    public LiveData<List<Rider>> getCurrentRider(){

        return mRepo.getCurrentRider();
    }

    public LiveData<List<Order>> getPendingOrders(){

        return mRepo.getPendingOrderlist();
    }

    public LiveData<List<OrderItem>> getOrderItemsList(){

        return mRepo.getOrderItemFromLocal();
    }

    public void downloadThisRidersOrder(int riderID) {

        Log.d(TAG,"getThisRidersOrder");
        isRiderOrderDownloading=true;
        mRepo.getRidersCurrentOrder(riderID);

    }

    public void logoutUser(final User user) {
        mRepo.logoutCurrentUser(user);
    }

    public void downloadRiderInformation(final int userID){

        isRiderInformationDownloading=true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                mRepo.downloadRiderInfo(userID);
                Log.d(TAG,"downloadedRiderInformation");
            }
        }).start();
    }

    public void downloadAllPendingOrdersFromFirebase(){

        downloadingAllPendingOrders=true;
        mRepo.downloadAllPendingOrdersFromFirebase();

        Log.d(TAG,"downloadAllPendingOrdersFromFirebase");
    }

    public void downLoadUserInformationFromFirebase(final int userID){

        downloadingUserInformation=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                mRepo.downloadUserInformationFromFirebase(userID);
                Log.d(TAG,"downloadUserInformation");
            }
        }).start();
    }

    public void downloadSpecificRestaurantData(List<OrderItem> itemsList) {

        if(orderItemList.size()==0){
            orderItemList=itemsList;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mRepo.downloadSpecificRestaurantData(getRestaurantIDs(orderItemList));
                }
            }).start();
        }
        else if(orderItemList.size()<itemsList.size()){

            List<Integer> resIDs=new ArrayList<>();

            resIDs=findTheDifferenceInResID(itemsList);

            if(resIDs.size()!=0){

                final List<Integer> finalResIDs = resIDs;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mRepo.downloadSpecificRestaurantData(finalResIDs);
                    }
                }).start();
            }
        }
    }

    private List<Integer> findTheDifferenceInResID(List<OrderItem> itemsList) {

       List<Integer> differentID= getRestaurantIDs(itemsList);

       differentID.removeAll(getRestaurantIDs(orderItemList));

       return differentID;
    }

    private ArrayList<Integer> getRestaurantIDs(List<OrderItem> orderItemList) {

        ArrayList<Integer> resIDs=new ArrayList<>();

        for(int i=0;i<orderItemList.size();i++){

            if(i==0){
                resIDs.add(orderItemList.get(i).getRestaurantID());
            }
            else{

                if(orderItemList.get(i).getRestaurantID()!=orderItemList.get(i-1).getRestaurantID()){
                    resIDs.add(orderItemList.get(i).getRestaurantID());
                }
            }
        }

        return resIDs;
    }

    public boolean isRiderInformationDownloading() {

        return this.isRiderInformationDownloading;
    }

    public boolean isRiderOrderDownloading() {

        return this.isRiderOrderDownloading;
    }

    public boolean downloadingAllPendingOrders() {
        return this.downloadingAllPendingOrders;
    }

    public boolean downloadUserInformation() {
        return this.downloadingUserInformation;
    }

    public User getLoggedInUser(List<User> users) {

        User newUser=new User();

        for(User user: users){
            if(user.getLogin_status().equals("Logged in") || user.getLogin_status().equals("Not logged in")){
                newUser=user;
            }
        }
        return newUser;
    }

    public User getCustomer(List<User> users) {

        User  newUser=new User();

        for(User user: users){
            if(user.getLogin_status().equals("customer")){
                newUser=user;
            }
        }

        return newUser;
    }

    public void updateOrderAskForPayment(int orderID) {

        mRepo.updateOrderAskForPayment(orderID);
    }

    public void updateOrderCompleted(int orderID) {
        mRepo.updateOrderCompleted(orderID);
    }

    public void settingRiderStatusOrderComplete(int userID) {

        mRepo.settingRiderStatusOrderComplete(userID);
    }

    public void deleteLocalDataAfterOrder(){
        mRepo.deleteLocalDataAfterOrder();
    }


}
