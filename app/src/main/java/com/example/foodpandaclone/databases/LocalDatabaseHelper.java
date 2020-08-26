package com.example.foodpandaclone.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocalDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_name="LocalDatabaseHelper"; private static int DB_version=1;

   public LocalDatabaseHelper(Context context){
       super(context,DB_name,null,DB_version);


   }

    @Override
    public void onCreate(SQLiteDatabase db) {

       String sqlCurrentOrder="CREATE TABLE CURRENT_ORDER(_id INTEGER PRIMARY KEY,NAME TEXT,DESCRIPTION TEXT, RES_ID INTEGER,  CONSTRAINT );";

       String sqlRestaurant="CREATE TABLE RESTAURANT(_id INTEGER PRIMARY KEY,);";


       db.execSQL(sqlRestaurant);
       db.execSQL(sqlCurrentOrder);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
