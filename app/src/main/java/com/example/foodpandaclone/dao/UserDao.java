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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserToLocal(User user);

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> fetchUserFromLocal();

    @Query("SELECT * FROM user_table")
    List<User> getCurrentUserFromLocal();

    @Query("UPDATE user_table SET id=:id, email=:email,password=:password,phone=:phone,type=:type WHERE id=1")
    void updateLocalUserData(int id, String email, String password, int phone, String type);

    @Query("UPDATE user_table SET latitude=:latitude,longitude=:longitude")
    void updateLocalUserLocation(double latitude,double longitude);

    @Query("DELETE  FROM user_table")
    void deleteLocalUser();
}
