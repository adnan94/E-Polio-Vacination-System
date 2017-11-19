
package com.example.ali.myapplication.Activities.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ADnan on 8/19/2017.
 */

public class BForm implements Parcelable {
    private String userName, applicantCnic,
            childName, relation, religion, fatherName, fatherCnic,
            motherName, motherCnic, areaOfBirth, dateOfBirth, disablity, address, district, gender;
    private boolean vacinated;
    private String formID;
    private String user_uid;
    private String form_status;
    private long timestamp;
    private double lat, lng;
    private int drops;

    public BForm() {
    }

    public BForm(String userName, String applicantCnic, String childName, String relation, String religion, String fatherName, String fatherCnic, String motherName, String motherCnic, String areaOfBirth, String dateOfBirth, String disablity, String address, String district, String gender, boolean vacinated, String formID, String user_uid, String form_status, long timestamp, double lat, double lng, int drops) {
        this.userName = userName;
        this.applicantCnic = applicantCnic;
        this.childName = childName;
        this.relation = relation;
        this.religion = religion;
        this.fatherName = fatherName;
        this.fatherCnic = fatherCnic;
        this.motherName = motherName;
        this.motherCnic = motherCnic;
        this.areaOfBirth = areaOfBirth;
        this.dateOfBirth = dateOfBirth;
        this.disablity = disablity;
        this.address = address;
        this.district = district;
        this.gender = gender;
        this.vacinated = vacinated;
        this.formID = formID;
        this.user_uid = user_uid;
        this.form_status = form_status;
        this.timestamp = timestamp;
        this.lat = lat;
        this.lng = lng;
        this.drops = drops;
    }

    protected BForm(Parcel in) {
        userName = in.readString();
        applicantCnic = in.readString();
        childName = in.readString();
        relation = in.readString();
        religion = in.readString();
        fatherName = in.readString();
        fatherCnic = in.readString();
        motherName = in.readString();
        motherCnic = in.readString();
        areaOfBirth = in.readString();
        dateOfBirth = in.readString();
        disablity = in.readString();
        address = in.readString();
        district = in.readString();
        gender = in.readString();
        vacinated = in.readByte() != 0;
        formID = in.readString();
        user_uid = in.readString();
        form_status = in.readString();
        timestamp = in.readLong();
        lat = in.readDouble();
        lng = in.readDouble();
        drops = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(applicantCnic);
        dest.writeString(childName);
        dest.writeString(relation);
        dest.writeString(religion);
        dest.writeString(fatherName);
        dest.writeString(fatherCnic);
        dest.writeString(motherName);
        dest.writeString(motherCnic);
        dest.writeString(areaOfBirth);
        dest.writeString(dateOfBirth);
        dest.writeString(disablity);
        dest.writeString(address);
        dest.writeString(district);
        dest.writeString(gender);
        dest.writeByte((byte) (vacinated ? 1 : 0));
        dest.writeString(formID);
        dest.writeString(user_uid);
        dest.writeString(form_status);
        dest.writeLong(timestamp);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeInt(drops);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BForm> CREATOR = new Creator<BForm>() {
        @Override
        public BForm createFromParcel(Parcel in) {
            return new BForm(in);
        }

        @Override
        public BForm[] newArray(int size) {
            return new BForm[size];
        }
    };

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getForm_status() {
        return form_status;
    }

    public void setForm_status(String form_status) {
        this.form_status = form_status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDrops() {
        return drops;
    }

    public void setDrops(int drops) {
        this.drops = drops;
    }

    public String getApplicantCnic() {
        return applicantCnic;
    }

    public void setApplicantCnic(String applicantCnic) {
        this.applicantCnic = applicantCnic;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherCnic() {
        return fatherCnic;
    }

    public void setFatherCnic(String fatherCnic) {
        this.fatherCnic = fatherCnic;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherCnic() {
        return motherCnic;
    }

    public void setMotherCnic(String motherCnic) {
        this.motherCnic = motherCnic;
    }

    public String getAreaOfBirth() {
        return areaOfBirth;
    }

    public void setAreaOfBirth(String areaOfBirth) {
        this.areaOfBirth = areaOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDisablity() {
        return disablity;
    }

    public void setDisablity(String disablity) {
        this.disablity = disablity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public boolean isVacinated() {
        return vacinated;
    }

    public void setVacinated(boolean vacinated) {
        this.vacinated = vacinated;
    }


}
