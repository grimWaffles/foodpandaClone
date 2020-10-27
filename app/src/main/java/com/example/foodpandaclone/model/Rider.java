package com.example.foodpandaclone.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rider_table")
public class Rider {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private int riderID;
    @ColumnInfo(name = "phone")
    private int phone;
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
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name="login_status")
    private String login_status;

    public Rider(String email, int phone, String password) {

        this.email = email;
        this.phone = phone;
        this.password = password;

        if (email.equals("1")) {
            this.riderID = 1;
        } else {
            this.riderID = phone;
        }

        this.type = "Rider";
        this.latitude = 0.000000;
        this.longitude = 0.000000;
        this.status="Available";
        this.login_status="Not logged in";
    }

    public Rider() {

    }

    public String getUsername(String email) {
        int i = email.indexOf('@');

        return email.substring(0, i);
    }

    public boolean checkIfRider(String email){
        int i=email.indexOf('@');

        if(email.substring(i+1,email.length()+1).equals("foodpanda.com")){
            return true;
        }
        return false;
    }

    public int getRiderID() {
        return riderID;
    }

    public void setRiderID(int riderID) {
        this.riderID = riderID;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}