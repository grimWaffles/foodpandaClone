package com.example.foodpandaclone.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.example.foodpandaclone.view.fragments.CustomDialogFragment;
import com.example.foodpandaclone.view.fragments.RiderOrderListFragment;
import com.example.foodpandaclone.viewModel.MainActivityRiderViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Rider extends AppCompatActivity implements RiderOrderAdapter.OnOrderItemClick, CustomDialogFragment.OnPromptClick {
    
    private Toolbar toolbar; private MainActivityRiderViewModel marVM; private List<Order> orders; private FragmentTransaction ft; private FragmentManager fm=getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__rider);

        this.setTitle("Bhook lagi?");
        orders=new ArrayList<>();
        
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        marVM=new ViewModelProvider(this).get(MainActivityRiderViewModel.class);

        marVM.checkForPendingOrders();

        marVM.getCurrentUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                if(users.get(0).getType().equals(("User"))){
                    startActivity(new Intent(MainActivity_Rider.this,MainActivity.class));
                    finish();
                }
            }
        });

        marVM.getPendingOrdersFromFirebase().observe(this,new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {

                ft=fm.beginTransaction();
                ft.replace(R.id.frame_layout,new RiderOrderListFragment(MainActivity_Rider.this,orders));
                ft.commit();

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
                Toast.makeText(this, "Functionality not available yet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.my_account:
                Toast.makeText(this, "Functionality not available yet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout_btn:
                marVM.logoutUser();
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOrderItemClick(int id) {
        loadDialogFragment();
    }

    private void loadDialogFragment() {

        CustomDialogFragment dialogFragment=new CustomDialogFragment();

        dialogFragment.show(fm,"My custom Dialog");
    }

    @Override
    public void onPromptClick() {
        Toast.makeText(this, "Does Absolutely nothing", Toast.LENGTH_SHORT).show();
    }
}