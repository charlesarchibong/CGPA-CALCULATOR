package com.zealtech.learning.util;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.appcompat.app.AlertDialog;

import com.example.kamran.calculator.R;

public class AppUtil
{
    public static boolean checkInternetAccess(Context context)
    {
       ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       // Network availableNetworkInfo = connectivityManager.getActiveNetwork();
        return false;
    }

    public static AlertDialog getAlertView(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.loading_progress_bar);
        builder.setCancelable(false);
        AlertDialog progressbar = builder.create();
        return progressbar;
    }
}
