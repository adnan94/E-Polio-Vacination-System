package com.example.ali.myapplication.Activities.ModelClasses;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by AdnanAhmed on 12/2/2017.
 */

public class Tracking {
    double lat,lng;
    String id;
    String name;
    Marker marker;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Tracking(double lat, double lng, String id, String name, Marker marker) {
        this.lat = lat;
        this.lng = lng;
        this.id = id;
        this.name = name;
        this.marker=marker;

    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
