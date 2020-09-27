package com.example.foodpandaclone.model;

import java.util.Date;
import java.util.List;

public class OrderFirebase {

    String orderID;
    String userID;
    String senderID;
    List<Item> orderItems;
    Date date;

    public OrderFirebase(String orderID, String userID, String senderID, List<Item> orderItems, Date date) {
        this.orderID = orderID;
        this.userID = userID;
        this.senderID = senderID;
        this.orderItems = orderItems;
        this.date = date;
    }
    public OrderFirebase(){

    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public List<Item> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Item> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
