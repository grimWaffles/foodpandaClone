package com.example.foodpandaclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

        switch (view.getId()){

            case R.id.use_current_loc:

                intent=new Intent(this,View_Restaurant_Shops.class);
                startActivity(intent);
                finish();

            case R.id.select_another:

                intent=new Intent(this,View_Restaurant_Shops.class);
                startActivity(intent);
                finish();
        }

    }
}