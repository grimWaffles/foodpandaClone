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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrderToLocal(Order order);

    @Query("SELECT * FROM order_table")
    LiveData<List<Order>> getOrderList();

    @Query("DELETE FROM order_table")
    void deleteAllOrderFromLocal();

    @Query("UPDATE order_table SET senderid=:id WHERE senderid=0")
    void updateOrderRider(int id);
}
