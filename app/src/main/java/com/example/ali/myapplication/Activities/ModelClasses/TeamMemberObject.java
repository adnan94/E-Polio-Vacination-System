package com.example.ali.myapplication.Activities.ModelClasses;

/**
 * Created by Sami Khan on 11/12/2017.
 */

public class TeamMemberObject {

    public String  member_name;
    public String member_nic_no;
    public String member_address;
    public String member_phone_no;
    public String member_image;
    public String member_uid;
    public String member_teamuid;

    public TeamMemberObject(String member_name, String member_nic_no, String member_address, String member_phone_no, String member_image, String member_uid, String member_teamuid) {
        this.member_name = member_name;
        this.member_nic_no = member_nic_no;
        this.member_address = member_address;
        this.member_phone_no = member_phone_no;
        this.member_image = member_image;
        this.member_uid = member_uid;
        this.member_teamuid = member_teamuid;
    }


    public TeamMemberObject() {
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_nic_no() {
        return member_nic_no;
    }

    public void setMember_nic_no(String member_nic_no) {
        this.member_nic_no = member_nic_no;
    }

    public String getMember_address() {
        return member_address;
    }

    public void setMember_address(String member_address) {
        this.member_address = member_address;
    }

    public String getMember_phone_no() {
        return member_phone_no;
    }

    public void setMember_phone_no(String member_phone_no) {
        this.member_phone_no = member_phone_no;
    }

    public String getMember_image() {
        return member_image;
    }

    public void setMember_image(String member_image) {
        this.member_image = member_image;
    }

    public String getMember_uid() {
        return member_uid;
    }

    public void setMember_uid(String member_uid) {
        this.member_uid = member_uid;
    }

    public String getMember_teamuid() {
        return member_teamuid;
    }

    public void setMember_teamuid(String member_teamuid) {
        this.member_teamuid = member_teamuid;
    }
}
