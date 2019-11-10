package com.zealtech.learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.example.kamran.calculator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.zealtech.learning.dao.UserDAO;
import com.zealtech.learning.database.UserDatabase;
import com.zealtech.learning.fragments.CourseFragment;
import com.zealtech.learning.fragments.GradeFragment;
import com.zealtech.learning.fragments.HomeFragment;
import com.zealtech.learning.fragments.ProfileFragment;
import com.zealtech.learning.model.User;
import com.zealtech.learning.util.Constants;


public class DashboardHome extends AppCompatActivity
{
    private TextView mTextMessage;
    private Toolbar toolbar;
    BottomNavigationView navView;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    final Fragment homeFragment = new HomeFragment();
    final Fragment courseFragment = new CourseFragment();
    final Fragment gradeFragment = new GradeFragment();
    final Fragment profileFragment = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_home);
        fm.beginTransaction().add(R.id.dashboard_main_container, profileFragment, "4").hide(profileFragment).commit();
        fm.beginTransaction().add(R.id.dashboard_main_container, gradeFragment, "3").hide(gradeFragment).commit();
        fm.beginTransaction().add(R.id.dashboard_main_container, courseFragment, "2").hide(courseFragment).commit();
        fm.beginTransaction().add(R.id.dashboard_main_container, homeFragment, "1").commit();
        this.navView = findViewById(R.id.nav_view);
        this.mTextMessage = findViewById(R.id.message);
        getSupportActionBar().hide();
        this.authStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(DashboardHome.this, MainActivity.class));
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        };
        this.firebaseAuth = FirebaseAuth.getInstance();
        Constants.userDatabase = Room.databaseBuilder(this, UserDatabase.class, Constants.DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        UserDAO userDAO = Constants.userDatabase.getUserDAO();
        User.currentAppUser = userDAO.getUserByUid(firebaseAuth.getCurrentUser().getUid());
        this.toolbar = findViewById(R.id.dashbooardToolbar);
        this.toolbar.setTitle("Home");
        //getSupportActionBar().hide();
        this.toolbar.inflateMenu(R.menu.dashboard_menu);
        this.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.action_settings: {return true;}
                    case R.id.action_logout : {signOut(); return true;}
                    case R.id.action_exit : {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;
                    }

                }
                return true;
            }
        });
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

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
                    toolbar.setTitle("Home");
                    fm.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    return true;
                }
                case R.id.navigation_course:
                {
                    toolbar.setTitle("Register Course");
                    fm.beginTransaction().hide(active).show(courseFragment).commit();
                    active = courseFragment;
                    return true;
                }
                case R.id.navigation_grade:
                {
                    toolbar.setTitle("Record Grade");
                    fm.beginTransaction().hide(active).show(gradeFragment).commit();
                    active = gradeFragment;
                    return true;
                }
                case R.id.navigation_profile :
                {
                    toolbar.setTitle("Profile");
                    fm.beginTransaction().hide(active).show(profileFragment).commit();
                    active = profileFragment;
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onStart()
    {
        super.onStart();
        this.firebaseAuth.addAuthStateListener(authStateListener);
        this.navView.setSelectedItemId(R.id.navigation_home);
        this.active = homeFragment;
    }
    public void signOut() {
        firebaseAuth.signOut();
    }
    @Override
    public void onBackPressed()
    {
        this.counter++;
        if(this.counter == 2){
            Toast.makeText(this, "Goto setting and exit the app!", Toast.LENGTH_SHORT).show();
        }

        final long DELAY_TIME = 3000L;
        new Thread(new Runnable() {
            public void run(){
                try {
                    Thread.sleep(DELAY_TIME);
                    counter = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.navView.setSelectedItemId(R.id.navigation_home);
        this.active = homeFragment;
    }

}
