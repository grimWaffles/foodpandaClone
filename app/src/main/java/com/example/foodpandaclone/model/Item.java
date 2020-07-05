package com.example.foodpandaclone.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Item implements Parcelable {

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

    protected Item(Parcel in) {
        itemID = in.readString();
        name = in.readString();
        description = in.readString();
        restaurantID = in.readString();
        itemType = in.readString();
        price = in.readInt();
        quantity = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemID);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(restaurantID);
        parcel.writeString(itemType);
        parcel.writeInt(price);
        parcel.writeInt(quantity);
    }
}
