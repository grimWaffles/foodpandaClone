package com.example.foodpandaclone.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String userID;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;

    public User(String phone, String email, String password) {

        this.email = email;
        this.phone = phone;
        this.password = password;

        if(email.equals("1")){
            this.userID="1";
        }
        else{
            this.userID=this.getUsername(email);
        }

        this.type="User";
        this.latitude=0.000000;
        this.longitude=0.000000;
    }

    public User(){

    }

    private String getUsername(String email){
      int endIndex=email.indexOf('@');
      String username="";

      if(endIndex!=-1){
        username=email.substring(0,endIndex);
      }
      return username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
