package com.example.foodpandaclone.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.viewModel.LogoPageViewModel;

public class LogoPage extends AppCompatActivity {

    private ImageView logo_img; private TextView tvName; private LogoPageViewModel mLPVM; private ProgressBar pbarMain; private boolean statusFire=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo__page);

        logo_img=findViewById(R.id.logo_img);
        tvName=findViewById(R.id.logo_name);
        pbarMain=findViewById(R.id.progress);

        pbarMain.setVisibility(View.GONE);

        mLPVM =new ViewModelProvider(this).get(LogoPageViewModel.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mLPVM.clearLocalStorage();
            }
        });

        Toast.makeText(this, "Update complete", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this,LocationAccess.class));
    }
}