/*
 * Copyright (c) 2019 , Zealnetworks Technologies. All rights reserved. DO NOT ALTER OR REMOVE
 * COPYRIGHT NOTICES OR THIS FILE HEADER. You are not meant to edit or modify this source code unless you are
 * authorized to do so.  Please contact me at contact@zealtech.com.ng or visit www.zealtech.com.ng if you need
 * additional information or have any questions.
 */

package com.zealtech.learning.testing;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kamran.calculator.R;
import com.zealtech.learning.util.ConnectivityReceiver;
import com.zealtech.learning.util.Learning;


public class ConnectionTest extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener
{

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_test);
        textView = findViewById(R.id.connectText);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Learning.getInstance().setConnectivityListener(ConnectionTest.this);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected)
    {
        if(ConnectivityReceiver.isConnected())
        {
            textView.setText("Connected");
        }
        else
        {
            textView.setText("Not Connected");
        }
    }


}
