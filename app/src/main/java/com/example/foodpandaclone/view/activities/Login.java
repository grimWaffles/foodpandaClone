package com.example.foodpandaclone.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodpandaclone.R;

public class Login extends AppCompatActivity {

    EditText user_email,user_password;

    Button btn_login,btn_gotosignup;

    Toolbar toolbar;

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

        this.setTitle("Log In");



    }
}