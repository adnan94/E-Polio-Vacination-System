package com.example.ali.myapplication.Activities.Admin_Ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.ModelClasses.Team_MemberObject;
import com.example.ali.myapplication.Activities.ModelClasses.Tracking;
import com.example.ali.myapplication.Activities.User_Ui.Add_form;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.SharedPref_Team;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

/**
 * Created by Sami Khan on 12/2/2017.
 */

public class Monitoring_Fragment extends android.support.v4.app.Fragment implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraChangeListener {
    SupportMapFragment mSupportMapFragment;
    GoogleMap map;
    LatLng source;
    View view;
    TextView textView = null;
    ArrayList<Tracking> listTracking;
    int childChangeCount = 0;
    DatabaseReference refTracking;
    DatabaseReference refStatus;
    ChildEventListener childEventListenerStatus, childEventListenerTracking;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.monitoring_map, container, false);
        } catch (InflateException e) {
        }
        refTracking = FirebaseDatabase.getInstance().getReference().child("TeamTracking");
        refStatus = FirebaseDatabase.getInstance().getReference().child("polio_teams");

        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.root, mSupportMapFragment).commit();
        }
        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(this);
        }
        listTracking = new ArrayList<>();

        getTeamsTracking();
        trackPolioTeamsStatus();
        return view;
    }

    private void trackPolioTeamsStatus() {
        refStatus.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                childEventListenerStatus = this;

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                childEventListenerStatus = this;
                if (dataSnapshot.getValue() != null) {
                    if (childChangeCount == 0) {
                        ++childChangeCount;

                        for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Log.d("Adnan", "if" + dataSnapshot1.getValue().toString());
                            final Team_MemberObject team_memberObject = dataSnapshot1.getValue(Team_MemberObject.class);
                            if (listTracking != null) {
                                if (listTracking.size() > 0) {
                                    boolean flag = true;
                                    for (int i = 0; i < listTracking.size(); i++) {
                                        if (team_memberObject.getMember_uid().equals(listTracking.get(i).getId())) {
                                            if (team_memberObject.status.equals("NotActive")) {
                                                listTracking.get(i).getMarker().remove();
                                                listTracking.remove(i);
                                                Log.d("Adnan", "Removed" + team_memberObject.getMember_name());
                                                flag = false;
                                            }
                                        }
                                    }
                                    if (flag) {
                                        if (team_memberObject.getStatus().equals("Active")) {
                                            FirebaseDatabase.getInstance().getReference().child("TeamTracking").child(team_memberObject.getMember_uid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.getValue() != null) {
                                                        final double lat = Double.valueOf(dataSnapshot.child("lat").getValue().toString());
                                                        final double lng = Double.valueOf(dataSnapshot.child("lng").getValue().toString());
                                                        final String teamId = dataSnapshot.child("teamId").getValue().toString();
                                                        final String ucId = dataSnapshot.child("uc_id").getValue().toString();
                                                        final String key = dataSnapshot.getKey();
                                                        FirebaseDatabase.getInstance().getReference().child("uc_teams").child(ucId).child(teamId).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.getValue() != null) {
                                                                    boolean flag = true;

                                                                    if (dataSnapshot.child("team_status").getValue().toString().equals("Activated")) {
                                                                        for (int i = 0; i < listTracking.size(); i++) {
                                                                            if (team_memberObject.getMember_uid().equals(listTracking.get(i).getId())) {
                                                                                flag = false;
                                                                            }
                                                                        }
                                                                        if (flag) {
                                                                            listTracking.add(new Tracking(lat, lng, key, team_memberObject.member_name, map.addMarker(new MarkerOptions()
                                                                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.tour_icon_marker))
                                                                                    .title(team_memberObject.getMember_name())
                                                                                    .position(new LatLng(lat, lng)))));
                                                                            Log.d("Adnan", "Added" + team_memberObject.getMember_name());

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
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }

                                    }


                                }
                            }
                        }

                    } else {
                        childChangeCount = 0;
                        Log.d("Adnan", "else");

                        for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Log.d("Adnan", "if" + dataSnapshot1.getValue().toString());
                            final Team_MemberObject team_memberObject = dataSnapshot1.getValue(Team_MemberObject.class);
                            if (listTracking != null) {
                                if (listTracking.size() > 0) {
                                    boolean flag = true;
                                    for (int i = 0; i < listTracking.size(); i++) {
                                        if (team_memberObject.getMember_uid().equals(listTracking.get(i).getId())) {
                                            if (team_memberObject.status.equals("NotActive")) {
                                                listTracking.get(i).getMarker().remove();
                                                listTracking.remove(i);
                                                Log.d("Adnan", "Removed" + team_memberObject.getMember_name());
                                                flag = false;
                                            }
                                        }
                                    }
                                    if (flag) {
                                        if (team_memberObject.getStatus().equals("Active")) {
                                            FirebaseDatabase.getInstance().getReference().child("TeamTracking").child(team_memberObject.getMember_uid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.getValue() != null) {
                                                        final double lat = Double.valueOf(dataSnapshot.child("lat").getValue().toString());
                                                        final double lng = Double.valueOf(dataSnapshot.child("lng").getValue().toString());
                                                        final String teamId = dataSnapshot.child("teamId").getValue().toString();
                                                        final String ucId = dataSnapshot.child("uc_id").getValue().toString();
                                                        final String key = dataSnapshot.getKey();
                                                        FirebaseDatabase.getInstance().getReference().child("uc_teams").child(ucId).child(teamId).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.getValue() != null) {
                                                                    boolean flag = true;

                                                                    if (dataSnapshot.child("team_status").getValue().toString().equals("Activated")) {
                                                                        for (int i = 0; i < listTracking.size(); i++) {
                                                                            if (team_memberObject.getMember_uid().equals(listTracking.get(i).getId())) {
                                                                                flag = false;
                                                                            }
                                                                        }
                                                                        if (flag) {
                                                                            listTracking.add(new Tracking(lat, lng, key, team_memberObject.member_name, map.addMarker(new MarkerOptions()
                                                                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.tour_icon_marker))
                                                                                    .title(team_memberObject.getMember_name())
                                                                                    .position(new LatLng(lat, lng)))));
                                                                            Log.d("Adnan", "Added" + team_memberObject.getMember_name());

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
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }

                                    }


                                }
                            }
                        }

                    }


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

    @Override
    public void onDestroy() {
        if (childEventListenerStatus != null) {
            refStatus.removeEventListener(childEventListenerStatus);
        }
        if (childEventListenerTracking != null) {
            refTracking.removeEventListener(childEventListenerTracking);
        }
        super.onDestroy();
    }

    private void getTeamsStatusTracking() {
    }

    private void getTeamsTracking() {
        refTracking.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                childEventListenerTracking = this;

                if (dataSnapshot.getValue() != null) {

                    final double lat = Double.valueOf(dataSnapshot.child("lat").getValue().toString());
                    final double lng = Double.valueOf(dataSnapshot.child("lng").getValue().toString());
                    final String teamId = dataSnapshot.child("teamId").getValue().toString();
                    final String ucId = dataSnapshot.child("uc_id").getValue().toString();
                    final String key = dataSnapshot.getKey();

                    FirebaseDatabase.getInstance().getReference().child("polio_teams").child(teamId).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                final Team_MemberObject team_memberObject = dataSnapshot.getValue(Team_MemberObject.class);
                                if (team_memberObject.getStatus().equals("Active")) {
                                    FirebaseDatabase.getInstance().getReference().child("uc_teams").child(ucId).child(teamId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.getValue() != null) {
                                                if (dataSnapshot.child("team_status").getValue().toString().equals("Activated")) {
                                                    listTracking.add(new Tracking(lat, lng, key, team_memberObject.member_name, map.addMarker(new MarkerOptions()
                                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.tour_icon_marker))
                                                            .title(team_memberObject.getMember_name())
                                                            .position(new LatLng(lat, lng)))));

                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                                }
                                //                                                                final String memberName = dataSnapshot.child("member_name").getValue().toString();
//if()

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
                if (getActivity() != null) {
                    final double lat = Double.valueOf(dataSnapshot.child("lat").getValue().toString());
                    final double lng = Double.valueOf(dataSnapshot.child("lng").getValue().toString());
                    final String teamId = dataSnapshot.child("teamId").getValue().toString();
                    final String ucId = dataSnapshot.child("uc_id").getValue().toString();

                    final String key = dataSnapshot.getKey();

                    for (int i = 0; i < listTracking.size(); i++) {
                        if (dataSnapshot.getKey().equals(listTracking.get(i).getId())) {
                            final int finalI = i;

                            FirebaseDatabase.getInstance().getReference().child("uc_teams").child(ucId).child(teamId)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.getValue() != null) {
                                                if (dataSnapshot.child("team_status").getValue().toString().equals("Activated")) {
                                                    FirebaseDatabase.getInstance().getReference().child("polio_teams").child(teamId).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            if (dataSnapshot.getValue() != null) {
                                                                final Team_MemberObject team_memberObject = dataSnapshot.getValue(Team_MemberObject.class);
                                                                if (team_memberObject.getStatus().equals("Active")) {

                                                                    listTracking.add(new Tracking(lat, lng, key, listTracking.get(finalI).getName(), map.addMarker(new MarkerOptions()
                                                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.tour_icon_marker))
                                                                            .title(listTracking.get(finalI).getName())
                                                                            .position(new LatLng(lat, lng)))));
                                                                    listTracking.get(finalI).getMarker().remove();
                                                                    listTracking.remove(finalI);
                                                                    Utils.toast(getActivity(), "Updated");


                                                                }
                                                                //                                                                final String memberName = dataSnapshot.child("member_name").getValue().toString();
//if()

                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });

                                                } else {
                                                    listTracking.get(finalI).getMarker().remove();
                                                    listTracking.remove(finalI);

                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                        }
//
                    }
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

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraIdle() {
        LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
        source = bounds.getCenter();
    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setOnCameraIdleListener(this);
        googleMap.setOnCameraMoveListener(this);
        googleMap.setMyLocationEnabled(true);
//        googleMap.setOnMarkerClickListener(this);
        googleMap.getUiSettings().isZoomControlsEnabled();
        googleMap.getUiSettings().isMyLocationButtonEnabled();
        Utils.checkForLocation(getActivity());

        getLocation();
    }

    public void getLocation() {
        SmartLocation.with(getActivity()).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        if (location != null) {
                            source = new LatLng(location.getLatitude(), location.getLongitude());
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13.0f));
                        }

                    }
                });
    }


}
