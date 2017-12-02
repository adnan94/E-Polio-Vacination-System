package com.example.ali.myapplication.Activities.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sami Khan on 11/18/2017.
 */

public class Polio_Team implements Parcelable {

    public String team_uid;
    public String team_name;
    public String team_area;
    public String team_email;
    public String team_image;
    public String team_status;
    public double team_lat;
    public double team_lng;

    public Polio_Team() {
    }

    public Polio_Team(String team_uid, String team_name, String team_area, String team_email, String team_image, String team_status, double team_lat, double team_lng) {
        this.team_uid = team_uid;
        this.team_name = team_name;
        this.team_area = team_area;
        this.team_email = team_email;
        this.team_image = team_image;
        this.team_status = team_status;
        this.team_lat = team_lat;
        this.team_lng = team_lng;
    }

    protected Polio_Team(Parcel in) {
        team_uid = in.readString();
        team_name = in.readString();
        team_area = in.readString();
        team_email = in.readString();
        team_image = in.readString();
        team_status = in.readString();
        team_lat = in.readDouble();
        team_lng = in.readDouble();
    }

    public static final Creator<Polio_Team> CREATOR = new Creator<Polio_Team>() {
        @Override
        public Polio_Team createFromParcel(Parcel in) {
            return new Polio_Team(in);
        }

        @Override
        public Polio_Team[] newArray(int size) {
            return new Polio_Team[size];
        }
    };

    public String getTeam_email() {
        return team_email;
    }

    public void setTeam_email(String team_email) {
        this.team_email = team_email;
    }

    public String getTeam_uid() {
        return team_uid;
    }

    public void setTeam_uid(String team_uid) {
        this.team_uid = team_uid;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_area() {
        return team_area;
    }

    public void setTeam_area(String team_area) {
        this.team_area = team_area;
    }

    public String getTeam_image() {
        return team_image;
    }

    public void setTeam_image(String team_image) {
        this.team_image = team_image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(team_uid);
        parcel.writeString(team_name);
        parcel.writeString(team_area);
        parcel.writeString(team_email);
        parcel.writeString(team_image);
        parcel.writeString(team_status);
        parcel.writeDouble(team_lat);
        parcel.writeDouble(team_lng);
    }

    public String getTeam_status() {
        return team_status;
    }

    public void setTeam_status(String team_status) {
        this.team_status = team_status;
    }

    public double getTeam_lat() {
        return team_lat;
    }

    public void setTeam_lat(double team_lat) {
        this.team_lat = team_lat;
    }

    public double getTeam_lng() {
        return team_lng;
    }

    public void setTeam_lng(double team_lng) {
        this.team_lng = team_lng;
    }
}
