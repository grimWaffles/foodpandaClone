package com.example.foodpandaclone.model;

public class Item {

    private String itemID,name,description,restaurantID,itemType;
    private int price,quantity;

    //check manual for more later on
    //itemCategory matches the one in restaurant and store model classes
    // TODO: 28-Jun-20

    public Item(String name, String description, String itemType, int price, int quantity) {
        this.name = name;
        this.description = description;
        this.restaurantID = "0";
        this.itemType = itemType;
        this.price = price;
        this.quantity = quantity;
        this.itemID="0";
    }
    public Item(){}

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
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

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
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
