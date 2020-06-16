package com.example.foodpandaclone.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.foodpandaclone.R;

public class Logo_Page extends AppCompatActivity {

    private ImageView logo_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo__page);

        logo_img=findViewById(R.id.logo_img);

        //main thread waits 4 secs  for dramtic effect
        synchronized (this){
            try{
                wait(5000);

            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        Intent intent=new Intent(this,Location_Access_Page.class);
        startActivity(intent);
        finish();
    }
}