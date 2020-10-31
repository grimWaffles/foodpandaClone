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
    void insertOrderItemToLocal(OrderItem item);

    @Query("SELECT * FROM order_item_table WHERE orderID=:orderID ORDER BY itemType ASC")
    LiveData<List<OrderItem>> fetchItemForOrderFromLocal(int orderID);

    @Query("DELETE FROM order_item_table")
    void deleteAllItemFromLocal();

    @Query("UPDATE order_item_table SET orderID=:i WHERE orderID=:id")
    void updateLocalOrderID(int i,int id);
}
