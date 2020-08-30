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

    @Update
    void updateUserLocal(User user);
}
