package com.example.foodpandaclone.model;

import java.util.List;

public class Restaurant {

    private String name, primaryKey, locationName;
    private int noOfReviews, delivery_cost, current_discount;
    private float rating;
    private List<Item> items;

    //add the time objects and location object

    public Restaurant(String name, String locationName, int noOfReviews, int delivery_cost, int current_discount, float rating, List<Item> items) {
        this.name = name;
        //this.primaryKey = primaryKey;
        this.locationName = locationName;
        this.noOfReviews = noOfReviews;
        this.delivery_cost = delivery_cost;
        this.current_discount = current_discount;
        this.rating = rating;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getNoOfReviews() {
        return noOfReviews;
    }

    public void setNoOfReviews(int noOfReviews) {
        this.noOfReviews = noOfReviews;
    }

    public int getDelivery_cost() {
        return delivery_cost;
    }

    public void setDelivery_cost(int delivery_cost) {
        this.delivery_cost = delivery_cost;
    }

    public int getCurrent_discount() {
        return current_discount;
    }

    public void setCurrent_discount(int current_discount) {
        this.current_discount = current_discount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
