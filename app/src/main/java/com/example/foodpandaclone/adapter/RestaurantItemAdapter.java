package com.example.foodpandaclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.Item;

import java.util.List;

public class RestaurantItemAdapter extends RecyclerView.Adapter<RestaurantItemAdapter.ViewHolder> {

    public Listerner listener;  private List<Item> itemsR;

    public interface Listerner{
        void onClick(int position);
    }

    public void setListener(RestaurantItemAdapter.Listerner listener){
        this.listener= listener;
    }

    public RestaurantItemAdapter(List<Item> itemsR){
        this.itemsR=itemsR;
    }

    @NonNull
    @Override
    public RestaurantItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rf,parent,false);

        return new RestaurantItemAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RestaurantItemAdapter.ViewHolder holder, final int position) {

        CardView  cardView=holder.cardView;

        //get references
        TextView item_name=cardView.findViewById(R.id.item_name);
        TextView item_price=cardView.findViewById(R.id.item_price);
        TextView item_description=cardView.findViewById(R.id.item_description);
        TextView item_quantity=cardView.findViewById(R.id.item_quanitity);

        //set values

        Item singleitem=itemsR.get(position);

        item_name.setText(singleitem.getName());

        item_description.setText(singleitem.getDescription());

        item_price.setText(Integer.toString(singleitem.getPrice()));

        item_quantity.setText(Integer.toString(singleitem.getQuantity()));

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
        return itemsR.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView=cardView;
        }
    }
}
