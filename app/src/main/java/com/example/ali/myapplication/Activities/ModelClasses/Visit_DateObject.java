package com.example.ali.myapplication.Activities.ModelClasses;

/**
 * Created by Sami Khan on 12/11/2017.
 */

public class Visit_DateObject  {

    public String visit_date;
    public String visit_for;
    public String visit_to;
    public String visit_uid;
    public String schedule_uid;


    public Visit_DateObject(String visit_date, String visit_for, String visit_to, String visit_uid, String schedule_uid) {
        this.visit_date = visit_date;
        this.visit_for = visit_for;
        this.visit_to = visit_to;
        this.visit_uid = visit_uid;
        this.schedule_uid = schedule_uid;
    }

    public Visit_DateObject() {
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getVisit_for() {
        return visit_for;
    }

    public void setVisit_for(String visit_for) {
        this.visit_for = visit_for;
    }

    public String getVisit_to() {
        return visit_to;
    }

    public void setVisit_to(String visit_to) {
        this.visit_to = visit_to;
    }

    public String getVisit_uid() {
        return visit_uid;
    }

    public void setVisit_uid(String visit_uid) {
        this.visit_uid = visit_uid;
    }

    public String getSchedule_uid() {
        return schedule_uid;
    }

    public void setSchedule_uid(String schedule_uid) {
        this.schedule_uid = schedule_uid;
    }
}
