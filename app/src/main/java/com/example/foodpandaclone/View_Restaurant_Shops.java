package com.example.foodpandaclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class View_Restaurant_Shops extends AppCompatActivity implements View.OnClickListener{

    private TextView promptMessage; private Button go_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__restaurant__shops);

        promptMessage=findViewById(R.id.promptMessage); go_next=findViewById(R.id.go_next);

        go_next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.go_next:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
        }
    }
}