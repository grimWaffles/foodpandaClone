package com.example.foodpandaclone.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderFirebase {

    int orderID;
    int userID;
    int senderID;
    List<OrderItem> orderItems=new ArrayList<>();
    String status;
    int total_cost;
    int discount;
    String date;

    public OrderFirebase(){
        this.orderID=1001;
    }

    public OrderFirebase(Order order,List<OrderItem> items){
        this.orderID=order.getOrderID();
        this.userID=order.getUserID();
        this.senderID=order.getSenderID();
        this.orderItems=items;
        this.status=order.getStatus();
        this.total_cost=order.getTotal_cost();
        this.date=order.getDate();
    }

    public OrderFirebase(int orderID, int userID, int senderID, List<OrderItem> orderItems, String status, int total_cost,int discount,String date) {
        this.orderID = orderID;
        this.userID = userID;
        this.senderID = senderID;
        this.orderItems = orderItems;
        this.status=status;
        this.total_cost=total_cost;
        this.discount=discount;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

        if(orderItems!=null){
            for(OrderItem i: this.orderItems){
                i.setOrderID(this.getOrderID());
            }
        }

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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Order getOrderObject(){

        Order order=new Order(this.getOrderID(),this.userID,this.senderID,this.status,this.total_cost,this.discount,this.date);

        return  order;
    }

}
