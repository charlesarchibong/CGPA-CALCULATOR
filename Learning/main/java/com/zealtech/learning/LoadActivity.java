package com.zealtech.learning;

import android.os.Bundle;
import android.transition.Explode;

import androidx.appcompat.app.AppCompatActivity;

public class LoadActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        getSupportActionBar().setTitle("App Layout");
        Explode explode = new Explode();
        explode.setDuration(getResources().getInteger(R.integer.short_duration));
        getWindow().setEnterTransition(explode);
    }
}

