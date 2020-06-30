package com.example.foodpandaclone.model;

import java.util.List;

public class Restaurant {

    private String restaurantID,name,location,phoneNumber,priceLevel;
    private int numberOfReviews, discount,deliveryCost;
    private List<String> categoriesOffered;
    private List<Item> items;
    private float rating;

    /*
    * private Time openingTime
    * private Time closingTime
    * private Location googleMapsLocation
    */
    // TODO: 28-Jun-20

    //check manual for more

    public Restaurant(){}

    public Restaurant(String name, String location, String phoneNumber, int numberOfReviews, int discount, int deliveryCost, String priceLevel, List<String> categoriesOffered, List<Item> items, float rating) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.numberOfReviews = numberOfReviews;
        this.discount = discount;
        this.deliveryCost = deliveryCost;
        this.priceLevel = priceLevel;
        this.categoriesOffered = categoriesOffered;
        this.items = items;
        this.rating = rating;
    }

    //getters and setters... auto-generated


    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;

        for (int i=0;i<this.items.size();i++){
            this.items.get(i).setRestaurantID(restaurantID);
            this.items.get(i).setItemID(Integer.toString(i));
        }
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
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

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String strDeliveryCost(){
        return Integer.toString(this.deliveryCost);
    }
    public String strRating(){
        return Float.toString(this.rating);
    }

    public String strDiscount() {
        return Integer.toString(this.discount);
    }
}
