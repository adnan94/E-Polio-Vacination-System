package com.example.ali.myapplication.Activities.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ali.myapplication.Activities.ModelClasses.Team_MemberObject;

/**
 * Created by Sami Khan on 12/5/2017.
 */

public class SharedPref_Team   {
    private static String NAME = "packageNamee";
    private static String M_NAME = "mname";
    private static String M_EMAIL = "memail";
    private static String M_NICNO = "mnic";
    private static String M_TYPE = "mtype";
    private static String M_PHONE = "mphone";
    private static String M_UID = "m_uid";
    private static String M_PIC = "m_pic";
    private static String M_T_UID = "m_tuid";

    public static void setCurrentUser(Context context, Team_MemberObject user) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(M_NAME, user.getMember_name()).apply();
        preferences.edit().putString(M_EMAIL, user.getMember_email()).apply();
        preferences.edit().putString(M_NICNO, user.getMember_nic_no()).apply();
        preferences.edit().putString(M_TYPE, user.getMember_type()).apply();
        preferences.edit().putString(M_PHONE, user.getMember_phone_no()).apply();
        preferences.edit().putString(M_UID, user.getMember_uid()).apply();
        preferences.edit().putString(M_PIC,user.getMember_pic()).apply();
        preferences.edit().putString(M_T_UID,user.getTeam_uid()).apply();
    }

    public static Team_MemberObject getCurrentUser(Context context) {
        Team_MemberObject user = new Team_MemberObject();
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        user.setMember_name(preferences.getString(M_NAME, ""));
        user.setMember_email(preferences.getString(M_EMAIL, ""));
        user.setMember_nic_no(preferences.getString(M_NICNO, ""));
        user.setMember_type(preferences.getString(M_TYPE, ""));
        user.setMember_phone_no(preferences.getString(M_PHONE, ""));
        user.setTeam_uid(preferences.getString(M_UID, ""));
        user.setMember_pic(preferences.getString(M_PIC, ""));
        user.setTeam_uid(preferences.getString(M_T_UID, ""));
        return user;
    }

}
