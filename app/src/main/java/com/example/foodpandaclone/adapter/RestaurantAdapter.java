package com.example.foodpandaclone.adapter;

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
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.view.activities.Restaurant_Activity;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Restaurant> listOfRestaurants; private int mode;

    public RestaurantAdapter(List<Restaurant> list, int mode){

        this.listOfRestaurants=list;
        this.mode=mode;
    }


    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        CardView cardView=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_discounted_restaurants,parent,false);

        return new RestaurantAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.ViewHolder viewHolder, final int i) {

        CardView cardView=viewHolder.cardView;

        //getting references:

        ImageView food_img=cardView.findViewById(R.id.food_img);
        TextView discount=cardView.findViewById(R.id.discount_amount);
        TextView deliveryStatus=cardView.findViewById(R.id.delivery_pickup);
        TextView shop_name=cardView.findViewById(R.id.shop_name);
        TextView shop_rating=cardView.findViewById(R.id.shop_rating);
        TextView numberOfReview=cardView.findViewById(R.id.numberOfReviews);
        TextView delivery_cost=cardView.findViewById(R.id.delivery_cost);
        TextView address=cardView.findViewById(R.id.address);

        //setting the appropriate values:

        final Restaurant res= listOfRestaurants.get(i);

         Log.d("Size of restaurantList in adapter",Integer.toString(listOfRestaurants.size()));

        if(mode==0 && res.getDiscount()!=0){
            discount.setText("Discount: "+Integer.toString(res.getDiscount()) +" Tk");
        }
        else if(mode==1 && res.getDiscount()==0){
            discount.setText("No discount");
        }

        if(res.getDeliveryCost()!=0){
            deliveryStatus.setText("Delivery Cost: "+Integer.toString(res.getDeliveryCost())+" Tk");
            delivery_cost.setText(Integer.toString(res.getDeliveryCost())+" Tk");
        }
        else{
            deliveryStatus.setText("Free Delivery");
            delivery_cost.setText("Free Delivery");
        }

        shop_name.setText(res.getResName());
        numberOfReview.setText("("+ Integer.toString(res.getReview())+")");
        shop_rating.setText(String.valueOf(res.getRating()));
        address.setText(res.getLocation());

        if(res.getResName().equals("Shumi's Hot Cake")){
            food_img.setImageResource(R.drawable.hotcakepic);
        }
        else {
            food_img.setImageResource(R.drawable.biryanipic);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), Restaurant_Activity.class);
                intent.putExtra("restaurant",res.getResID());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        if(listOfRestaurants==null){
            Log.d("Size in RestaurantAdapter","Adapter empty");
            return 0;
        }
        else{

            Log.d("Size in RestaurantAdapter",Integer.toString(listOfRestaurants.size()));
            return listOfRestaurants.size();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

    private CardView cardView;

        public ViewHolder(CardView c) {
            super(c);
            cardView=c;
        }
    }
}
