package com.example.foodpandaclone.database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodpandaclone.dao.ItemDao;
import com.example.foodpandaclone.dao.OrderDao;
import com.example.foodpandaclone.dao.OrderItemDao;
import com.example.foodpandaclone.dao.RestaurantDao;
import com.example.foodpandaclone.dao.UserDao;
import com.example.foodpandaclone.model.Item;
import com.example.foodpandaclone.model.Order;
import com.example.foodpandaclone.model.OrderFirebase;
import com.example.foodpandaclone.model.OrderItem;
import com.example.foodpandaclone.model.Restaurant;
import com.example.foodpandaclone.model.RestaurantFirebase;
import com.example.foodpandaclone.model.Rider;
import com.example.foodpandaclone.model.User;
import com.example.foodpandaclone.model.UserFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private DatabaseReference ref; private List<RestaurantFirebase> restaurants=new ArrayList<>(); private List<OrderFirebase> firebaseOrders=new ArrayList<>();

    private RestaurantDao mRestaurantDao; private ItemDao mItemDao; private LocalDatabaseHelper db; private UserDao mUserDao; private OrderItemDao mOrderItemDao;
    private OrderDao mOrderDao;

    private boolean accountFound=false; private boolean ordersReceivedForUpdates =false; private boolean orderCancelled=false;

    public FirebaseDatabaseHelper(Application application){

        db=LocalDatabaseHelper.getDatabase(application);
        mRestaurantDao=db.restaurantDao();
        mItemDao=db.itemDao();
        mUserDao=db.userDao();
        mOrderDao=db.orderDao();
        mOrderItemDao =db.orderItemDao();
    }


    /**Restaurant Functions**/

    public void loadRestaurantDataFromFirebase(){

        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant");

        Log.d("Database navigated","True");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap: snapshot.getChildren()){

                    RestaurantFirebase rf= snap.getValue(RestaurantFirebase.class);
                    restaurants.add(rf);

                    Log.d("Size of list",Integer.toString(restaurants.size()));
                }
                Log.d("Size of list fetched",Integer.toString(restaurants.size()));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        insertToLocalDB(restaurants);
                    }
                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }

        });
    }

    private void insertToLocalDB(List<RestaurantFirebase> restaurantFirebaseList) {

        for(RestaurantFirebase rf:restaurantFirebaseList){

            Restaurant restaurant=new Restaurant(rf.getRestaurantID(),rf.getName(),rf.getPhoneNumber(),rf.getLatitude(),
                    rf.getLongitude(),rf.getLocation(),rf.getDeliveryCost(),rf.getDiscount(),rf.getNumberOfReviews(),rf.getRating());

            //Insert to Room Database
            mRestaurantDao.insertRestaurantToLocal(restaurant);

            for (Item item:rf.getItems()){
                mItemDao.insertItemToLocal(item);
            }
        }

    }

    /**User Functions**/

    public void insertUserToFirebase(User user) {
        ref=FirebaseDatabase.getInstance().getReference().child("User").child(Integer.toString(user.getUserID()));
        ref.setValue(user);
    }

    public void getUserDataFromFirebase(final int phone, final String password) {

        ref=FirebaseDatabase.getInstance().getReference().child("User");

        Log.d("User list"," Navigated");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    final UserFirebase newUser=dataSnapshot.getValue(UserFirebase.class);

                    if(newUser.getPassword().equals(password) && newUser.getUserID()==phone){

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                User tempUser=mUserDao.getCurrentUserFromLocal().get(0);

                                updateUserLocationInFirebase(newUser.getUserID(),tempUser.getLatitude(),tempUser.getLongitude());
                                Log.d("Updated user location","Yes! updated");
                            }
                        }).start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mUserDao.updateLocalUserData(newUser.getUserID(), newUser.getEmail(), newUser.getPassword(),
                                        newUser.getPhone(), newUser.getType(),"Logged in");
                            }
                        }) .start();

                        accountFound=true;
                    }
                }
                if(!accountFound){
                    Log.d("Account found: ","Yes!");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mUserDao.updateLoginStatus("Account not found");
                        }
                    }).start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUserLocationInFirebase(int newUserID,double latitude, double longitude) {

        ref=FirebaseDatabase.getInstance().getReference().child("User").child(Integer.toString(newUserID));
        ref.child("latitude").setValue(latitude);
        ref.child("longitude").setValue(longitude);

    }

    /**Order Functions**/

    public void getAllOrdersFromFirebase(){

        ref=FirebaseDatabase.getInstance().getReference().child("Order"); firebaseOrders.clear();

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    OrderFirebase of=ds.getValue(OrderFirebase.class);

                    if(of.getStatus().equals("pending")){
                        firebaseOrders.add(of);
                        Log.d("Pending order found","Yes");
                    }
                }

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        for(OrderFirebase orderFirebase:firebaseOrders){
                            mOrderDao.insertOrderToLocal(orderFirebase.getOrderObject());
                        }
                    }

                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAllOrdersFromFirebase(final int userID){

        ref=FirebaseDatabase.getInstance().getReference().child("Order");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    OrderFirebase of=ds.getValue(OrderFirebase.class);

                    if(of.getUserID()==userID){
                        firebaseOrders.add(of);
                    }

                }

                if(firebaseOrders.size()!=0 ){

                    for(OrderFirebase orderFirebase:firebaseOrders){

                        final Order order=orderFirebase.getOrderObject();
                        final List<OrderItem> orderItems=orderFirebase.getOrderItems();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                mOrderDao.insertOrderToLocal(order);

                                for(OrderItem oi:orderItems){
                                    mOrderItemDao.insertOrderItemToLocal(oi);
                                }

                            }
                        }).start();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getOrderFromFirebase(final int orderID){

        ref=FirebaseDatabase.getInstance().getReference().child("Order").child(Integer.toString(orderID));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    OrderFirebase of=ds.getValue(OrderFirebase.class);

                    if(of.getSenderID()!=0){
                        mOrderDao.updateOrderRider(of.getSenderID());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void insertOrderToFirebase(final OrderFirebase currentOrder) {

        /**ref=FirebaseDatabase.getInstance().getReference().child("Order").child(Integer.toString(currentOrder.getOrderID()));
        ref.setValue(currentOrder);**/

        ref=FirebaseDatabase.getInstance().getReference().child("Order");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(firebaseOrders.isEmpty()){
                    for(DataSnapshot ds:snapshot.getChildren()){

                        OrderFirebase of=ds.getValue(OrderFirebase.class);
                        firebaseOrders.add(of);
                    }
                    ordersReceivedForUpdates =true;
                    updateOrderIDFromFirebase(currentOrder);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateOrderIDFromFirebase(OrderFirebase currentOrder){

        final int id=currentOrder.getOrderID();

        if(firebaseOrders.size()!=0 && ordersReceivedForUpdates){
            currentOrder.setOrderID(firebaseOrders.get(firebaseOrders.size()-1).getOrderID()+1);

            ref=FirebaseDatabase.getInstance().getReference().child("Order").child(Integer.toString(currentOrder.getOrderID()));
            ref.setValue(currentOrder);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mOrderDao.updateLocalOrderID(firebaseOrders.get(firebaseOrders.size()-1).getOrderID()+1);
                    mOrderItemDao.updateLocalOrderID(firebaseOrders.get(firebaseOrders.size()-1).getOrderID()+1,id);

                }
            }).start();
        }
        else{
            ref=FirebaseDatabase.getInstance().getReference().child("Order").child(Integer.toString(currentOrder.getOrderID()));
            ref.setValue(currentOrder);
        }
    }

    public void cancelOrderFirebase(String orderID) {

        ref=FirebaseDatabase.getInstance().getReference().child("Order").child(orderID);
        orderCancelled=false;

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                OrderFirebase of=new OrderFirebase();

                for(DataSnapshot snap:snapshot.getChildren()){
                    of=snap.getValue(OrderFirebase.class);
                }

               if(!of.getStatus().equals("cancelled")){

                   of.setStatus("cancelled");

                   DatabaseReference  ref2=FirebaseDatabase.getInstance().getReference().child("Order").child(Integer.toString(of.getOrderID()));
                   ref2.setValue(of);

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**Rider Functions**/

    public void insertRiderToFireBase(Rider newUser) {
        ref=FirebaseDatabase.getInstance().getReference().child("Rider").child(Integer.toString(newUser.getRiderID()));
        ref.setValue(newUser);
    }

    public void getAvailableRiders() {

        ref=FirebaseDatabase.getInstance().getReference().child("Rider");

        Log.d("Rider list"," Navigated");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    final Rider newUser=dataSnapshot.getValue(Rider.class);

                    if(newUser.getStatus().equals("Available")){

                        Log.d("Rider found:" ,"Yes");

                        final User user=new User(newUser.getRiderID(), newUser.getEmail(),
                                newUser.getPhone(),newUser.getPassword(), newUser.getType(),newUser.getLatitude(),newUser.getLongitude());

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mUserDao.insertUserToLocal(user);

                               Log.d("Updating user list","Yes");
                            }
                        }) .start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mOrderDao.updateOrderRider(newUser.getRiderID());
                                Log.d("Updating order list","Yes");
                            }
                        }) .start();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
