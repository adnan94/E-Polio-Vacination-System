package com.example.ali.myapplication.Activities.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ADnan on 8/19/2017.
 */

public class Utils {
    private static Typeface typeface;

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void checkForLocation(Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps(context);
        }


    }

    public static void setTypeFace(Context context, EditText editText) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        editText.setTypeface(typeface);
    }

    public static void setTypeFace(Context context, Button button) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-SemiBold.ttf");
        button.setTypeface(typeface);
    }

    public static void setTypeFace(Context context, CheckBox check) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        check.setTypeface(typeface);
    }

    public static void setTypeFace(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        textView.setTypeface(typeface);

    }


    public static void relwayRegular(Context context, Button textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
        textView.setTypeface(typeface);

    }
    public static void relwayRegular(Context context, EditText textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
        textView.setTypeface(typeface);

    }
    public static void relwayRegular(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
        textView.setTypeface(typeface);

    }

    //
    public static void relwayMedium(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Medium.ttf");
        textView.setTypeface(typeface);

    }
    public static void relwayMedium(Context context, EditText textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Medium.ttf");
        textView.setTypeface(typeface);

    }
    public static void relwayMedium(Context context, Button textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Medium.ttf");
        textView.setTypeface(typeface);

    }


    public static void relwaySemiBold(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-SemiBold.ttf");
        textView.setTypeface(typeface);

    }
    public static void relwaySemiBold(Context context, EditText textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-SemiBold.ttf");
        textView.setTypeface(typeface);

    }
    public static void relwaySemiBold(Context context, Button textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-SemiBold.ttf");
        textView.setTypeface(typeface);

    }



    public static void relwayLight(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Light.ttf");
        textView.setTypeface(typeface);

    }
    public static void relwayLight(Context context, EditText textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Light.ttf");
        textView.setTypeface(typeface);

    }
    public static void relwayLight(Context context, Button textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Light.ttf");
        textView.setTypeface(typeface);

    }




    public static void relwayBold(Context context, TextView textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Bold.ttf");
        textView.setTypeface(typeface);

    }
    public static void relwayBold(Context context, EditText textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Bold.ttf");
        textView.setTypeface(typeface);

    }
    public static void relwayBold(Context context, Button textView) {
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Bold.ttf");
        textView.setTypeface(typeface);

    }


    public static String formatDAte(Date date1) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date1);
        return dateString;
    }
    private static void buildAlertMessageNoGps(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
