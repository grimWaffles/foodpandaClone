package com.example.foodpandaclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.OrderFirebase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RiderOrderAdapter extends RecyclerView.Adapter<RiderOrderAdapter.ViewHolder> {

    private List<Order> allOrders=new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView= (CardView) LayoutInflater.from(parent.getContext()).inflate(com.example.foodpandaclone.R.layout.card_rider_orders,parent,false);

        return new RiderOrderAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CardView card=holder.cardView;

        Order order=allOrders.get(position);

        TextView orderID=card.findViewById(R.id.tv_orderID);
        TextView customer_name=card.findViewById(R.id.tv_customer_name);
        TextView total_price=card.findViewById(R.id.tv_total_price);

        orderID.setText("#OrderID: "+Integer.toString(order.getOrderID()));
        customer_name.setText("Phone Number: "+"+880"+Integer.toString(order.getUserID()));
        total_price.setText("Total Price: "+Integer.toString(order.getTotal_cost()));

    }

    public void setOrders(List<Order> orders){
        this.allOrders=orders;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
       if(allOrders==null || allOrders.size()==0){
           return 0;
       }
       else{
          return allOrders.size();
       }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cardView=(CardView) itemView;

            // TODO: 15-Nov-20 Add onClickListener and add to activity
        }
    }
}
