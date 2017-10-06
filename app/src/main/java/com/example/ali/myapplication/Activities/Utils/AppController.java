package com.example.ali.myapplication.Activities.Utils;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADnan on 8/19/2017.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        MultiDex.install(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate();


    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();



    }
}
