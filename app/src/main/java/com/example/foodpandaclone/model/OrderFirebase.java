package com.example.foodpandaclone.model;

import java.util.Date;
import java.util.List;

public class OrderFirebase {

    int orderID;
    int userID;
    int senderID;
    List<Item> orderItems;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderFirebase(int orderID, int userID, int senderID, List<Item> orderItems, String status) {
        this.orderID = orderID;
        this.userID = userID;
        this.senderID = senderID;
        this.orderItems = orderItems;
        this.status=status;
    }
    public OrderFirebase(){

    }
    public OrderFirebase(Order order,List<Item> items){
        this.orderID=order.getOrderID();
        this.userID=order.getUserID();
        this.senderID=order.getSenderID();
        this.orderItems=items;
        this.status=order.getStatus();
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

    public List<Item> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Item> orderItems) {
        this.orderItems = orderItems;
    }
    
}
