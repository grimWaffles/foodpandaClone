package com.example.foodpandaclone.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodpandaclone.model.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItemToLocal(Item item);

    @Query("SELECT * FROM item_table WHERE resID=:res_id ORDER BY itemType ASC")
    LiveData<List<Item>> fetchItemFromLocal(int res_id);

    @Query("SELECT * FROM item_table WHERE quantity!=0 ORDER BY resID ASC")
    LiveData<List<Item>> getCartItemsFromLocal();

    @Query("DELETE FROM item_table")
    void deleteAllItemFromLocal();

    @Query("UPDATE item_table SET quantity=quantity+1 WHERE resID=:restaurantID AND id=:itemID")
    void increaseItemQuantity(int itemID, int restaurantID);

    @Query("UPDATE item_table SET quantity=quantity-1 WHERE resID=:restaurantID AND id=:itemID")
    void decreaseItemQuantity(int itemID, int restaurantID);
}
