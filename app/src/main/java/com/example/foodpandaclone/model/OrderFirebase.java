package com.example.foodpandaclone.model;

import java.util.Date;
import java.util.List;

public class OrderFirebase {

    int orderID;
    int userID;
    int senderID;
    List<Item> orderItems;
    String status;
    int total_cost;

    public OrderFirebase(){
        this.orderID=1001;
    }
    public OrderFirebase(Order order,List<Item> items){
        this.orderID=order.getOrderID();
        this.userID=order.getUserID();
        this.senderID=order.getSenderID();
        this.orderItems=items;
        this.status=order.getStatus();
        this.total_cost=order.getTotal_cost();
    }

    public OrderFirebase(int orderID, int userID, int senderID, List<Item> orderItems, String status, int total_cost) {
        this.orderID = orderID;
        this.userID = userID;
        this.senderID = senderID;
        this.orderItems = orderItems;
        this.status=status;
        this.total_cost=total_cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
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
