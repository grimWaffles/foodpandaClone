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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItemToLocal(Item item);

    @Query("SELECT * FROM order_item_table WHERE order_id=:orderID ")
    LiveData<List<OrderItem>> getOrderItems(int orderID);

    @Query("DELETE FROM order_item_table")
    void deleteAllItemFromLocal();
}
