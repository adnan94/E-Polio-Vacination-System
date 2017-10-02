package com.example.ali.myapplication.Activities.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ali.myapplication.Activities.ModelClasses.UserModel;


/**
 * Created by Faiz on 7/31/2017.
 */

public class SharedPref {
    private static String NAME = "packageName";
    private static String U_FNAME = "fname";
    private static String F_LNAME = "lanme";
    private static String U_ID = "userid";
    private static String U_EMAIL = "email";
    private static String U_ADDRESS = "iamgurl";
    private static String U_PASS = "u_dob";
    private static String U_CELLNO = "u_gea";
    private static String U_STATUS = "u_status";

    public static void setCurrentUser(Context context, UserModel user) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(U_FNAME, user.getName()).apply();
        preferences.edit().putString(F_LNAME, user.getFname()).apply();
        preferences.edit().putString(U_ADDRESS, user.getAddress()).apply();
        preferences.edit().putString(U_ID, user.getCnic()).apply();
        preferences.edit().putString(U_EMAIL, user.getEmail()).apply();
        preferences.edit().putString(U_CELLNO, user.getCellNo()).apply();
        preferences.edit().putString(U_PASS, user.getPassword()).apply();
    }

    public static UserModel getCurrentUser(Context context) {
        UserModel user = new UserModel();
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        user.setName(preferences.getString(U_FNAME, ""));
        user.setFname(preferences.getString(F_LNAME, ""));
        user.setEmail(preferences.getString(U_EMAIL, ""));
        user.setCnic(preferences.getString(U_ID, ""));
        user.setAddress(preferences.getString(U_ADDRESS, ""));
        user.setCellNo(preferences.getString(U_CELLNO, ""));
        user.setPassword(preferences.getString(U_PASS, ""));
        return user;
    }

}
