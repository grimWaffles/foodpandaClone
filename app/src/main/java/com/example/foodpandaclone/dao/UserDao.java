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
    List<User> fetchUserFromLocal();

    @Query("UPDATE user_table SET id=:id, email=:email,password=:password WHERE id=1")
    void updateLocal(String id, String email,String password);

    @Query("UPDATE user_table SET latitude=:latitude,longitude=:longitude  WHERE id=1")
    void updateLocalUserLocation(double latitude,double longitude);
}
