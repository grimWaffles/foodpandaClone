package com.example.foodpandaclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RiderCartAdapter extends RecyclerView.Adapter<RiderCartAdapter.ViewHolder> {

    private RiderCartListener listener; private List<OrderItem> orderItems=new ArrayList<>(); private List<Restaurant> restaurantList=new ArrayList<>();

    public interface RiderCartListener{
        void onCartItemClick(int position);
    }

    @NonNull
    @Override
    public RiderCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rider_order_item,parent,false);

        return new RiderCartAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        CardView cardView=holder.cardView;

        OrderItem item=orderItems.get(position);

        TextView restaurant_name=cardView.findViewById(R.id.tv_restaurant_name);
        TextView item_name=cardView.findViewById(R.id.tv_item_name);
        TextView quantity=cardView.findViewById(R.id.tv_item_quantity);
        TextView netPrice=cardView.findViewById(R.id.tv_net_item_price);

        restaurant_name.setText(getRestaurantName(item.getRestaurantID()));
        item_name.setText(item.getName());
        quantity.setText("x"+item.getQuantity());
        netPrice.setText(getNetItemPrice(item.getPrice(),item.getQuantity())+ "Tk");


        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(listener!=null){
                    listener.onCartItemClick(position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {

        if(orderItems.size()==0){
            return 0;
        }
        else{
            return orderItems.size();
        }
    }

    public void setOrderLists(List<OrderItem> orderItems, List<Restaurant> restaurantList){
        this.orderItems=orderItems;
        this.restaurantList=restaurantList;

        notifyDataSetChanged();
    }

    public String getRestaurantName(int id){

        String resname="";

        for(Restaurant restaurant:restaurantList){
            if(restaurant.getResID()==id){
                resname=restaurant.getResName();
            }
        }

        return resname;
    }

    public String getNetItemPrice(int quantity, int price){
        return Integer.toString(quantity*price);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cardView=(CardView) itemView;
        }
    }
}
