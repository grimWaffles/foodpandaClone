package com.example.foodpandaclone.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;

import java.util.List;

@Dao
public interface RiderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUserToLocal(Rider rider);

    @Query("SELECT * FROM rider_table")
    LiveData<List<Rider>> fetchRiderFromLocal();

    @Query("SELECT * FROM rider_table")
    List<Rider> getCurrentRiderFromLocal();

    @Query("UPDATE rider_table SET id=:id, email=:email,password=:password,phone=:phone,type=:type,login_status=:login_status WHERE id=1")
    void updateLocalRiderData(int id, String email, String password, int phone, String type,String login_status);

    @Query("UPDATE rider_table SET latitude=:latitude,longitude=:longitude")
    void updateLocalRiderLocation(double latitude,double longitude);

    @Query("DELETE  FROM rider_table")
    void deleteLocalRider();

    @Query("UPDATE rider_table SET id=1, email=1,password=1,phone=1,login_status='Not logged in'")
    void logoutCurrent();

    @Query("UPDATE rider_table SET status=:status")
    void updateLoginStatus(String status);
}
