package com.example.ali.myapplication.Activities.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ali.myapplication.Activities.Activity.UcHome;
import com.example.ali.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * Created by AdnanAhmed on 10/28/2017.
 */

public class Service extends android.app.Service {
    String id;
    DatabaseReference firebase;
    int childChangeCount = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        firebase = FirebaseDatabase.getInstance().getReference();
        if (FirebaseAuth.getInstance().getCurrentUser().getUid() != null) {
            id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        if (FirebaseAuth.getInstance().getCurrentUser().getUid() != null) {
            getTokenNotifications();
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void getTokenNotifications() {
        if (FirebaseAuth.getInstance().getCurrentUser().getUid() != null) {
            id = FirebaseAuth.getInstance().getCurrentUser().getUid();

            firebase.child("form_tokens").child(id).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getValue() != null) {
                        Log.d("TAG", dataSnapshot.getValue().toString());
                        long tokenDate = 0;
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            tokenDate = Long.parseLong(d.child("token_date").getValue().toString());
                        }

                        final long finalTokenDate = tokenDate;
                        firebase.child("ActivitySeen").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    if (childChangeCount == 0) {
                                        if (Long.parseLong(dataSnapshot.getValue().toString()) < finalTokenDate) {
                                            Log.d("TAG", dataSnapshot.getValue().toString());
                                            Intent intent = new Intent(getApplicationContext(), UcHome.class);
                                            NotificationManager notificationManager =
                                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                            Notification notification = null;
                                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                                notification = new Notification.Builder(getApplicationContext())
                                                        .setTicker("E-Polio")
                                                        .setContentTitle("E-Polio")
                                                        .setStyle(new Notification.BigTextStyle().bigText(""))
                                                        .setContentText("You have notification")
                                                        .setTicker("E-Polio")
                                                        .setPriority(Notification.PRIORITY_HIGH)
                                                        .setSmallIcon(R.mipmap.nadra)
                                                        .setAutoCancel(true)
                                                        .setContentIntent(pendingIntent)
                                                        .setVibrate(new long[]{500, 500})
                                                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                                        .build();
                                            }
                                            Random r = new Random();
                                            int i = r.nextInt(80 - 65) + 65;
//                                    int i = 1221;
                                            notificationManager.notify(++i, notification);
                                            if (id != null) {
                                                firebase.child("ActivitySeen").child(id).setValue(ServerValue.TIMESTAMP, new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                Utils.log("completed");
                                                    }
                                                });

                                            }


                                        }
                                        ++childChangeCount;
                                    } else {
                                        childChangeCount = 0;
                                    }


                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot.getValue() != null) {
                        Log.d("TAG", dataSnapshot.getValue().toString());
                        long tokenDate = 0;
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            tokenDate = Long.parseLong(d.child("token_date").getValue().toString());
                        }

                        final long finalTokenDate = tokenDate;
                        firebase.child("ActivitySeen").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {

                                    if (Long.parseLong(dataSnapshot.getValue().toString()) < finalTokenDate) {
                                        Log.d("TAG", dataSnapshot.getValue().toString());
                                        Intent intent = new Intent(getApplicationContext(), UcHome.class);
                                        NotificationManager notificationManager =
                                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                        Notification notification = null;
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                            notification = new Notification.Builder(getApplicationContext())
                                                    .setTicker("E-Polio")
                                                    .setContentTitle("E-Polio")
                                                    .setStyle(new Notification.BigTextStyle().bigText(""))
                                                    .setContentText("You have notification")
                                                    .setTicker("E-Polio")
                                                    .setPriority(Notification.PRIORITY_HIGH)
                                                    .setSmallIcon(R.mipmap.nadra)
                                                    .setAutoCancel(true)
                                                    .setContentIntent(pendingIntent)
                                                    .setVibrate(new long[]{500, 500})
                                                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                                    .build();
                                        }
                                        Random r = new Random();
                                        int i = r.nextInt(80 - 65) + 65;
//                                    int i = 1221;
                                        notificationManager.notify(++i, notification);
                                        if (id != null) {
                                            firebase.child("ActivitySeen").child(id).setValue(ServerValue.TIMESTAMP, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                Utils.log("completed");
                                                }
                                            });

                                        }


                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
