package com.example.foodpandaclone.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpandaclone.R;
import com.example.foodpandaclone.model.RestaurantFirebase;
import com.example.foodpandaclone.view.activities.Restaurant_Activity;

import java.util.List;

public class DiscountResAdapter extends RecyclerView.Adapter<DiscountResAdapter.ViewHolder> {


private List<RestaurantFirebase> listOfRestaurants; public Listener listener;

    public interface Listener{
        void onClick(int position);
    }

    public void setListener(DiscountResAdapter.Listener listener){
        this.listener=listener;
    }

    public DiscountResAdapter(List<RestaurantFirebase> list){
        this.listOfRestaurants=list;
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

        final RestaurantFirebase res= listOfRestaurants.get(i);

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
        discount.setText(res.strDiscount());
        food_img.setImageResource(R.drawable.foodpanda_logo);

        Log.d("Number of Items", Integer.toString(res.getItems().size()));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), Restaurant_Activity.class);
                intent.putExtra("restaurant",res);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("Size in dresadapter",Integer.toString(listOfRestaurants.size()));
        return listOfRestaurants.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

    private CardView cardView;

        public ViewHolder(CardView c) {
            super(c);
            cardView=c;
        }
    }
}
