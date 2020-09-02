package com.example.foodpandaclone.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.database.FirebaseDatabaseHelper;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.RestaurantFirebase;

import java.util.ArrayList;
import java.util.List;

public class DeveloperSettings extends AppCompatActivity /**implements View.OnClickListener**/{

    //Restaurant - Section 1
    private EditText name,location,phoneNumber,priceLevel,numberOfReviews,discount,deliveryCost,rating;

    //Item - Section 2
    private EditText itemName,description,itemType,price,quantity;

    //Buttons
    Button addItem,addRestaurant;

    //Objects
    Item item; RestaurantFirebase restaurant; List<Item> itemList; List<String> categories;

    //Repository
    FirebaseDatabaseHelper repository;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_settings);

        this.setTitle("Create  a Restaurant");
/**
        toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        repository=new FirebaseDatabaseHelper();

        name=findViewById(R.id.name);location=findViewById(R.id.location); phoneNumber=findViewById(R.id.phoneNumber);priceLevel=findViewById(R.id.priceLevel);
        numberOfReviews=findViewById(R.id.numberOfReviews); discount=findViewById(R.id.discount); deliveryCost=findViewById(R.id.deliveryCost);
        rating=findViewById(R.id.rating);

        itemName=findViewById(R.id.itemName);description=findViewById(R.id.description);itemType=findViewById(R.id.itemType); price=findViewById(R.id.price);
        quantity=findViewById(R.id.quantity);

        addItem=findViewById(R.id.add_item); addRestaurant=findViewById(R.id.btn_add_restaurant);
        addItem.setOnClickListener(this);
        
        itemList=new ArrayList<>(); categories=new ArrayList<>();
/**
        addRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restaurant=new RestaurantFirebase(name.getText().toString(),location.getText().toString(),phoneNumber.getText().toString(),
                        Integer.parseInt(numberOfReviews.getText().toString()),Integer.parseInt(discount.getText().toString()),
                        Integer.parseInt(deliveryCost.getText().toString()),priceLevel.getText().toString(),
                        categories,itemList,Float.parseFloat(rating.getText().toString()));

                repository.addRestaurantData(restaurant);
                Toast.makeText(DeveloperSettings.this, "Restaurant added successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.add_item:

                item=new Item(itemName.getText().toString(),description.getText().toString(),itemType.getText().toString(),
                        Integer.parseInt(price.getText().toString()),Integer.parseInt(quantity.getText().toString()));

                itemList.add(item); categories.add(itemType.getText().toString());

                Toast.makeText(this, "Item + Category added successfully", Toast.LENGTH_LONG).show();
                return;

            default:
                Toast.makeText(this, "Nothing happened", Toast.LENGTH_SHORT).show();
        }
 **/
    }


}