package com.example.foodpandaclone.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.foodpandaclone.dao.ItemDao;
import com.example.foodpandaclone.dao.OrderDao;
import com.example.foodpandaclone.dao.OrderItemDao;
import com.example.foodpandaclone.dao.RestaurantDao;
import com.example.foodpandaclone.dao.RiderDao;
import com.example.foodpandaclone.dao.UserDao;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Restaurant.class, Item.class, User.class, Order.class, OrderItem.class, Rider.class},version = 1,exportSchema = false)
public abstract class LocalDatabaseHelper extends RoomDatabase {

    public abstract RestaurantDao restaurantDao();
    public abstract ItemDao itemDao();
    public abstract UserDao userDao();
    public abstract OrderDao orderDao();
    public abstract OrderItemDao orderItemDao();
    public abstract RiderDao riderDao();

    public static volatile LocalDatabaseHelper INSTANCE;

    static final ExecutorService databaseWriterExecutor= Executors.newFixedThreadPool(4);

    static final Migration MIGRATION_1_2 = new Migration(1,2){

        @Override
        public void migrate(SupportSQLiteDatabase database){

            database.execSQL(""); //Use this if  necessary
        }

    };

    static LocalDatabaseHelper getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (LocalDatabaseHelper.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        LocalDatabaseHelper.class,
                        "app_database")
                        .setQueryExecutor(databaseWriterExecutor)
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }
        return INSTANCE;
    }
}
