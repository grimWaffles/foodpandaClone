package com.example.foodpandaclone.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodpandaclone.model.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrderToLocal(Order order);

    @Query("SELECT * FROM order_table")
    LiveData<List<Order>> getOrderList();

    @Query("DELETE FROM order_table")
    void deleteAllOrderFromLocal();

    @Query("SELECT * FROM order_table WHERE userid=:id")
    LiveData<List<Order>> getUserOrderFromLocal(int id);
}
