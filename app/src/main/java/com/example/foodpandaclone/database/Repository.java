package com.example.foodpandaclone.database;

import android.app.Application;
import android.location.Location;

import androidx.lifecycle.LiveData;

import com.example.foodpandaclone.dao.ItemDao;
import com.example.foodpandaclone.dao.RestaurantDao;
import com.example.foodpandaclone.dao.UserDao;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.User;

import java.util.List;

public class Repository {

    private RestaurantDao mRestaurantDao; private ItemDao mItemDao; private LocalDatabaseHelper db; private UserDao mUserDao;
    private FirebaseDatabaseHelper fireDB;

    public Repository(Application application){

        db=LocalDatabaseHelper.getDatabase(application);

        mRestaurantDao=db.restaurantDao();
        mItemDao=db.itemDao();
        mUserDao=db.userDao();

        fireDB=new FirebaseDatabaseHelper(application);
    }

    //Functions to get and receive data
    public LiveData<List<Restaurant>> getRestaurantFromLocal(){
        return mRestaurantDao.fetchRestaurantFromLocal();
    }

    public LiveData<List<Item>> getItemsFromLocal(int resID){ return mItemDao.fetchItemFromLocal(resID); }

    //public void insertToFirebase(RestaurantFirebase restaurantFirebase){ fireDB.insertRestaurantData(restaurantFirebase); }

    public void getFirebaseData(){ fireDB.loadRestaurantDataFromFirebase(); }

    public void clearAllDataLocal(){ mRestaurantDao.deleteAllRestaurantFromLocal(); mItemDao.deleteAllItemFromLocal(); }

    public LiveData<List<User>> getUserListFromLocal(){ return mUserDao.fetchUserFromLocal(); }

    public void insertUserToLocal(User user) {
        mUserDao.insertUserToLocal(user);
    }

    public LiveData<Restaurant> getSingleRestaurant(int id){ return mRestaurantDao.getSingleRestaurant(id);}

    public void updateLocalUser(User user) {
        mUserDao.updateLocalUserData(user.getUserID(),user.getEmail(),user.getPassword(),user.getPhone(),user.getType());
    }

    public void updateLocalUserLocation(Location location) {
        mUserDao.updateLocalUserLocation(location.getLatitude(),location.getLongitude());
    }

    public List<User> getLocalUser() { return mUserDao.getCurrentUserFromLocal();}

    public LiveData<List<User>> getUserListFromFirebase(String phone, String password) {

        fireDB.getUserDataFromFirebase(phone,password);

        return getUserListFromLocal();
    }

    public void insertCurrentUserToFirebase(User user){

        fireDB.insertUserToFirebase(user);
    }
}
