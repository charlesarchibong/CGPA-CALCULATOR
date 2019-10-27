package com.example.kamran.calculator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kamran.calculator.R;

public class SigninActivity extends AppCompatActivity {

    ImageView sback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        sback = findViewById(R.id.sinb);
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SigninActivity.this, MainActivity.class);
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
