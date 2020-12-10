package com.example.foodpandaclone.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapter.RiderOrderAdapter;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.view.fragment.CustomDialogFragment;
import com.example.foodpandaclone.view.fragment.RiderOrderListFragment;
import com.example.foodpandaclone.viewModel.MainActivityRiderViewModel;

import java.util.List;

public class MainActivity_Rider extends AppCompatActivity implements RiderOrderAdapter.OnOrderItemClick, CustomDialogFragment.OnPromptClick {

    //Widgets
    private Toolbar toolbar;

    //ViewModel
    private MainActivityRiderViewModel marVM;

    //Variables
    private FragmentTransaction ft; private FragmentManager fm=getFragmentManager(); private boolean riderBusy =false; private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__rider);

        this.setTitle("Bhook lagi?");
        
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        marVM=new ViewModelProvider(this).get(MainActivityRiderViewModel.class);

        marVM.checkForPendingOrders();

    }

    @Override
    protected void onStart() {
        super.onStart();

        marVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                if(users.get(0).getType().equals(("User"))){
                    startActivity(new Intent(MainActivity_Rider.this,MainActivity.class));
                    finish();
                }
                else{
                    mUser=users.get(0);
                }
            }
        });

        marVM.getPendingOrdersFromFirebase().observe(this,new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {

                if(orders.size()!=0 && !riderBusy){
                    loadOrderListFragment(orders);
                }
                else if(orders==null){
                    loadDialogFragment("No orders are currently available");
                }
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
                Toast.makeText(this, "Functionality not added yet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.my_orders:
                Toast.makeText(this, "Functionality not available yet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.my_account:
                Toast.makeText(this, "Functionality not available yet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout_btn:
                if(mUser!=null){
                    marVM.logoutUser(mUser);
                }
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOrderItemClick(int id) {
        loadDialogFragment("");
    }

    @Override
    public void onPromptClick(String message) {
        Toast.makeText(this, "Does Absolutely nothing", Toast.LENGTH_SHORT).show();
    }

    private void loadDialogFragment(String message) {

        CustomDialogFragment dialogFragment=new CustomDialogFragment(message);

        dialogFragment.show(fm,"My custom Dialog");
    }

    private void loadOrderListFragment(List<Order> orders) {
        ft=fm.beginTransaction();
        ft.replace(R.id.frame_layout,new RiderOrderListFragment(MainActivity_Rider.this,orders));
        ft.commit();
    }
}