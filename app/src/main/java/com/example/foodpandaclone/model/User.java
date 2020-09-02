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
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;

    public User(String name, String phone, String email, String password, String type) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;

        if(!email.equals("null")){
            this.userID=  this.getUsername(email);
        }
        else{
            this.userID="1";
        }

        this.type=type;
        this.address="";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
