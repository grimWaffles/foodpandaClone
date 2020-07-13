package com.example.foodpandaclone.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.Item;

import org.w3c.dom.Text;

import java.util.List;

public class ResItemAdapter extends RecyclerView.Adapter<ResItemAdapter.ViewHolder> {

    public Listerner listener;  private List<Item> itemsR;

    public interface Listerner{
        void onClick(int position);
    }

    public void setListener(ResItemAdapter.Listerner listener){
        this.listener= listener;
    }

    public ResItemAdapter(List<Item> itemsR){
        this.itemsR=itemsR;
    }

    @NonNull
    @Override
    public ResItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cardView=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rf,parent,false);

        return new ResItemAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ResItemAdapter.ViewHolder holder, final int position) {

        CardView  cardView=holder.cardView;

        //get references
        TextView item_name=cardView.findViewById(R.id.item_name);
        TextView item_price=cardView.findViewById(R.id.item_price);
        TextView item_description=cardView.findViewById(R.id.item_description);

        //set values

        Item singleitem=itemsR.get(position);

        item_name.setText(singleitem.getName());

        item_description.setText(singleitem.getDescription());

        item_price.setText(Integer.toString(singleitem.getPrice()));

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
