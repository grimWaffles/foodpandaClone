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
    @ColumnInfo(name = "date")
    private String date;

    public Order(int orderID, int userID, int senderID, String date) {
        this.orderID = orderID;
        this.userID = userID;
        this.senderID = senderID;
        this.date = date;
    }
    public Order(){

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
