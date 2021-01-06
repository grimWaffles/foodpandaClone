package com.example.foodpandaclone.database;

import android.app.Application;
import android.util.Log;
import android.widget.TableLayout;

import androidx.annotation.NonNull;

import com.example.foodpandaclone.dao.ItemDao;
import com.example.foodpandaclone.dao.OrderDao;
import com.example.foodpandaclone.dao.OrderItemDao;
import com.example.foodpandaclone.dao.RestaurantDao;
import com.example.foodpandaclone.dao.RiderDao;
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
import java.util.concurrent.ExecutorService;

public class FirebaseDatabaseHelper {

    private static final String TAG="FirebaseDatabaseHelper";

    private DatabaseReference ref;

    //Dao
    private RestaurantDao mRestaurantDao; private ItemDao mItemDao; private LocalDatabaseHelper db;
    private UserDao mUserDao; private OrderItemDao mOrderItemDao; private OrderDao mOrderDao; private RiderDao mRiderDao;

    //Variables
    private List<RestaurantFirebase> restaurants=new ArrayList<>(); private List<OrderFirebase> firebaseOrders=new ArrayList<>();
    private boolean accountFound=false; private boolean ordersReceivedForUpdates =false; private boolean orderCancelled=false;
    private boolean riderFound=false;

    //Executor
    private ExecutorService executorService;

    public FirebaseDatabaseHelper(Application application){

        db=LocalDatabaseHelper.getDatabase(application);
        mRestaurantDao=db.restaurantDao();
        mItemDao=db.itemDao();
        mUserDao=db.userDao();
        mOrderDao=db.orderDao();
        mOrderItemDao =db.orderItemDao();
        mRiderDao=db.riderDao();
        executorService=LocalDatabaseHelper.databaseWriterExecutor;

    }

    /**Restaurant Functions**/

