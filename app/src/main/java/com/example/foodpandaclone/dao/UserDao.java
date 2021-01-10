package com.example.foodpandaclone.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodpandaclone.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUserToLocal(User user);

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> fetchUserFromLocal();

    @Query("SELECT * FROM user_table")
    List<User> getCurrentUserFromLocal();

    @Query("UPDATE user_table SET id=:id, email=:email,password=:password,phone=:phone,type=:type,login_status=:login_status WHERE id=1")
    void updateLocalUserData(int id, String email, String password, int phone, String type,String login_status);

    @Query("UPDATE user_table SET latitude=:latitude,longitude=:longitude")
    void updateLocalUserLocation(double latitude,double longitude);

    @Query("DELETE  FROM user_table")
    void deleteLocalUser();

    @Query("DELETE  FROM user_table WHERE login_status='customer'")
    void deleteCustomer();

    @Query("UPDATE user_table SET id=1, email=1,password=1,phone=1,type='User',login_status='Not logged in' WHERE login_status='Logged in'")
    void logoutCurrent();

    @Query("DELETE  FROM user_table WHERE type='Rider'")
    void deleteLocalRider();

    @Query("UPDATE user_table SET login_status=:status WHERE id=1")
    void updateLoginStatus(String status);

    @Query("DELETE FROM user_table WHERE login_status='customer'")
    void deleteCustomerFromLocal();
}
