package com.example.foodpandaclone.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.Order;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private List<Order>  orderList; public Listener listener;

    public interface Listener{
        void onClick(int position);
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_my_order,parent,false);

        return new MyOrderAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, final int position) {

        CardView cardView=holder.cardView;
        Order order=orderList.get(position);

        TextView tv_date=cardView.findViewById(R.id.tv_date);
        TextView tv_tcost=cardView.findViewById(R.id.tv_totalcost);
        TextView tv_orderID=cardView.findViewById(R.id.tv_orderID);
        TextView tv_status=cardView.findViewById(R.id.tv_status);

        tv_date.setText(order.getDate());
        tv_tcost.setText("BDT."+Integer.toString(order.getTotal_cost()));
        tv_orderID.setText("#"+Integer.toString(order.getOrderID()));
        Log.d("Order status",order.getStatus());
        tv_status.setText(order.getStatus());

        //onClick functionality
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

       if(orderList.size()==0){
           return 0;
       }
       else{
           return orderList.size();
       }
    }

    public void setOrderList(List<Order> orders){
        this.orderList=new ArrayList<>();
        this.orderList=orders;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView=cardView;
        }
    }
}
