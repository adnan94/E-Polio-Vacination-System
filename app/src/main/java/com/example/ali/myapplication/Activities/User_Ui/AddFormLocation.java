package com.example.ali.myapplication.Activities.User_Ui;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ali.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

//import io.nlopez.smartlocation.OnLocationUpdatedListener;
//import io.nlopez.smartlocation.SmartLocation;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFormLocation extends Fragment implements OnMapReadyCallback {
    View view;
    SupportMapFragment mSupportMapFragment;
    GoogleMap map;

    public AddFormLocation() {
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
            view = inflater.inflate(R.layout.fragment_add_form_location, container, false);
        } catch (InflateException e) {
        }
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.maincontainer, mSupportMapFragment).commit();
        }
        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getLocation();
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
}
