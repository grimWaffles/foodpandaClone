package com.example.foodpandaclone.model;

import java.util.ArrayList;
import java.util.List;

public class RestaurantFirebase {

    private int restaurantID;
    private String name;
    private String location;
    private String phoneNumber;
    private int priceLevel;
    private int numberOfReviews;
    private int discount;
    private int deliveryCost;
    private double rating;
    private double latitude;
    private double longitude;
    private List<String> categoriesOffered=new ArrayList<>();
    private List<Item> items=new ArrayList<>();

    public RestaurantFirebase(){}

    public RestaurantFirebase(int restaurantID, String name, String location, String phoneNumber, int priceLevel, int numberOfReviews, int discount, int deliveryCost, double rating, double latitude, double longitude, List<String> categoriesOffered, List<Item> items) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.priceLevel = priceLevel;
        this.numberOfReviews = numberOfReviews;
        this.discount = discount;
        this.deliveryCost = deliveryCost;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categoriesOffered = categoriesOffered;
        this.items = items;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getCategoriesOffered() {
        return categoriesOffered;
    }

    public void setCategoriesOffered(List<String> categoriesOffered) {
        this.categoriesOffered = categoriesOffered;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
