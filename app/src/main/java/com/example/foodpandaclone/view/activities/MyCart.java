package com.example.foodpandaclone.view.activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.MyCartItemsAdapter;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.viewModel.MyCartViewModel;

import java.util.Date;
import java.util.List;

public class MyCart extends AppCompatActivity {

    private RecyclerView  rv_main;

    private CardView card_cart_total;

    TextView subtotal,discount,total; //inside the card_cart_total

    Button orderNow;

    Toolbar toolbar;

    private MyCartItemsAdapter adapter; private MyCartViewModel mcVM; private LinearLayoutManager llm;
    private List<Restaurant> res; private Restaurant restaurant;private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.setTitle("My Cart");

        user=new User();

        rv_main=findViewById(R.id.rv_cart);
        card_cart_total=findViewById(R.id.card_cart_total);

        subtotal=card_cart_total.findViewById(R.id.tv_sta);
        discount=card_cart_total.findViewById(R.id.tv_disa);
        total=card_cart_total.findViewById(R.id.tv_ta);

        orderNow=findViewById(R.id.order_btn);

        mcVM=new ViewModelProvider(this).get(MyCartViewModel.class);

        adapter=new MyCartItemsAdapter();

        llm=new LinearLayoutManager(this);

        mcVM.getRestaurantData().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                res=restaurants;
            }
        });

        mcVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                MyCart.this.user=users.get(0);
            }
        });

        mcVM.getOrderItemsFromLocal().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(final List<Item> items) {

                adapter.setCartItems(items);

                rv_main.setAdapter(adapter);
                rv_main.setLayoutManager(llm);

                adapter.setListener(new MyCartItemsAdapter.Listener() {
                    @Override
                    public void onClick(int position) {

                        Toast.makeText(MyCart.this, "Item Quantity reduced by 1 unit", Toast.LENGTH_SHORT).show();

                        final Item item=items.get(position);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mcVM.decreaseItemFromOrder(item.getItemID(),item.getRestaurantID());
                            }
                        }).start();
                    }
                });

                inputPricingInTheCards(items);

            }
        });


        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(user.getUserID()==1){
                   Toast.makeText(MyCart.this, "You need to login first!", Toast.LENGTH_SHORT).show();
               }
               else{

                   String id=Integer.toString(1)+Integer.toString(user.getUserID());

                   final Order currentOrder=new Order(Integer.parseInt(id),user.getUserID(),0,Integer.toString(new Date().getDate()),"pending");

                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           mcVM.insertOrderToLocal(currentOrder);
                       }
                   }).start();

                   Toast.makeText(MyCart.this, "This may take a while XD", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(MyCart.this,ActiveOrder.class));
                   finish();
               }
            }
        });


    }

    private void inputPricingInTheCards(List<Item> items) {

        int s_cost=0;int t_discount=0; int t_cost=0;

        for(Item i :items){

            s_cost+=i.getPrice()*i.getQuantity();

            for(Restaurant r:res){
                if(i.getRestaurantID()==r.getResID()){
                    restaurant=r;
                }
            }
            if(i.getPrice()>restaurant.getDiscount()){
                t_discount+=restaurant.getDiscount();
            }

        }

        t_cost=s_cost-t_discount;

        //set values to cards
        subtotal.setText(Integer.toString(s_cost)); discount.setText(Integer.toString(t_discount)); total.setText(Integer.toString(t_cost));

    }
}