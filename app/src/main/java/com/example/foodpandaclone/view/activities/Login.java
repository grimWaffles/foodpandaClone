package com.example.foodpandaclone.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodpandaclone.R;

public class Login extends AppCompatActivity {

    EditText user_email,user_password;

    Button btn_login,btn_gotosignup;

    Toolbar toolbar;
    final int SIGN_UP_ACTIVITY=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_email=findViewById(R.id.user_email);
        user_password=findViewById(R.id.user_password);
        btn_login =findViewById(R.id.btn_login);
        btn_gotosignup=findViewById(R.id.btn_gotosignup);

        toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.setTitle("Log In Here");

        btn_gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(Login.this,Sign_Up.class),SIGN_UP_ACTIVITY);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!user_email.getText().toString().isEmpty() && !user_password.getText().toString().isEmpty()){
                   setResult(RESULT_OK,new Intent());
                   finish();
               }

               else{
                   Toast.makeText(Login.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==SIGN_UP_ACTIVITY){
            if(resultCode==RESULT_OK){
                Toast.makeText(this, "Sign up  successful", Toast.LENGTH_SHORT).show();
                user_email.setText(data.getStringExtra("email"));
                user_password.setText(data.getStringExtra("password"));
            }
            else{
                Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}