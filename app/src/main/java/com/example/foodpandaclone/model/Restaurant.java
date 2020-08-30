package com.example.foodpandaclone.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurant_table")
public class Restaurant {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int resID;
    @ColumnInfo(name = "name")
    private String resName;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "deliveryCost")
    private int deliveryCost;
    @ColumnInfo(name = "discount")
    private int discount;
    @ColumnInfo(name = "review")
    private int review;
    @ColumnInfo(name = "rating")
    private double rating;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Restaurant(){

    }

    public Restaurant(int resID, String resName, String phone, double latitude, double longitude,String location, int deliveryCost, int discount, int review, double rating) {
        this.resID = resID;
        this.resName = resName;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location=location;
        this.deliveryCost = deliveryCost;
        this.discount = discount;
        this.review = review;
        this.rating = rating;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
