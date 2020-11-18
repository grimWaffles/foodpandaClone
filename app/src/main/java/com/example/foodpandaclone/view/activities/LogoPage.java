package com.example.foodpandaclone.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.viewModel.LogoPageViewModel;

import java.util.List;

import static java.lang.Thread.currentThread;

public class LogoPage extends AppCompatActivity {

    private ImageView logo_img; private TextView tvName; private LogoPageViewModel mLPVM; private ProgressBar pbarMain; private boolean statusFire=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo__page);

        logo_img=findViewById(R.id.logo_img);
        tvName=findViewById(R.id.logo_name);
        pbarMain=findViewById(R.id.progress);

        pbarMain.setVisibility(View.VISIBLE);

        mLPVM =new ViewModelProvider(this).get(LogoPageViewModel.class);

        mLPVM.getUserFromLocal().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                if(users==null || users.size()==0){
                    mLPVM.insertBlankUserToLocal();
                }
                else if(users.size()>0){

                    mLPVM.clearLocalStorage();

                    Toast.makeText(LogoPage.this, "Updates complete", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(LogoPage.this,LocationAccess.class);

                    if(users.get(0).getType().equals("Rider")){

                        intent.putExtra("type","Rider");
                    }
                    else{
                        intent.putExtra("type","User");
                    }

                    startActivity(intent);
                    finish();

                }

            }
        });
    }
}