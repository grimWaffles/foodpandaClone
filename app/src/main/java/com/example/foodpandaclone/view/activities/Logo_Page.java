package com.example.foodpandaclone.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.DummyDB;

public class Logo_Page extends AppCompatActivity {

    private ImageView logo_img; private TextView tvName;private DummyDB dummy; private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo__page);

        logo_img=findViewById(R.id.logo_img);
        tvName=findViewById(R.id.logo_name);

        dummy=new DummyDB();

        if(dummy.isDatabaseAvailable()){
            intent=new Intent(this,Location_Access_Page.class);
            startActivity(intent);
            finish();
        }
    }
}