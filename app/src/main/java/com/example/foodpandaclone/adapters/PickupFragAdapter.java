package com.example.foodpandaclone.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.foodpandaclone.R;

public class PickupFragAdapter extends RecyclerView.Adapter<PickupFragAdapter.ViewHolder> {


    private String[] list;

    public PickupFragAdapter(String[] list){
        this.list=list;
    }


    @Override
    public PickupFragAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

       CardView cardView=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fp,parent,false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        CardView cardView=viewHolder.cardView;

        /// TODO: 27-Jun-20

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(@NonNull CardView c) {
            super(c);
            cardView=c;
        }
    }
}
