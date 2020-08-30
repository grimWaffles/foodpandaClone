package com.example.foodpandaclone.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.adapters.DiscountResAdapter;
import com.example.foodpandaclone.adapters.PickupFragAdapter;
import com.example.foodpandaclone.databases.FirebaseDatabaseHelper;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.RestaurantFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Delivery_Fragment extends Fragment {

    private RecyclerView treat_hobe,all_restaurants;
    private CardView panda_favorites;

    private List<RestaurantFirebase> restaurantList=new ArrayList<>();

    private DatabaseReference ref;

    private ProgressBar progressBar;

    private DiscountResAdapter dra;  private PickupFragAdapter pfa;

    private FirebaseDatabaseHelper dbHelper=new FirebaseDatabaseHelper();

    public Delivery_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        final View rootView= inflater.inflate(R.layout.fragment_delivery_,container,false);

        //initializing the recycler views
        Button btn_sync = rootView.findViewById(R.id.btn_sync);
        progressBar=rootView.findViewById(R.id.progress);
        panda_favorites=rootView.findViewById(R.id.panda_favorites);
        treat_hobe=rootView.findViewById(R.id.treat_hobe);
        all_restaurants=rootView.findViewById(R.id.all_restaurants);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);



        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                restaurantList.clear();
                RestaurantFirebase res;

                for(DataSnapshot snap: snapshot.getChildren()){
                    res=snap.getValue(RestaurantFirebase.class);
                    restaurantList.add(res);
                }

                //adding adapters
                dra= new DiscountResAdapter(restaurantList);
                treat_hobe.setAdapter(dra); Log.d(" adapter","Adapter set");


                //adding adapters
                pfa= new PickupFragAdapter(restaurantList,"delivery");
                all_restaurants.setAdapter(pfa);

                //notify data changed
                dra.notifyDataSetChanged();
                pfa.notifyDataSetChanged();

                for(RestaurantFirebase r:restaurantList){

                    if(r==restaurantList.get(0)){
                        r.setRestaurantID(202008301);
                    }

                    else if(r==restaurantList.get(1)){
                        r.setRestaurantID(202008302);
                    }

                    dbHelper.insertRestaurantData(r);
                    Toast.makeText(rootView.getContext(), "Finished sync", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        progressBar.setVisibility(View.GONE);


        //onClick functionality and layout managers

        treat_hobe.setLayoutManager(linearLayoutManager);  all_restaurants.setLayoutManager(new LinearLayoutManager(getActivity()));

        panda_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Card functions not implemented yet",Toast.LENGTH_LONG).show();
            }
        });

        btn_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabaseHelper dbHelper=new FirebaseDatabaseHelper();

                if(restaurantList!=null){
                    for(RestaurantFirebase res:restaurantList){

                        if(res==restaurantList.get(0)){
                            res.setRestaurantID(202008301);
                        }

                        else if(res==restaurantList.get(1)){
                            res.setRestaurantID(202008302);
                        }

                        dbHelper.insertRestaurantData(res);
                        Toast.makeText(rootView.getContext(), "Finished sync", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return rootView;
    }
}