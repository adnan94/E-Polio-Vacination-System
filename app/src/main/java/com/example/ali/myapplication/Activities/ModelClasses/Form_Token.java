package com.example.ali.myapplication.Activities.ModelClasses;

/**
 * Created by Sami Khan on 10/22/2017.
 */

public class Form_Token {

    public String token_id;
    public String Form_id;
    public String ApplicantName;
    public String CNIC_no;
    public long form_date;
    public long appointment_date;



    public Form_Token() {
    }

    public Form_Token(String token_id, String form_id, String applicantName, String CNIC_no, long form_date, long appointment_date) {
        this.token_id = token_id;
        Form_id = form_id;
        ApplicantName = applicantName;
        this.CNIC_no = CNIC_no;
        this.form_date = form_date;
        this.appointment_date = appointment_date;
    }

    public String getForm_id() {
        return Form_id;
    }

    public void setForm_id(String form_id) {
        Form_id = form_id;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getApplicantName() {
        return ApplicantName;
    }

    public void setApplicantName(String applicantName) {
        ApplicantName = applicantName;
    }

    public String getCNIC_no() {
        return CNIC_no;
    }

    public void setCNIC_no(String CNIC_no) {
        this.CNIC_no = CNIC_no;
    }

    public long getForm_date() {
        return form_date;
    }

    public void setForm_date(long form_date) {
        this.form_date = form_date;
    }

    public long getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(long appointment_date) {
        this.appointment_date = appointment_date;
    }
}
