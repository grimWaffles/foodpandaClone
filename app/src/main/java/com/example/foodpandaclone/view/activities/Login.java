package com.example.foodpandaclone.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.viewModel.LoginViewModel;

import java.util.List;

public class Login extends AppCompatActivity {

    EditText user_phone,user_password;

    Button btn_login,btn_gotosignup; ProgressBar pbmain;

    Toolbar toolbar;
    final int SIGN_UP_ACTIVITY=2;

    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_phone =findViewById(R.id.user_email);
        user_password=findViewById(R.id.user_password);
        btn_login =findViewById(R.id.btn_login);
        btn_gotosignup=findViewById(R.id.btn_gotosignup);

        toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.setTitle("Login");

        pbmain=findViewById(R.id.pbmain);
        pbmain.setVisibility(View.GONE);

        loginViewModel=new ViewModelProvider(this).get(LoginViewModel.class);

        btn_gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(Login.this,Sign_Up.class),SIGN_UP_ACTIVITY);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!user_phone.getText().toString().isEmpty() && !user_password.getText().toString().isEmpty()){

                   loginViewModel.loginUser(Integer.parseInt(user_phone.getText().toString()),user_password.getText().toString()).observe(Login.this, new Observer<List<User>>() {
                       @Override
                       public void onChanged(List<User> users) {

                           if(users.get(0).getUserID()==1 && users.get(0).getLogin_status().equals("Not logged in")){
                               pbmain.setVisibility(View.VISIBLE);
                           }
                           else if(users.get(0).getUserID()==1 && users.get(0).getLogin_status().equals("Account not found")){
                               pbmain.setVisibility(View.GONE);

                               Toast.makeText(Login.this, "User account does not exist", Toast.LENGTH_SHORT).show();
                           }
                           else{
                               pbmain.setVisibility(View.GONE);
                               Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                               setResult(RESULT_OK,new Intent());
                               finish();
                           }

                       }
                   });
               }

               else{
                   Toast.makeText(Login.this, "Fields are empty", Toast.LENGTH_SHORT).show();
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
                user_phone.setText(data.getStringExtra("email"));
                user_password.setText(data.getStringExtra("password"));
            }
            else{
                Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}