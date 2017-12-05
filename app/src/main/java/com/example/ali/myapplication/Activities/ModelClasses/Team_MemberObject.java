package com.example.ali.myapplication.Activities.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sami Khan on 11/18/2017.
 */


public class Team_MemberObject implements Parcelable {

    public String member_name;
    public String member_email;
    public String member_nic_no;
    public String member_type;
    public String member_phone_no;
    public String member_uid;
    public String member_pic;
    public String team_uid;

    public Team_MemberObject() {
    }

    public Team_MemberObject(String member_name, String member_email, String member_nic_no, String member_type, String member_phone_no, String member_uid, String member_pic, String team_uid) {
        this.member_name = member_name;
        this.member_email = member_email;
        this.member_nic_no = member_nic_no;
        this.member_type = member_type;
        this.member_phone_no = member_phone_no;
        this.member_uid = member_uid;
        this.member_pic = member_pic;
        this.team_uid = team_uid;
    }

    protected Team_MemberObject(Parcel in) {
        member_name = in.readString();
        member_email = in.readString();
        member_nic_no = in.readString();
        member_type = in.readString();
        member_phone_no = in.readString();
        member_uid = in.readString();
        member_pic = in.readString();
        team_uid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(member_name);
        dest.writeString(member_email);
        dest.writeString(member_nic_no);
        dest.writeString(member_type);
        dest.writeString(member_phone_no);
        dest.writeString(member_uid);
        dest.writeString(member_pic);
        dest.writeString(team_uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Team_MemberObject> CREATOR = new Creator<Team_MemberObject>() {
        @Override
        public Team_MemberObject createFromParcel(Parcel in) {
            return new Team_MemberObject(in);
        }

        @Override
        public Team_MemberObject[] newArray(int size) {
            return new Team_MemberObject[size];
        }
    };

    public String getTeam_uid() {
        return team_uid;
    }

    public void setTeam_uid(String team_uid) {
        this.team_uid = team_uid;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public void setMember_nic_no(String member_nic_no) {
        this.member_nic_no = member_nic_no;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }

    public void setMember_phone_no(String member_phone_no) {
        this.member_phone_no = member_phone_no;
    }

    public void setMember_uid(String member_uid) {
        this.member_uid = member_uid;
    }

    public String getMember_pic() {
        return member_pic;
    }

    public void setMember_pic(String member_pic) {
        this.member_pic = member_pic;
    }

    public String getMember_uid() {
        return member_uid;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getMember_email() {
        return member_email;
    }

    public String getMember_nic_no() {
        return member_nic_no;
    }

    public String getMember_type() {
        return member_type;
    }

    public String getMember_phone_no() {
        return member_phone_no;
    }
}
