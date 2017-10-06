
package com.example.ali.myapplication.Activities.ModelClasses;

/**
 * Created by ADnan on 8/19/2017.
 */

public class BForm {
    String applicantName,applicantCnic,childName,relation,religion,fatherName,fatherCnic,motherName,motherCnic,areaOfBirth,dateOfBirth,disablity,address,district,gender;
    boolean vacinated;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public BForm() {
    }

    public BForm(String applicantName, String applicantCnic, String childName, String relation, String religion, String fatherName, String fatherCnic, String motherName, String motherCnic, String areaOfBirth, String dateOfBirth, String disablity, String address, String district, String gender, boolean vacinated) {
        this.applicantName = applicantName;
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
    }

    public String getApplicantName() {

        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
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
