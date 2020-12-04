package com.example.foodpandaclone.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import com.example.foodpandaclone.database.Repository;

//JobIntent Service
public class OrderTracker extends JobIntentService {

    private static final String TAG="OrderTracker";

    public static void enqueueWork(Context context, Intent work){
        enqueueWork(context,OrderTracker.class,123,work);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG,"onCreate OrderTracker");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG,"onHandleWork");

        String input=intent.getStringExtra("inputExtra");

        for(int i=0;i<10;i++){
            Log.d(TAG, input +" - "+ i );

            if(isStopped()) return;

            SystemClock.sleep(2000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "OnDestroy Order Tracker");
    }

    @Override
    public boolean onStopCurrentWork() {

        Log.d(TAG,"onStopCurrentWork");

     return super.onStopCurrentWork();
    }
}
