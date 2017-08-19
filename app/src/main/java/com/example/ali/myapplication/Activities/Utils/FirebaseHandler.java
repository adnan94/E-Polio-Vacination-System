package com.example.ali.myapplication.Activities.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ADnan on 8/19/2017.
 */

public class FirebaseHandler {
    public static DatabaseReference databaseReference;


    public static DatabaseReference getDataBaseReference() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("AppData");
        databaseReference.keepSynced(true);
        return databaseReference;
    }
}
