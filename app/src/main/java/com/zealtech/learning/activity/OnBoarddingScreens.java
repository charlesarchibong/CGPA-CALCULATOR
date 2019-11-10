package com.zealtech.learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.kamran.calculator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.zealtech.learning.util.PagerAdapter;
import com.zealtech.learning.util.PreferanceManager;

public class OnBoarddingScreens extends AppCompatActivity
{
    private ViewPager viewPager;
    private int [] slides = {R.layout.first_slide, R.layout.second_slide, R.layout.third_slide};
    private PagerAdapter pagerAdapter;
    private LinearLayout linearLayout;
    private ImageView [] dots;
    private Button nextBtn, backBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        firebaseAuth = FirebaseAuth.getInstance();
        if(new PreferanceManager(this).checkPreference())
            loadHome();
        setContentView(R.layout.activity_welcome);
        viewPager = findViewById(R.id.viewPager);
        linearLayout = findViewById(R.id.dots);
        nextBtn = findViewById(R.id.nextBtn);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadHome();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nextSlide();
            }
        });
        pagerAdapter = new PagerAdapter(slides, this);
        viewPager.setAdapter(pagerAdapter);
        createDot(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                createDot(position);
                if(position == slides.length - 1)
                {
                    nextBtn.setText("Start");
                    backBtn.setVisibility(View.INVISIBLE);
                }
                else
                {
                    nextBtn.setText("Next");
                    backBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }
    private void createDot(int currentPosition)
    {
        if(linearLayout != null )
            linearLayout.removeAllViews();
        dots = new ImageView[slides.length];
        for(int i = 0; i<slides.length; i++)
        {
            dots[i] = new ImageView(this);
            if(i == currentPosition)
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));
            else
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            linearLayout.addView(dots[i], params );
        }
    }

    private void loadHome()
    {
        new PreferanceManager(this).writePreference();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void nextSlide()
    {
        int nextSlide = viewPager.getCurrentItem() + 1;
        if(nextSlide < slides.length)
            viewPager.setCurrentItem(nextSlide);
        else
        {
            loadHome();
            new PreferanceManager(this).writePreference();
        }
    }
}
