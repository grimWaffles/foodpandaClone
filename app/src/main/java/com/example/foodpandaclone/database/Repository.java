package com.example.foodpandaclone.database;

import android.app.Application;
import android.location.Location;

import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.dao.ItemDao;
import com.example.foodpandaclone.dao.OrderDao;
import com.example.foodpandaclone.dao.OrderItemDao;
import com.example.foodpandaclone.dao.RestaurantDao;
import com.example.foodpandaclone.dao.RiderDao;
import com.example.foodpandaclone.dao.UserDao;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.OrderFirebase;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;

import java.util.List;

public class Repository {

    private RestaurantDao mRestaurantDao; private ItemDao mItemDao; private LocalDatabaseHelper db; private UserDao mUserDao;
    private FirebaseDatabaseHelper fireDB; private OrderDao mOrderDao;  private OrderItemDao orderItemDao;  private RiderDao mRiderDao;

    public Repository(Application application){

        db=LocalDatabaseHelper.getDatabase(application);
        orderItemDao=db.orderItemDao();
        mRestaurantDao=db.restaurantDao();
        mItemDao=db.itemDao();
        mUserDao=db.userDao();
        mOrderDao=db.orderDao();
        mRiderDao=db.riderDao();
        fireDB=new FirebaseDatabaseHelper(application);
    }

    /**User Functions**/
    public LiveData<List<User>> getUserListFromLocal(){ return mUserDao.fetchUserFromLocal(); }

    public void insertUserToLocal(User user) { mUserDao.insertUserToLocal(user); }

    //public void updateLocalUser(User user) { mUserDao.updateLocalUserData(user.getUserID(),user.getEmail(),user.getPassword(),user.getPhone(),user.getType()); }

    public void updateLocalUserLocation(Location location) { mUserDao.updateLocalUserLocation(location.getLatitude(),location.getLongitude()); }

    public List<User> getLocalUser() { return mUserDao.getCurrentUserFromLocal();}

    public LiveData<List<User>> getUserListFromFirebase(int phone, String password) {
        fireDB.getUserDataFromFirebase(phone,password);
        return getUserListFromLocal();
    }

    public void insertCurrentUserToFirebase(User user){

        User tempUser=getLocalUser().get(0);
        user.setLatitude(tempUser.getLatitude()); user.setLongitude(tempUser.getLongitude());
        fireDB.insertUserToFirebase(user);
    }

    public void logoutCurrentUser() {
        mUserDao.logoutCurrent();
    }


    /**Restaurant Functions**/
    public LiveData<List<Restaurant>> getRestaurantFromLocal(){ return mRestaurantDao.fetchRestaurantFromLocal(); }

    public LiveData<List<Item>> getItemsFromLocal(int resID){ return mItemDao.fetchItemFromLocal(resID); }

    public void getFirebaseData(){ fireDB.loadRestaurantDataFromFirebase(); }

    public LiveData<Restaurant> getSingleRestaurant(int id){ return mRestaurantDao.getSingleRestaurant(id);}


    /**Order Functions**/
    public void increaseItemQuantity(int itemID, int restaurantID) { mItemDao.increaseItemQuantity(itemID,restaurantID); }

    public void decreaseItemQuantity(int itemID, int restaurantID) { mItemDao.decreaseItemQuantity(itemID,restaurantID); }

    public LiveData<List<Item>> getOrderItemsFromLocal() { return mItemDao.getCartItemsFromLocal(); }

    public void deleteOrders() {
        mOrderDao.deleteAllOrderFromLocal();
    }

    public LiveData<List<Order>> getOrderlist() {
        return mOrderDao.getOrderList();
    }

    public void insertOrderToLocal(Order currentOrder) { mOrderDao.insertOrderToLocal(currentOrder); }

    public void insertOrderToFirebase(OrderFirebase currentOrder) { fireDB.insertOrderToFirebase(currentOrder); }

    public void cancelOrder(final String s) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mUserDao.deleteLocalRider();
                mOrderDao.deleteAllOrderFromLocal();
                fireDB.cancelOrderFirebase(s);
            }
        }).start();
    }

    public void insertOrderItemsToLocal(List<OrderItem> orderItems) {

        for(OrderItem oi:orderItems){
            orderItemDao.insertOrderItemToLocal(oi);
        }
    }

    public void downloadUserOrder(int userID) { fireDB.getAllOrdersFromFirebase(userID); }

    public void getOrderFromFirebase(int orderID) {
        fireDB.getOrderFromFirebase(orderID);
    }


    /**Rider functions**/

    public void addRiderToFirebase(Rider newUser) {
        fireDB.insertRiderToFireBase(newUser);
    }

    public void getAvailableRiders() {
        fireDB.getAvailableRiders();
    }

    public LiveData<List<Rider>> getCurrentRider(){ return mRiderDao.fetchRiderFromLocal();}

    /**Global Functions**/
    public void clearAllDataLocal(){

        mRestaurantDao.deleteAllRestaurantFromLocal();
        mItemDao.deleteAllItemFromLocal();
        //mUserDao.deleteLocalUser();
        mOrderDao.deleteAllOrderFromLocal();
        orderItemDao.deleteAllItemFromLocal();
        mRiderDao.deleteLocalRider();
    }
}
