package com.example.foodpandaclone.view.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.foodpandaclone.R;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cart_items; private CardView bill_amount, contact_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        cart_items=findViewById(R.id.cart_item_recycler);
        bill_amount=findViewById(R.id.bill_amount_card);
        contact_info=findViewById(R.id.contact_information);




    }
}