package com.example.foodpandaclone.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class Item implements Parcelable{

    @PrimaryKey
    @ColumnInfo(name="id")
    private int itemID;
    @ColumnInfo(name = "resID")
    private int restaurantID;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "price")
    private int price;
    @ColumnInfo(name = "quantity")
    private int quantity;

    //check manual for more later on
    //itemCategory matches the one in restaurant and store model classes

    public Item(String name, String description, String category, int price, int quantity) {
        this.name = name;
        this.description = description;
        this.restaurantID = 0;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.itemID=0;
    }

    public Item(){}

    protected Item(Parcel in) {
        itemID = in.readInt();
        name = in.readString();
        description = in.readString();
        restaurantID = in.readInt();
        category = in.readString();
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

    public int getItemID() {
        return itemID;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        parcel.writeInt(itemID);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(restaurantID);
        parcel.writeString(category);
        parcel.writeInt(price);
        parcel.writeInt(quantity);
    }
}
