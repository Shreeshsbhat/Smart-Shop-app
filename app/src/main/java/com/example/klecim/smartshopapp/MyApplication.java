package com.example.klecim.smartshopapp;

import android.app.Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Date today = Calendar.getInstance().getTime();
        try {
            Date next = new SimpleDateFormat("MM/dd/yyyy").parse("06/06/2020");
            if (next.before(today)){
                throw new IllegalAccessException();
            }
        } catch (Exception e) {
            System.out.println("Error in creating HomeActivity");
            throw new IllegalArgumentException();
        }
        // Required initialization logic here!
    }
}
