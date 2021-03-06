package com.zealtech.learning.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kamran.calculator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.zealtech.learning.model.User;
import com.zealtech.learning.util.Constants;

public class MainActivity extends AppCompatActivity {
    TextView sin;
    LinearLayout circle;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
        {
            User.currentAppUser = Constants.userDatabase.getUserDAO().getUserByUid(firebaseAuth.getCurrentUser().getUid());
            startActivity(new Intent(this, DashboardHome.class));
            finish();
        }
        setContentView(R.layout.activity_main);
        circle = findViewById(R.id.signup);
        sin = findViewById(R.id.sin);

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(it);

            }
        });
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(it);
            }
        });

    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }
    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setMessage("Do you want to close " + getString(R.string.app_name))
                .setTitle("Exit")
                .setIcon(R.drawable.logo)
                .setNegativeButton("No", null)
                .show();
    }
}
