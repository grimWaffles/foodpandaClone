package com.example.foodpandaclone.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.MyOrderAdapter;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.viewModel.MyOrderViewModel;

import java.util.List;

public class MyOrder extends AppCompatActivity {

    private Toolbar toolbar; private RecyclerView recyclerView; private MyOrderViewModel moVM; private MyOrderAdapter adapter;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        this.setTitle("My Orders");

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        adapter=new MyOrderAdapter();

        recyclerView=findViewById(R.id.recyclerView);
        llm=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);

        moVM=new ViewModelProvider(this).get(MyOrderViewModel.class);

        moVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                Log.d("Users fetched","Yes");

                if(users.get(0).getLogin_status().equals("Logged in")){

                    moVM.downloadUserOrders(users.get(0).getUserID());

                    moVM.getUserOrders().observe(MyOrder.this, new Observer<List<Order>>() {
                        @Override
                        public void onChanged(List<Order> orders) {
                            adapter.setOrderList(orders);
                            recyclerView.setAdapter(adapter);
                        }
                    });

                }
                else{
                    Toast.makeText(MyOrder.this, "You have to login first", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}