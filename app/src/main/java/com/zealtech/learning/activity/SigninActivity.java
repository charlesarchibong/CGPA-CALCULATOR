package com.zealtech.learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kamran.calculator.R;

public class SigninActivity extends AppCompatActivity {

    ImageView sback;
    TextView  faceBook, forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_signin);
        sback = findViewById(R.id.sinb);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SigninActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
        forgetPassword = findViewById(R.id.forgetAcct);
        forgetPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(SigninActivity.this, ForgetPassword.class);
                startActivity(it);
            }
        });
    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
