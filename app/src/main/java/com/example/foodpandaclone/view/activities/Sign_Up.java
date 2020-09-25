package com.example.foodpandaclone.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.viewModel.Sign_Up_ViewModel;

public class Sign_Up extends AppCompatActivity {

    EditText user_phone,user_email,user_password;
    Button btn_signup;
    Toolbar toolbar;

    Sign_Up_ViewModel svm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        user_email=findViewById(R.id.user_email);
        user_phone=findViewById(R.id.user_phone);
        user_password=findViewById(R.id.user_password);
        btn_signup=findViewById(R.id.btn_signup);

        toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.setTitle("Fill in the details below");

        svm=new ViewModelProvider(this).get(Sign_Up_ViewModel.class);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!user_email.getText().toString().isEmpty() && !user_password.getText().toString().isEmpty() &&!user_phone.getText().toString().isEmpty()){

                    Intent intent=new Intent();
                    intent.putExtra("email",user_email.getText().toString());
                    intent.putExtra("password",user_password.getText().toString());

                    setResult(RESULT_OK,intent);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User newUser=new User(user_email.getText().toString(),user_phone.getText().toString(),user_password.getText().toString());
                            svm.addUserToDB(newUser);
                        }
                    }).start();

                    finish();

                }
                else{
                    Toast.makeText(Sign_Up.this, "Entries are not filled correctly", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}