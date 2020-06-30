package com.example.foodpandaclone.view.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.databases.Repository;
import com.example.foodpandaclone.model.DummyDB;
import com.example.foodpandaclone.model.Restaurant;

import java.util.List;

public class Logo_Page extends AppCompatActivity {

    private ImageView logo_img; private TextView tvName;private DummyDB dummy; private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo__page);

        logo_img=findViewById(R.id.logo_img);
        tvName=findViewById(R.id.logo_name);

        Repository repository=new Repository();
        List<Restaurant> restaurants=repository.restaurantData;
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}