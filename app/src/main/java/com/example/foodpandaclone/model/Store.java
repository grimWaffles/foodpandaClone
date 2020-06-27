package com.example.foodpandaclone.model;

import java.util.List;

public class Store {

    String storeID,name,location;
    int priceLevel,deliveryCost;
    List<String> categoriesOffered;
    List<Item> items;

    /*private Time openingTime
     * private Time closingTime
     * private Location googleMapsLocation
     */

    public Store(String name, String location, int priceLevel, int deliveryCost, List<String> categoriesOffered, List<Item> items) {
        this.name = name;
        this.location = location;
        this.priceLevel = priceLevel;
        this.deliveryCost = deliveryCost;
        this.categoriesOffered = categoriesOffered;
        this.items = items;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
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

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
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
