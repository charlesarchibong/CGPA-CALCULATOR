/*
 * Copyright (c) 2019 , Zealnetworks Technologies. All rights reserved. DO NOT ALTER OR REMOVE
 * COPYRIGHT NOTICES OR THIS FILE HEADER. You are not meant to edit or modify this source code unless you are
 * authorized to do so.  Please contact me at contact@zealtech.com.ng or visit www.zealtech.com.ng if you need
 * additional information or have any questions.
 */

package com.zealtech.learning.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver
{
    public static  ConnectivityReceiverListener connectivityReceiverListener;

    public ConnectivityReceiver()
    {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected =  networkInfo != null && networkInfo.isConnected();
        if(connectivityReceiverListener != null)
        {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }
    }

    public  static boolean isConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) Learning.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

    public interface ConnectivityReceiverListener
    {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
