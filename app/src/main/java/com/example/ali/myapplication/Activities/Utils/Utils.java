package com.example.ali.myapplication.Activities.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ADnan on 8/19/2017.
 */

public class Utils {

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
