package com.example.ali.myapplication.Activities.ModelClasses;

/**
 * Created by ADnan on 10/1/2017.
 */

public class UserModel {
   public String name,picUrl, fname, address, email, password, cnic, cellNo,uid;
    public int user_type;



    public UserModel(String name, String fname, String address, String email, String password, String cnic, String cellNo, int user_type,String picUrl,String uid) {
        this.name = name;
        this.fname = fname;
        this.address = address;
        this.email = email;
        this.password = password;
        this.cnic = cnic;
        this.cellNo = cellNo;
        this.user_type = user_type;
    this.uid=uid;
        this.picUrl=picUrl;
    }
    public UserModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static UserModel myObj;

    public static UserModel getInstance(String name, String fname, String address, String email, String password, String cnic, String cellNo, int user_type,String uid,String picUrl){
        if(myObj == null){
            myObj = new UserModel(name, fname,address,email,password,cnic,cellNo,user_type,uid,picUrl);
        }
        return myObj;
    }

    public static UserModel getInstanceIfNotNull(){
        return myObj;
    }

}
