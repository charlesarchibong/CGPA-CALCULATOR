package com.zealtech.learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.kamran.calculator.R;
import com.zealtech.learning.database.UserDatabase;
import com.zealtech.learning.util.Constants;

public class SplashScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Constants.userDatabase = Room.databaseBuilder(this, UserDatabase.class, Constants.DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(SplashScreen.this, OnBoarddingScreens.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, 3000);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }

}
