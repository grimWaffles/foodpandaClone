package com.example.foodpandaclone.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodpandaclone.model.Restaurant;

import java.util.List;

@Dao
public interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRestaurantToLocal(Restaurant restaurant);

    @Query("SELECT * FROM restaurant_table ORDER BY name ASC")
    LiveData<List<Restaurant>> fetchRestaurantFromLocal();

    @Query("DELETE FROM restaurant_table")
    void deleteAllRestaurantFromLocal();

}
