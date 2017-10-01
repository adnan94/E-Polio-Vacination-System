package com.example.ali.myapplication.Activities.Utils;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADnan on 8/19/2017.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate();


    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();



    }
}
