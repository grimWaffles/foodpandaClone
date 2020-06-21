package com.example.foodpandaclone.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.foodpandaclone.R;

public class View_Restaurant_Shops extends AppCompatActivity implements View.OnClickListener{

    CardView orderCard, shopCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__restaurant__shops);

        orderCard=findViewById(R.id.to_food);
        shopCard=findViewById(R.id.to_shops);

        orderCard.setOnClickListener(this);
        shopCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.to_food || view.getId()==R.id.to_shops){
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}