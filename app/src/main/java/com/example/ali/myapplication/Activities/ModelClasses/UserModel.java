package com.example.ali.myapplication.Activities.ModelClasses;

/**
 * Created by ADnan on 10/1/2017.
 */

public class UserModel {
   public String name, fname, address, email, password, cnic, cellNo;



    public UserModel(String name, String fname, String address, String email, String password, String cnic, String cellNo) {
        this.name = name;
        this.fname = fname;
        this.address = address;
        this.email = email;
        this.password = password;
        this.cnic = cnic;
        this.cellNo = cellNo;
    }

    public UserModel() {
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



}
