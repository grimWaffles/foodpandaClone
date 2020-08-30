package com.example.foodpandaclone.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RestaurantFirebase implements Parcelable {

    private int restaurantID;
    private String name;
    private String location;
    private String phoneNumber;
    private String priceLevel;
    private int reviews;
    private int discount;
    private int deliveryCost;
    private float rating;
    private double latitude;
    private double longitude;
    private List<String> categoriesOffered=new ArrayList<>();
    private List<Item> items=new ArrayList<>();

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

    public RestaurantFirebase(String name, String location, String phoneNumber, int reviews, int discount, int deliveryCost, String priceLevel, List<String> categoriesOffered, List<Item> items, float rating, double latitude, double longitude) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.reviews = reviews;
        this.discount = discount;
        this.deliveryCost = deliveryCost;
        this.priceLevel = priceLevel;
        this.categoriesOffered = categoriesOffered;
        this.items = items;
        this.rating = rating;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public RestaurantFirebase(){}

    //getters and setters... auto-generated


    protected RestaurantFirebase(Parcel in) {
        restaurantID = in.readInt();
        name = in.readString();
        location = in.readString();
        phoneNumber = in.readString();
        priceLevel = in.readString();
        reviews = in.readInt();
        discount = in.readInt();
        deliveryCost = in.readInt();
        categoriesOffered = in.createStringArrayList();
        rating = in.readFloat();
        in.readTypedList(items, Item.CREATOR);
    }

    public static final Creator<RestaurantFirebase> CREATOR = new Creator<RestaurantFirebase>() {
        @Override
        public RestaurantFirebase createFromParcel(Parcel in) {
            return new RestaurantFirebase(in);
        }

        @Override
        public RestaurantFirebase[] newArray(int size) {
            return new RestaurantFirebase[size];
        }
    };

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID =restaurantID;

        for (int i=0;i<this.items.size();i++){
            this.items.get(i).setRestaurantID(restaurantID);
            this.items.get(i).setItemID(i);
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

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(restaurantID);
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(phoneNumber);
        parcel.writeString(priceLevel);
        parcel.writeInt(reviews);
        parcel.writeInt(discount);
        parcel.writeInt(deliveryCost);
        parcel.writeStringList(categoriesOffered);
        parcel.writeFloat(rating);
        parcel.writeTypedList(items);
    }
}
