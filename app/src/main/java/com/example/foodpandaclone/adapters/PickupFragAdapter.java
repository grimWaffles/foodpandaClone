package com.example.foodpandaclone.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.Restaurant;

import java.util.List;

public class PickupFragAdapter extends RecyclerView.Adapter<PickupFragAdapter.ViewHolder> {


    private List<Restaurant> list; private String isDeliveryOrPickup; public Listener listener;

    public interface Listener{
        void onClick(int position);
    }

    public void setListener(Listener listener){
        this.listener=listener;
    }

    public PickupFragAdapter(List<Restaurant> list, String deliveryOrPickup){
        this.list=list;
        this.isDeliveryOrPickup=deliveryOrPickup;
    }


    @Override
    public PickupFragAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

       CardView cardView=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fd,parent,false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {

        CardView cardView=viewHolder.cardView;

        //getting references:

        ImageView food_img=cardView.findViewById(R.id.food_img);
        TextView deliveryOrPickup=cardView.findViewById(R.id.delivery_pickup);
        TextView shop_name=cardView.findViewById(R.id.shop_name);
        TextView shop_rating=cardView.findViewById(R.id.shop_rating);
        TextView numberOfReview=cardView.findViewById(R.id.numberOfReviews);
        TextView priceLevel=cardView.findViewById(R.id.price_level);
        TextView categories=cardView.findViewById(R.id.shop_categories);
        TextView delivery_cost=cardView.findViewById(R.id.delivery_cost);

        //setting the appropriate values:

        Restaurant res=list.get(i);

        if(isDeliveryOrPickup.equals("delivery")){
            if(res.getDeliveryCost()==0){
                deliveryOrPickup.setText("Free Delivery");
            }
            else{
                delivery_cost.setText(res.strDeliveryCost() + " Tk");
            }
        }
        else{
            deliveryOrPickup.setText("PICKUP");
        }


        if(res.getDeliveryCost()==0){
            delivery_cost.setText("Free Delivery");
        }
        else{
            delivery_cost.setText(res.strDeliveryCost() + " Tk");
        }

        StringBuilder stringBuilder=new StringBuilder();

        for(int j=0;j<res.getCategoriesOffered().size();j++){

            if(i!=res.getCategoriesOffered().size()){
                stringBuilder.append(res.getCategoriesOffered().get(j));
                stringBuilder.append(", ");
            }
            else{
                stringBuilder.append("more...");
            }
        }
        categories.setText(stringBuilder);

        String str2="(" + res.strRating() +")";
        numberOfReview.setText(str2);

        shop_name.setText(res.getName());
        shop_rating.setText(res.strRating());
        priceLevel.setText(res.getPriceLevel());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onClick(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView c) {
            super(c);
            cardView=c;
        }
    }
}
