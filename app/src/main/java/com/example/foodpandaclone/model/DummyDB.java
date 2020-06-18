package com.example.foodpandaclone.model;

public class DummyDB {

    //dummy placeholder function
    public boolean isDatabaseAvailable(){

        int num=10;

        synchronized (this){
            if(num==10){
                try{
                    wait(6000);

                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            return true;
        }


    }
}