    public void downloadSpecificRestaurantData(final List<Integer> restaurants) {

        final List<RestaurantFirebase> rfList=new ArrayList<>();

        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap:snapshot.getChildren()){
                    RestaurantFirebase rf=snap.getValue(RestaurantFirebase.class);

                    if(restaurantsInList(restaurants,rf.getRestaurantID())){
                        rfList.add(rf);
                    }
                }

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        for(RestaurantFirebase rf:rfList){
                            mRestaurantDao.insertRestaurantToLocal(new Restaurant(rf.getRestaurantID(),rf.getName(),rf.getPhoneNumber(),rf.getLatitude(),
                                    rf.getLongitude(),rf.getLocation(),rf.getDeliveryCost(),rf.getDiscount(),rf.getNumberOfReviews(),rf.getRating()));
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean restaurantsInList(List<Integer> restaurants, int restaurantID) {

        boolean doesExist=false;

        for (Integer i:restaurants){
            if(i==restaurantID){
                doesExist=true;
            }
        }

        return doesExist;
    }

    public void loadRestaurantDataFromFirebase(){

        ref=FirebaseDatabase.getInstance().getReference().child("Restaurant");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap: snapshot.getChildren()){

                    RestaurantFirebase rf= snap.getValue(RestaurantFirebase.class);
                    restaurants.add(rf);
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        insertToLocalDB(restaurants);
                    }
                }).start();

                ref.removeEventListener(this);
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

                                if(newUser.getType().equals("Rider")){
                                    updateRiderLocationInFirebase(newUser.getUserID(),tempUser.getLatitude(),tempUser.getLongitude());
                                }

                                updateUserLocationInFirebase(newUser.getUserID(),tempUser.getLatitude(),tempUser.getLongitude());

                                mUserDao.updateLocalUserData(newUser.getUserID(), newUser.getEmail(), newUser.getPassword(),
                                        newUser.getPhone(), newUser.getType(),"Logged in");

                                Log.d("Updated user location","Yes! updated");
                            }
                        }).start();

                        if(newUser.getType().equals("Rider")){
                            setRiderStatus(newUser.getUserID(),"Available");
                        }

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

                ref.removeEventListener(this);
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

    public void updateRiderLocationInFirebase(int newUserID,double latitude, double longitude) {

        ref=FirebaseDatabase.getInstance().getReference().child("Rider").child(Integer.toString(newUserID));
        ref.child("latitude").setValue(latitude);
        ref.child("longitude").setValue(longitude);

    }

    public void downloadUserInformationFromFirebase(final int userID){

        ref=FirebaseDatabase.getInstance().getReference().child("User");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap:snapshot.getChildren()){
                    final User user=snap.getValue(User.class);

                    if(user.getUserID()==userID){

                        user.setLogin_status("customer");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mUserDao.insertUserToLocal(user);
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

    /**Order Functions**/

    public void getAllPendingOrdersFromFirebase(){

        firebaseOrders.clear();

        ref=FirebaseDatabase.getInstance().getReference().child("Order");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    OrderFirebase of=ds.getValue(OrderFirebase.class);

                    firebaseOrders.add(of);
                }

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        for(OrderFirebase orderFirebase:firebaseOrders){
                            mOrderDao.insertOrderToLocal(orderFirebase.getOrderObject());
                        }

                    }

                }).start();

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getRidersCurrentOrder(final int riderID){

        firebaseOrders.clear();

        ref=FirebaseDatabase.getInstance().getReference().child("Order");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    OrderFirebase of=ds.getValue(OrderFirebase.class);

                    if(!of.getStatus().equals("completed") && of.getSenderID()==riderID ){
                        firebaseOrders.add(of);
                    }
                }

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG,"Writing order to database");

                        for(OrderFirebase orderFirebase:firebaseOrders){
                            mOrderDao.insertOrderToLocal(orderFirebase.getOrderObject());

                            for(OrderItem item: orderFirebase.getOrderItems()){
                                mOrderItemDao.insertOrderItemToLocal(item);
                            }
                        }
                    }
                });

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getAllOrdersOfUser(final int userID){

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

                if(firebaseOrders.size()!=0 && firebaseOrders!=null){

                    Log.d(TAG,Integer.toString(firebaseOrders.size()));

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            for(OrderFirebase orderFirebase:firebaseOrders){

                                Log.d(TAG,"Inside forLoop");

                                final Order order=orderFirebase.getOrderObject();
                                final List<OrderItem> orderItems=orderFirebase.getOrderItems();

                                Log.d(TAG,"Created seperate objects");

                                mOrderDao.insertOrderToLocal(order);

                                Log.d(TAG,"Order locally updated");

                                for(OrderItem oi:orderItems){
                                    mOrderItemDao.insertOrderItemToLocal(oi);
                                }
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getOrderFromFirebase(final int orderID){

        Thread newThread=new Thread(){
            @Override
            public void run() {
                super.run();

                ref=FirebaseDatabase.getInstance().getReference().child("Order").child(Integer.toString(orderID));

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        OrderFirebase of;

                        for(DataSnapshot ds:snapshot.getChildren()){

                            of=ds.getValue(OrderFirebase.class);

                            if(of.getSenderID()!=0){
                                mOrderDao.updateOrderRider(of.getSenderID());
                            }
                        }

                        ref.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        };

        newThread.start();
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
        ref.child("status").setValue("cancelled");
    }

    public void updateSenderIDFirebase(int riderID, int orderID) {
        ref=FirebaseDatabase.getInstance().getReference().child("Order").child(Integer.toString(orderID)).child("senderID");
        ref.setValue(riderID);
    }

    private void setOrderStatus(int id,String status_message) {
        ref=FirebaseDatabase.getInstance().getReference().child("Order").child(Integer.toString(id)).child("status");
        ref.setValue(status_message);
    }

    /**Rider Functions**/

    public void insertRiderToFireBase(Rider newUser) {
        ref=FirebaseDatabase.getInstance().getReference().child("Rider").child(Integer.toString(newUser.getRiderID()));
        ref.setValue(newUser);
    }

    public void getAvailableRiders(final Order order) {

        ref=FirebaseDatabase.getInstance().getReference().child("Rider");
        riderFound=false;

        Log.d(TAG," Navigated");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    final Rider rider=dataSnapshot.getValue(Rider.class);

                    if(rider.getStatus().equals("Available")){

                        riderFound=true;

                        setRiderStatus(rider.getRiderID(),"Busy");
                        setOrderStatus(order.getOrderID(),"Rider Found");
                        Log.d(TAG,"Available rider found!");

                        updateSenderIDFirebase(rider.getRiderID(),order.getOrderID());
                        Log.d(TAG,"Order information updated in firebase");

                        Log.d(TAG,"Updating local cache!");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                rider.setStatus("Busy");
                                mRiderDao.insertUserToLocal(rider);
                                mOrderDao.updateOrderRider(rider.getRiderID());
                            }
                        }).start();
                    }
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mOrderDao.updateOrderMessage(order.getOrderID(),"Riders not found");
                    }
                }).start();

                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void setRiderStatus(int userID,String status) {
        ref=FirebaseDatabase.getInstance().getReference().child("Rider").child(Integer.toString(userID)).child("status");
        ref.setValue(status);
    }

    public void signOutRider(int userID) {
        ref=FirebaseDatabase.getInstance().getReference().child("Rider").child(Integer.toString(userID)).child("status");
        ref.setValue("Unavailable");
    }

    public void downloadRiderInfo(final int riderID) {
        ref=FirebaseDatabase.getInstance().getReference().child("Rider");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap:snapshot.getChildren()){

                    final Rider rider=snap.getValue(Rider.class);

                    if(rider.getRiderID()==riderID){

                        Log.d(TAG,"Rider Found");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG,"Writing to DB");
                                mRiderDao.insertUserToLocal(rider);
                            }
                        }).start();
                    }
                }
                ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
