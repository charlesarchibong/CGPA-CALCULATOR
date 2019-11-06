package com.zealtech.learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kamran.calculator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;



public class DashboardHome extends AppCompatActivity
{
    private TextView mTextMessage;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                {
                    mTextMessage.setText("Home");
                    toolbar.setTitle("Home");
                    return true;
                }
                case R.id.navigation_course:
                {
                    mTextMessage.setText("Register Course");
                    toolbar.setTitle("Register Course");
                    return true;
                }
                case R.id.navigation_grade:
                {
                    mTextMessage.setText("Record Grade");
                    toolbar.setTitle("Record Grade");
                    return true;
                }
                case R.id.navigation_profile :
                {
                    mTextMessage.setText("Profile");
                    toolbar.setTitle("Profile");
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        getSupportActionBar().hide();
        firebaseAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.dashbooardToolbar);
        toolbar.setTitle("Home");
        //getSupportActionBar().hide();
        toolbar.inflateMenu(R.menu.dashboard_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.action_settings: {return true;}
                    case R.id.action_logout : {signOut();}
                }
                return true;
            }
        });
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public void signOut() {
        firebaseAuth.signOut();
        startActivity(new Intent(DashboardHome.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


}
