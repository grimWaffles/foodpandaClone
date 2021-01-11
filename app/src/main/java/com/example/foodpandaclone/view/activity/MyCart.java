package com.example.foodpandaclone.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.MyCartItemsAdapter;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.OrderFirebase;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.viewModel.MyCartViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyCart extends AppCompatActivity {

    private RecyclerView  rv_main;

    private CardView card_cart_total;

    TextView subtotal,discount,delivery_cost,total; //inside card_cart_total

    Button orderNow;

    Toolbar toolbar; private ProgressBar progressBar; private int t_discount;

    private MyCartItemsAdapter adapter; private MyCartViewModel mcVM; private LinearLayoutManager llm;
    private List<Restaurant> res; private Restaurant restaurant;private User mUser; private List<Item> itemList;
    private boolean orderPending =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.setTitle("My Cart");

        progressBar=findViewById(R.id.pb_cart); progressBar.setVisibility(View.GONE);

        mUser =new User(); restaurant=new Restaurant(); t_discount=0;

        rv_main=findViewById(R.id.rv_cart);
        card_cart_total=findViewById(R.id.card_cart_total);

        subtotal=card_cart_total.findViewById(R.id.tv_sta);
        discount=card_cart_total.findViewById(R.id.tv_disa);
        total=card_cart_total.findViewById(R.id.tv_ta);
        delivery_cost=card_cart_total.findViewById(R.id.tv_delamount);

        orderNow=findViewById(R.id.order_btn);

        mcVM=new ViewModelProvider(this).get(MyCartViewModel.class);

        adapter=new MyCartItemsAdapter();

        llm=new LinearLayoutManager(this);

        rv_main.setAdapter(adapter);
        rv_main.setLayoutManager(llm);

        adapter.setListener(new MyCartItemsAdapter.Listener() {
            @Override
            public void onClick(int position) {

                final Item item=itemList.get(position);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if(!orderPending){
                            mcVM.decreaseItemFromOrder(item.getItemID(),item.getRestaurantID());
                        }
                        else{
                            Toast.makeText(MyCart.this, "An order is currently active", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).start();

                Toast.makeText(MyCart.this, "Item Quantity reduced by 1 unit", Toast.LENGTH_SHORT).show();

            }
        });

        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(mUser.getUserID()==1){
                   Toast.makeText(MyCart.this, "You need to login first!", Toast.LENGTH_SHORT).show();
                   //shows this prompt and takes you to login screen
                   startActivity(new Intent(MyCart.this,Login.class));
               }

               else{

                   if(itemList.size()==0){
                       Toast.makeText(MyCart.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                   }
                   else{

                       if(!orderPending){
                           progressBar.setVisibility(View.VISIBLE);

                           Toast.makeText(MyCart .this, "This may take a while XD", Toast.LENGTH_SHORT).show();

                           //add validator here
                           mcVM.processOrder(mUser,itemList,Integer.parseInt(String.valueOf(total.getText())),Integer.parseInt(String.valueOf(discount.getText())));

                           progressBar.setVisibility(View.GONE);

                           synchronized (this){
                               try{
                                   wait(3000);
                               }
                               catch(Exception e){
                                   e.printStackTrace();
                               }
                           }

                           startActivity(new Intent(MyCart.this,ActiveOrder.class));
                           finish();

                           // TODO: 10-Jan-21 Uncomment these and test
                       }
                       else{
                           Toast.makeText(MyCart.this, "Order currently in progress", Toast.LENGTH_SHORT).show();
                       }

                   }
               }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mcVM.getCurrentUser().observe(MyCart.this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                MyCart.this.mUser =users.get(0);

            }
        });

        mcVM.getOrderFromLocal().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {

                if(orders.size()!=0){

                    if(orders.get(0).getStatus().equals("pending")){
                        orderPending =true;
                    }
                }
            }
        });

        mcVM.getRestaurantData().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(final List<Restaurant> restaurants) {
                res=restaurants;

                if(res!=null){
                    t_discount=mcVM.findTotalDiscount(restaurants);

                    updateCartUI(itemList);
                }
            }
        });

        mcVM.getOrderItemsFromLocal().observe(MyCart.this, new Observer<List<Item>>() {
            @Override
            public void onChanged(final List<Item> items) {

                itemList=items;

                adapter.setCartItems(items);

                updateCartUI(itemList);
            }
        });
    }



    private void updateCartUI(List<Item> items) {

        if(items!=null && items.size()!=0){
            int s_cost=0; int t_cost=0; int t_delivery=0;

            for(Item i :items){

                s_cost+=i.getPrice()*i.getQuantity();

                if(res!=null){
                    for(Restaurant r:res){
                        if(i.getRestaurantID()==r.getResID()){
                            restaurant=r;
                        }
                        if(t_delivery==0 && restaurant.getDeliveryCost()!=0){
                            t_delivery=restaurant.getDeliveryCost();
                        }
                    }
                }
            }

            t_cost=s_cost-t_discount+t_delivery;

            //set values to cards
            subtotal.setText(Integer.toString(s_cost));
            discount.setText(Integer.toString(t_discount));
            delivery_cost.setText(Integer.toString(t_delivery));
            total.setText(Integer.toString(t_cost));
        }

    }
}