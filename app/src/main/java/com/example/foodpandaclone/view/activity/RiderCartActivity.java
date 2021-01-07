package com.example.foodpandaclone.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.RiderCartAdapter;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.viewModel.RiderCartActivityViewModel;

import java.util.List;

public class RiderCartActivity extends AppCompatActivity implements RiderCartAdapter.RiderCartListener {

    public static final String TAG="RiderCartActivity";

    //ViewModel
    private RiderCartActivityViewModel rcVM;

    //Widgets
    private Toolbar toolbar; private RecyclerView recyclerView; private RiderCartAdapter adapter; private LinearLayoutManager llm;
    private Button btn_items_bought;

    //Variables
    private List<OrderItem> orderItems; private String orderID="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_cart);

        this.setTitle("Items in cart");

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(getIntent().getExtras().getString("orderID")!=null){
            orderID=getIntent().getExtras().getString("orderID");
        }

        recyclerView=findViewById(R.id.recycler_view);
        adapter=new RiderCartAdapter();
        llm=new LinearLayoutManager(this);

        btn_items_bought =findViewById(R.id.btn_items_bought);

        recyclerView.setLayoutManager(llm);

        rcVM= new ViewModelProvider(this).get(RiderCartActivityViewModel.class);

        btn_items_bought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcVM.updateOrderItemsBought(orderID);

                startActivity(new Intent(RiderCartActivity.this,MainActivity_Rider.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();



        rcVM.getOrderItems().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(final List<OrderItem> orderItems) {

                RiderCartActivity.this.orderItems=orderItems;
            }
        });

        rcVM.getRestaurantList().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurantList) {

                if(orderItems!=null && restaurantList!=null){
                    adapter.setOrderLists(orderItems,restaurantList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    //Adapter interface method
    @Override
    public void onCartItemClick(int position) {
        //todo
    }
}
