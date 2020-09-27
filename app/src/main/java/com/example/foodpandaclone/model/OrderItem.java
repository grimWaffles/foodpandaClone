package com.example.foodpandaclone.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_item_table")
public class OrderItem {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private int order_item_id;
    @ColumnInfo(name = "order_id")
    private int orderID;
    @ColumnInfo(name = "item_id")
    private int itemID;
    @ColumnInfo(name = "res_id")
    private int resID;
    @ColumnInfo(name = "quantity")
    private int quantity;
    @ColumnInfo(name="price")
    private int price;

    public OrderItem(int orderID, int itemID, int resID, int quantity, int price) {
        this.order_item_id = order_item_id;
        this.orderID = orderID;
        this.itemID = itemID;
        this.resID = resID;
        this.quantity = quantity;
        this.price = price;
    }
    public OrderItem(){

    }

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
