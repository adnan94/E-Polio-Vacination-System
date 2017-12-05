package com.example.ali.myapplication.Activities.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ali.myapplication.Activities.ModelClasses.Team_MemberObject;
import com.example.ali.myapplication.Activities.ModelClasses.UC_Object;

/**
 * Created by Sami Khan on 12/5/2017.
 */

public class SharedPref_UC {
    private static String NAME = "packageName";
    private static String UC_AREA = "mname";
    private static String UC_NAME = "memail";
    private static String UC_CNIC = "mnic";
    private static String UC_EMAIL = "mtype";
    private static String UC_PHONE = "mphone";
    private static String UC_PIC = "m_uid";
    private static String UC_UID = "m_pic";
 //   private static String M_T_UID = "m_tuid";

    public static void setCurrentUser(Context context, UC_Object user) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(UC_AREA, user.getUc_area()).apply();
        preferences.edit().putString(UC_NAME, user.getUc_membern()).apply();
        preferences.edit().putString(UC_CNIC, user.getUc_member_cnic()).apply();
        preferences.edit().putString(UC_EMAIL, user.getUc_member_email()).apply();
        preferences.edit().putString(UC_PHONE, user.getUc_member_phone()).apply();
        preferences.edit().putString(UC_PIC, user.getUc_member_piture()).apply();
        preferences.edit().putString(UC_UID,user.getUc_member_uid()).apply();
     //   preferences.edit().putString(M_T_UID,user.getTeam_uid()).apply();
    }

    public static UC_Object getCurrentUser(Context context) {
        UC_Object user = new UC_Object();
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        user.setUc_area(preferences.getString(UC_AREA, ""));
        user.setUc_membern(preferences.getString(UC_NAME, ""));
        user.setUc_member_cnic(preferences.getString(UC_CNIC, ""));
        user.setUc_member_email(preferences.getString(UC_EMAIL, ""));
        user.setUc_member_phone(preferences.getString(UC_PHONE, ""));
        user.setUc_member_piture(preferences.getString(UC_PIC, ""));
        user.setUc_member_uid(preferences.getString(UC_UID, ""));
    //    user.setTeam_uid(preferences.getString(M_T_UID, ""));
        return user;
    }

}
