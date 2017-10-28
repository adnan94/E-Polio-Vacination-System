package com.example.ali.myapplication.Activities.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ali.myapplication.Activities.ModelClasses.UserModel;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.Service;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    public UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //  mFirebaseAnalytics = FirebaseAnalytics.getInstance(SplashScreen.this);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    mAuthListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            final FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                // User is signed in
                                FirebaseHandler.getInstance().getUsersRef().child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot != null) {
                                            if (dataSnapshot.getValue() != null) {
                                                //      for(DataSnapshot data:dataSnapshot.getChildren()) {
                                                userModel = dataSnapshot.getValue(UserModel.class);
                                                UserModel.getInstance(
                                                        userModel.getName(),
                                                        userModel.getFname(),
                                                        userModel.getAddress(),
                                                        userModel.getEmail(),
                                                        userModel.getPassword(),
                                                        userModel.getCnic(),
                                                        userModel.getCellNo(),
                                                        userModel.getUser_type()
                                                );
                                                //     }
                                            }
                                            openMainScreen(userModel);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            } else {
                                // User is signed out
                                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                                //   overridePendingTransition(R.anim.slide_down,R.anim.slide_up);
                                startActivity(intent);
                                finish();
                            }
                        }
                    };
                    //add listener
                    mAuth.addAuthStateListener(mAuthListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };
        thread.start();
    }

    private void openMainScreen(UserModel userModel) {

        if (userModel.getUser_type() == 3) {
            //User Screen
            databaseReference.child("ActivitySeen").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(ServerValue.TIMESTAMP);
            startService(new Intent(SplashScreen.this, Service.class));
            startActivity(new Intent(SplashScreen.this, UserHome.class));
        } else if (userModel.getUser_type() == 1) {
            //Admin Screen
            databaseReference.child("ActivitySeen").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(ServerValue.TIMESTAMP);
            startService(new Intent(SplashScreen.this, Service.class));
            startActivity(new Intent(SplashScreen.this, AdminHome.class));
        } else if (userModel.getUser_type() == 2) {
            //Uc Screen
            databaseReference.child("ActivitySeen").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(ServerValue.TIMESTAMP);
            startService(new Intent(SplashScreen.this, Service.class));
            startActivity(new Intent(SplashScreen.this, UcHome.class));
        } else if (userModel.getUser_type() == 4) {
            startService(new Intent(SplashScreen.this, Service.class));
            databaseReference.child("ActivitySeen").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(ServerValue.TIMESTAMP);
        }


        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
