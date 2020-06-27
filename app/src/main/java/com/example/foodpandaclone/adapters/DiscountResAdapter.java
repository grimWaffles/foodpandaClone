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

public class DiscountResAdapter extends RecyclerView.Adapter<DiscountResAdapter.ViewHolder> {


private List<Restaurant> list; public Listener listener;

    public interface Listener{
        void onClick(int position);
    }

    public void setListener(DiscountResAdapter.Listener listener){
        this.listener=listener;
    }

    public DiscountResAdapter(List<Restaurant> list){
        this.list=list;
    }


    @Override
    public DiscountResAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        CardView cardView=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dfd,parent,false);

        return new DiscountResAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(DiscountResAdapter.ViewHolder viewHolder, final int i) {

        CardView cardView=viewHolder.cardView;

        //getting references:

        ImageView food_img=cardView.findViewById(R.id.food_img);
        TextView discount=cardView.findViewById(R.id.discount_amount);
        TextView deliveryOrPickup=cardView.findViewById(R.id.delivery_pickup);
        TextView shop_name=cardView.findViewById(R.id.shop_name);
        TextView shop_rating=cardView.findViewById(R.id.shop_rating);
        TextView numberOfReview=cardView.findViewById(R.id.numberOfReviews);
        TextView priceLevel=cardView.findViewById(R.id.price_level);
        TextView categories=cardView.findViewById(R.id.shop_categories);
        TextView delivery_cost=cardView.findViewById(R.id.delivery_cost);

        //setting the appropriate values:

        Restaurant res=list.get(i);

        if(res.getDeliveryCost()==0){
            deliveryOrPickup.setText("Free Delivery");
        }
        else{
            delivery_cost.setText(res.strDeliveryCost() + " Tk");
        }


        if(res.getDeliveryCost()==0){
            delivery_cost.setText("Free Delivery");
        }
        else{
            delivery_cost.setText(res.strDeliveryCost() + " Tk");
        }

        StringBuilder stringBuilder=new StringBuilder();

        for(int j=0;j<=3;j++){

            if(i!=3){
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
        discount.setText(res.strDiscount());

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
