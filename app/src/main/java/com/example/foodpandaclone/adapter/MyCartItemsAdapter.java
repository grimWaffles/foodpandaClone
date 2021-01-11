package com.example.foodpandaclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.Item;

import org.w3c.dom.Text;

import java.util.List;

public class MyCartItemsAdapter extends RecyclerView.Adapter<MyCartItemsAdapter.ViewHolder> {

    private List<Item> cartItems; public Listener listener;

    public interface Listener{
        void onClick(int position);
    }

    public void setListener(MyCartItemsAdapter.Listener listener){
        this.listener= listener;
    }

    @NonNull
    @Override
    public MyCartItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart_item,parent,false);

        return new MyCartItemsAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        CardView  cardView=holder.cardView;

        TextView quantity=cardView.findViewById(R.id.i_quantity);

        TextView itemName=cardView.findViewById(R.id.item_name);
        TextView itemCost=cardView.findViewById(R.id.item_cost);

        Item item=cartItems.get(position);

        quantity.setText(Integer.toString(item.getQuantity()));
        itemName.setText(item.getName());

        int t_cost=item.getQuantity()* item.getPrice();

        itemCost.setText(Integer.toString(t_cost)+ "Tk");

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
        if(cartItems==null ||cartItems.size()==0){
            return 0;
        }
        else{
            return cartItems.size();
        }
    }

    public void setCartItems(List<Item> items){
        this.cartItems=items;
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