package com.example.foodpandaclone.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_table")
public class Order {

    @PrimaryKey
    @ColumnInfo(name="id")
    @NonNull
    private int orderID;
    @ColumnInfo(name="userid")
    private int userID;
    @ColumnInfo(name="senderid")
    private int senderID;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "total_cost")
    private int total_cost;
    @ColumnInfo(name="discount")
    private int discount;
    @ColumnInfo(name="date")
    private String date;

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order(int orderID, int userID, int senderID, String status, int total_cost, int discount,String date) {
        this.orderID = orderID;
        this.userID = userID;
        this.senderID = senderID;
        this.status="pending";
        this.total_cost=total_cost;
        this.discount=discount;
        this.date=date;
    }
    public Order(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

}
