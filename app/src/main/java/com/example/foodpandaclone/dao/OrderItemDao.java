package com.example.foodpandaclone.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.OrderItem;

import java.util.List;

@Dao
public interface OrderItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrderItemToLocal(OrderItem item);

    @Query("SELECT * FROM order_item_table  ORDER BY resID ASC")
    LiveData<List<OrderItem>> getOrderItemFromLocal();

    @Query("DELETE FROM order_item_table")
    void deleteAllItemFromLocal();

    @Query("UPDATE order_item_table SET orderID=:id WHERE orderID=1")
    void updateLocalOrderID(int id);
}
