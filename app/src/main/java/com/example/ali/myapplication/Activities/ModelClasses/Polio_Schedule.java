package com.example.ali.myapplication.Activities.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sami Khan on 12/10/2017.
 */

public class Polio_Schedule implements Parcelable {

    public String schedule_from;
    public String schedule_to;
    public String schedule_des;
    public String schedule_titile;
    public String schedule_id;

    public Polio_Schedule() {
    }

    public Polio_Schedule(String schedule_from, String schedule_to, String schedule_des, String schedule_titile, String schedule_id) {
        this.schedule_from = schedule_from;
        this.schedule_to = schedule_to;
        this.schedule_des = schedule_des;
        this.schedule_titile = schedule_titile;
        this.schedule_id = schedule_id;
    }

    protected Polio_Schedule(Parcel in) {
        schedule_from = in.readString();
        schedule_to = in.readString();
        schedule_des = in.readString();
        schedule_titile = in.readString();
        schedule_id = in.readString();
    }

    public static final Creator<Polio_Schedule> CREATOR = new Creator<Polio_Schedule>() {
        @Override
        public Polio_Schedule createFromParcel(Parcel in) {
            return new Polio_Schedule(in);
        }

        @Override
        public Polio_Schedule[] newArray(int size) {
            return new Polio_Schedule[size];
        }
    };

    public String getSchedule_from() {
        return schedule_from;
    }

    public void setSchedule_from(String schedule_from) {
        this.schedule_from = schedule_from;
    }

    public String getSchedule_to() {
        return schedule_to;
    }

    public void setSchedule_to(String schedule_to) {
        this.schedule_to = schedule_to;
    }

    public String getSchedule_des() {
        return schedule_des;
    }

    public void setSchedule_des(String schedule_des) {
        this.schedule_des = schedule_des;
    }

    public String getSchedule_titile() {
        return schedule_titile;
    }

    public void setSchedule_titile(String schedule_titile) {
        this.schedule_titile = schedule_titile;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(schedule_from);
        parcel.writeString(schedule_to);
        parcel.writeString(schedule_des);
        parcel.writeString(schedule_titile);
        parcel.writeString(schedule_id);
    }
}
