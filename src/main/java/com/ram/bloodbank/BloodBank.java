package com.ram.bloodbank;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by RJGUP on 18-12-2017.
 */

public class BloodBank extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
