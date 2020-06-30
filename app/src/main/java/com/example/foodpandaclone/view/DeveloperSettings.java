package com.example.foodpandaclone.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.databases.Repository;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class DeveloperSettings extends AppCompatActivity implements View.OnClickListener{

    //Restaurant - Section 1
    private EditText name,location,phoneNumber,priceLevel,numberOfReviews,discount,deliveryCost,rating,addCategory;

    //Item - Section 2
    private EditText itemName,description,itemType,price,quantity;

    //Buttons
    Button addItem,addRestaurant,addRestaurantCategory;

    //Objects
    Item item; Restaurant restaurant; List<Item> itemList; List<String> categories;

    //Repository
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_settings);

        name=findViewById(R.id.name);location=findViewById(R.id.location); phoneNumber=findViewById(R.id.phoneNumber);priceLevel=findViewById(R.id.priceLevel);
        numberOfReviews=findViewById(R.id.numberOfReviews); discount=findViewById(R.id.discount); deliveryCost=findViewById(R.id.deliveryCost);
        rating=findViewById(R.id.rating); addCategory=findViewById(R.id.add_category);

        itemName=findViewById(R.id.itemName);description=findViewById(R.id.description);itemType=findViewById(R.id.itemType); price=findViewById(R.id.price);
        quantity=findViewById(R.id.quantity);

        addItem=findViewById(R.id.add_item); addRestaurant=findViewById(R.id.btn_add_restaurant); addRestaurantCategory=findViewById(R.id.add_restaurant_categories);
        addItem.setOnClickListener(this); addRestaurantCategory.setOnClickListener(this);
        itemList=new ArrayList<>(); categories=new ArrayList<>();

        addRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurant=new Restaurant(name.getText().toString(),location.getText().toString(),phoneNumber.getText().toString(),
                        Integer.parseInt(numberOfReviews.getText().toString()),Integer.parseInt(discount.getText().toString()),
                        Integer.parseInt(deliveryCost.getText().toString()),priceLevel.getText().toString(),
                        categories,itemList,Float.parseFloat(rating.getText().toString()));

                repository=new Repository();

                repository.addRestaurantData(restaurant);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_item:

                item=new Item(itemName.getText().toString(),description.getText().toString(),itemType.getText().toString(),
                        Integer.parseInt(price.getText().toString()),Integer.parseInt(quantity.getText().toString()));

                itemList.add(item);

                Toast.makeText(this, "Item added successfully", Toast.LENGTH_LONG).show();

            case R.id.add_restaurant_categories:

                categories.add(addCategory.getText().toString());
                Toast.makeText(this, "Category added success fully", Toast.LENGTH_LONG).show();
                addCategory.setText("");
        }
    }
}