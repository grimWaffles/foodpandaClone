package com.example.foodpandaclone.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_item_table")
public class OrderItem{

    @PrimaryKey
    @ColumnInfo(name="id")
    private int itemID;
    @ColumnInfo(name = "resID")
    private int restaurantID;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "itemType")
    private String itemType;
    @ColumnInfo(name = "price")
    private int price;
    @ColumnInfo(name = "quantity")
    private int quantity;
    @ColumnInfo(name="orderID")
    private int orderID;

    //check manual for more later on
    //itemCategory matches the one in restaurant and store model classes

    public OrderItem(String name, String description, String itemType, int price,int orderID) {
        this.name = name;
        this.description = description;
        this.restaurantID = 0;
        this.itemType = itemType;
        this.price = price;
        this.quantity = 0;
        this.itemID=0;
        this.orderID=orderID;
    }

    public OrderItem(){}

    public int getItemID() {
        return itemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
