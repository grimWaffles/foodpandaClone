package com.example.foodpandaclone.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodpandaclone.dao.ItemDao;
import com.example.foodpandaclone.dao.OrderDao;
import com.example.foodpandaclone.dao.RestaurantDao;
import com.example.foodpandaclone.dao.UserDao;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Restaurant.class, Item.class, User.class, Order.class},version = 1,exportSchema = false)
public abstract class LocalDatabaseHelper extends RoomDatabase {

    public abstract RestaurantDao restaurantDao();
    public abstract ItemDao itemDao();
    public abstract UserDao userDao();
    public abstract OrderDao orderDao();

    public static volatile LocalDatabaseHelper INSTANCE;

    static final ExecutorService databaseWriterExecutor= Executors.newFixedThreadPool(4);

    static LocalDatabaseHelper getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (LocalDatabaseHelper.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        LocalDatabaseHelper.class,
                        "app_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}
