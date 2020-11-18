package com.example.foodpandaclone.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.RiderOrderAdapter;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.OrderFirebase;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.viewModel.MainActivityRiderViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Rider extends AppCompatActivity {
    
    private Toolbar toolbar; private RecyclerView recyclerView; private LinearLayoutManager linearLayoutManager; private RiderOrderAdapter adapter; private List<Order> orders;
    private MainActivityRiderViewModel marVM; private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__rider);

        this.setTitle("Bhook lagi?");
        orders=new ArrayList<>();
        
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recycler_view_rider);
        linearLayoutManager=new LinearLayoutManager(this);
        adapter=new RiderOrderAdapter();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        marVM=new ViewModelProvider(this).get(MainActivityRiderViewModel.class);

        handler=new Handler();

        marVM.checkForPendingOrders();

        marVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if(users.get(0).getType().equals(("user"))){
                    startActivity(new Intent(MainActivity_Rider.this,MainActivity.class));
                    finish();
                }
            }
        });

        marVM.getPendingOrdersFromFirebase().observe(this,new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                Log.d("Searching for changes","Yes");
                adapter.setOrders(orders);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        getMenuInflater().inflate(R.menu.toolbar_menu_rider,menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        switch(item.getItemId()){

            case R.id.my_cart:
                Toast.makeText(this, "Fucntionality not added yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_orders:
                marVM.logoutUser();
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }
}