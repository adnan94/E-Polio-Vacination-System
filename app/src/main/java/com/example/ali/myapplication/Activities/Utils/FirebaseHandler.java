package com.example.ali.myapplication.Activities.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by ADnan on 8/19/2017.
 */

public class FirebaseHandler {
    public static DatabaseReference databaseReference;

    private DatabaseReference firebaseRef;
    private FirebaseAuth authData;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mfirebaseStorage;
    private static FirebaseHandler ourInstance;
    private DatabaseReference usersRef;
    private DatabaseReference add_forms;

    public static DatabaseReference getDataBaseReference() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("AppData");
        databaseReference.keepSynced(true);
        return databaseReference;
    }

    public static FirebaseHandler getInstance() {
        if (ourInstance == null) {
            AppLogs.d("rootRef", "" + FirebaseDatabase.getInstance().getReference());
            ourInstance = new FirebaseHandler();
        }
        return ourInstance;
    }

    private FirebaseHandler() {
        firebaseRef = FirebaseDatabase.getInstance().getReference();
        mfirebaseStorage = FirebaseStorage.getInstance().getReferenceFromUrl(String.valueOf(FirebaseStorage.getInstance().getReference()));
        initializeChildRefs();
    }

    //  DatabaseStorage
    public StorageReference getRootStorageRef() {
        return mfirebaseStorage;
    }

    //    DatabaseReference
    public DatabaseReference getRootFirebaseRef() {
        return firebaseRef;
    }

    private void initializeChildRefs() {
        usersRef = firebaseRef.child("users");
        add_forms = firebaseRef.child("user_forms");
    }
    public DatabaseReference getUsersRef() {
        return usersRef;
    }

    public DatabaseReference getAdd_forms() {
        return add_forms;
    }
}
