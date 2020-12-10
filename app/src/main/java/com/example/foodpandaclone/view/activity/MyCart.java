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
    private List<Restaurant> res; private Restaurant restaurant;private User user; private List<Item> itemList;
    private List<OrderItem> orderItems; private boolean orderPending =false;

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

        user=new User(); restaurant=new Restaurant(); t_discount=0;

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
                    mcVM.getCurrentUser().observe(MyCart.this, new Observer<List<User>>() {
                        @Override
                        public void onChanged(List<User> users) {
                            MyCart.this.user=users.get(0);
                            findTotalDiscount(res);
                        }
                    });

                    mcVM.getOrderItemsFromLocal().observe(MyCart.this, new Observer<List<Item>>() {
                        @Override
                        public void onChanged(final List<Item> items) {

                            adapter.setCartItems(items); itemList=items;

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

                                            if(!orderPending){
                                                mcVM.decreaseItemFromOrder(item.getItemID(),item.getRestaurantID());
                                            }
                                            else{
                                                Toast.makeText(MyCart.this, "An order  is currently active", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).start();
                                }
                            });

                            inputPricingInTheCards(items);

                        }
                    });
                }
            }
        });

        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(user.getUserID()==1){
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

                           final Order currentOrder=new Order(202010,user.getUserID(),0,"pending",Integer.parseInt(total.getText().toString()),t_discount,getCurrentDate());
                           orderItems=convertItemsToOrderItems(currentOrder,itemList);

                           Toast.makeText(MyCart.this, "This may take a while XD", Toast.LENGTH_SHORT).show();

                           new Thread(new Runnable() {
                               @Override
                               public void run() {

                                   mcVM.insertOrderToLocal(currentOrder);
                                   mcVM.insertOrderItemsToLocal(orderItems);

                                   OrderFirebase orderFirebase=new OrderFirebase(currentOrder, orderItems);

                                   mcVM.uploadOrderToFirebase(orderFirebase);

                               }
                           }).start();

                           progressBar.setVisibility(View.GONE);

                           startActivity(new Intent(MyCart.this,ActiveOrder.class));
                           finish();
                       }
                       else{
                           Toast.makeText(MyCart.this, "Order currently in progress", Toast.LENGTH_SHORT).show();
                       }

                   }
               }
            }
        });
    }

    private void findTotalDiscount(List<Restaurant> restaurantList) {

        int discount=0; int count=0;

        for(Restaurant r:restaurantList){
            discount+=r.getDiscount();
            count++;
        }

        t_discount=discount/count;

    }

    private void inputPricingInTheCards(List<Item> items) {

        int s_cost=0; int t_cost=0; int t_delivery=0;

        for(Item i :items){

            s_cost+=i.getPrice()*i.getQuantity();

            for(Restaurant r:res){
                if(i.getRestaurantID()==r.getResID()){
                    restaurant=r;
                }
                if(t_delivery==0 && restaurant.getDeliveryCost()!=0){
                    t_delivery=restaurant.getDeliveryCost();
                }
            }
        }

        t_cost=s_cost-t_discount+t_delivery;

        //set values to cards
        subtotal.setText(Integer.toString(s_cost)); discount.setText(Integer.toString(t_discount)); delivery_cost.setText(Integer.toString(t_delivery));total.setText(Integer.toString(t_cost));

    }

    private String getCurrentDate(){

        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return simpleDateFormat.format(calendar.getTime());
    }
    
    private List<OrderItem> convertItemsToOrderItems(Order order,List<Item> items){

        List<OrderItem> theOrderItems=new ArrayList<>();

        for(Item i:items){
            theOrderItems.add(new OrderItem(order.getOrderID(),i));
        }

        return theOrderItems;
    }
}