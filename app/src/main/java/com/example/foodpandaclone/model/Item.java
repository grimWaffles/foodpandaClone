package com.example.foodpandaclone.model;

public class Item {

    private String name, description, restaurant_name, primaryKey;
    int price, quantity;

    public Item(String name, String description, String restaurant_name,String primaryKey, int price, int quantity) {
        this.name = name;
        this.description = description;
        this.restaurant_name = restaurant_name;
        this.price = price;
        this.quantity = quantity;
        this.primaryKey=primaryKey;
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

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
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
