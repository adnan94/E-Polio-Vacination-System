package com.example.ali.myapplication.Activities.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sami Khan on 12/5/2017.
 */

public class UC_Object implements Parcelable{
    public String uc_area;
    public String uc_membern;
    public String uc_member_cnic;
    public String uc_member_email;
    public String uc_member_phone;
    public String uc_member_piture;
    public String uc_member_uid;

    public UC_Object() {
    }

    public UC_Object(String uc_area, String uc_membern, String uc_member_cnic, String uc_member_email, String uc_member_phone, String uc_member_piture, String uc_member_uid) {
        this.uc_area = uc_area;
        this.uc_membern = uc_membern;
        this.uc_member_cnic = uc_member_cnic;
        this.uc_member_email = uc_member_email;
        this.uc_member_phone = uc_member_phone;
        this.uc_member_piture = uc_member_piture;
        this.uc_member_uid = uc_member_uid;
    }

    protected UC_Object(Parcel in) {
        uc_area = in.readString();
        uc_membern = in.readString();
        uc_member_cnic = in.readString();
        uc_member_email = in.readString();
        uc_member_phone = in.readString();
        uc_member_piture = in.readString();
        uc_member_uid = in.readString();
    }

    public static final Creator<UC_Object> CREATOR = new Creator<UC_Object>() {
        @Override
        public UC_Object createFromParcel(Parcel in) {
            return new UC_Object(in);
        }

        @Override
        public UC_Object[] newArray(int size) {
            return new UC_Object[size];
        }
    };

    public String getUc_area() {
        return uc_area;
    }

    public void setUc_area(String uc_area) {
        this.uc_area = uc_area;
    }

    public String getUc_membern() {
        return uc_membern;
    }

    public void setUc_membern(String uc_membern) {
        this.uc_membern = uc_membern;
    }

    public String getUc_member_cnic() {
        return uc_member_cnic;
    }

    public void setUc_member_cnic(String uc_member_cnic) {
        this.uc_member_cnic = uc_member_cnic;
    }

    public String getUc_member_email() {
        return uc_member_email;
    }

    public void setUc_member_email(String uc_member_email) {
        this.uc_member_email = uc_member_email;
    }

    public String getUc_member_phone() {
        return uc_member_phone;
    }

    public void setUc_member_phone(String uc_member_phone) {
        this.uc_member_phone = uc_member_phone;
    }

    public String getUc_member_piture() {
        return uc_member_piture;
    }

    public void setUc_member_piture(String uc_member_piture) {
        this.uc_member_piture = uc_member_piture;
    }

    public String getUc_member_uid() {
        return uc_member_uid;
    }

    public void setUc_member_uid(String uc_member_uid) {
        this.uc_member_uid = uc_member_uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uc_area);
        parcel.writeString(uc_membern);
        parcel.writeString(uc_member_cnic);
        parcel.writeString(uc_member_email);
        parcel.writeString(uc_member_phone);
        parcel.writeString(uc_member_piture);
        parcel.writeString(uc_member_uid);
    }
}
