package com.example.foodpandaclone.view.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.foodpandaclone.R;

public class Location_Access_Page extends AppCompatActivity implements View.OnClickListener {

    private ImageView  logo; private Button use_current_loc,select_another;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location__access__page);

        logo=findViewById(R.id.logo_img);
        use_current_loc=findViewById(R.id.use_current_loc);
        select_another=findViewById(R.id.select_another);

        use_current_loc.setOnClickListener(this);
        select_another.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent;

        if(view.getId()==R.id.use_current_loc || view.getId()==R.id.select_another){
            intent=new Intent(this,View_Restaurant_Shops.class);
            startActivity(intent);
            finish();
        }

    }
}