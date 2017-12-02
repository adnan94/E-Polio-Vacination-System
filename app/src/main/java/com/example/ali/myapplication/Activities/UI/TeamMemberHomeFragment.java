package com.example.ali.myapplication.Activities.UI;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ali.myapplication.Activities.ModelClasses.BForm;
import com.example.ali.myapplication.Activities.ModelClasses.UserModel;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamMemberHomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    View view;
    SupportMapFragment mSupportMapFragment;
    GoogleMap map;
    ArrayList<BForm> list;

    public TeamMemberHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_team_member_home, container, false);
        } catch (InflateException e) {
        }
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
        Utils.checkForLocation(getActivity());
        getLocation();
        getData();
        return view;
    }


    private void getData() {
        list = new ArrayList<>();

        DatabaseReference ref = FirebaseHandler.getInstance().getAdd_forms();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final BForm bform = dataSnapshot.getValue(BForm.class);
                list.add(bform);
                map.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.tour_icon_marker))
                        .title(list.size() - 1 + "")
                        .position(new LatLng(bform.getLat(), bform.getLng())));


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().isZoomControlsEnabled();
        googleMap.getUiSettings().isMyLocationButtonEnabled();
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnInfoWindowClickListener(this);
        getLocation();

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                Log.d("Called", "called");

                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {
                Log.d("Called", "called");

                View v = LayoutInflater.from(getActivity()).inflate(R.layout.marker_alert_view, null);
                BForm data = list.get(Integer.parseInt(arg0.getTitle()));

                TextView textView = (TextView) v.findViewById(R.id.textView);
                TextView location = (TextView) v.findViewById(R.id.location);
                Button done = (Button) v.findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Utils.toast(getActivity(), "Clicked");
                    }
                });
                RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
                textView.setText(data.getChildName());
                location.setText(data.getAddress());
                return v;

            }
        });
    }

    public void getLocation() {
        SmartLocation.with(getActivity()).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        if (location != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13.0f));
                        }

                    }
                });
    }


    @Override
    public void onInfoWindowClick(final Marker marker) {
        if (list.get(Integer.parseInt(marker.getTitle())).getDrops() == 10) {
            Utils.toast(getActivity(), "Drops limit exceed");
        } else {
            list.get(Integer.parseInt(marker.getTitle())).setDrops(list.get(Integer.parseInt(marker.getTitle())).getDrops()+1);
            FirebaseHandler.getInstance().getAdd_forms().child(list.get(Integer.parseInt(marker.getTitle())).getFormID()).setValue(list.get(Integer.parseInt(marker.getTitle())), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    Utils.toast(getActivity(), "Vacinated");
                    list.get(Integer.parseInt(marker.getTitle()));
                    marker.remove();

                }
            });

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}