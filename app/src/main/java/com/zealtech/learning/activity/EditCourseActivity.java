/*
 * Copyright (c) 2019 , Zealnetworks Technologies. All rights reserved. DO NOT ALTER OR REMOVE
 * COPYRIGHT NOTICES OR THIS FILE HEADER. You are not meant to edit or modify this source code unless you are
 * authorized to do so.  Please contact me at contact@zealtech.com.ng or visit www.zealtech.com.ng if you need
 * additional information or have any questions.
 */

package com.zealtech.learning.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.kamran.calculator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.zealtech.learning.database.UserDatabase;
import com.zealtech.learning.model.Course;
import com.zealtech.learning.util.AppUtil;
import com.zealtech.learning.util.Constants;

import java.util.Objects;


public class EditCourseActivity extends AppCompatActivity
{

    private EditText courseTitle, mCourseCode, creditUnit;
    private TextView updateBtn;
    String courseCode;
    String year;
    String semester;
    Course course;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        getSupportActionBar().setTitle("Edit Course");
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.courseTitle = findViewById(R.id.courseTitleUpdate);
        this.mCourseCode = findViewById(R.id.courseCodeUpdate);
        this.creditUnit = findViewById(R.id.creditUnitUpdate);
        this.updateBtn = findViewById(R.id.updateCourseBtn);
        Constants.userDatabase = Room.databaseBuilder(this, UserDatabase.class, Constants.DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        this.initEditText(savedInstanceState);
        this.updateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateCourse();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    private void initEditText(Bundle savedInstanceState)
    {
        try
        {
            if (savedInstanceState == null)
            {
                Bundle extras = getIntent().getExtras();
                if (extras == null)
                {
                    this.courseCode = null;
                    this.year = null;
                    this.semester = null;
                } else
                {
                    this.courseCode = extras.getString("courseCode");
                    this.year = extras.getString("year");
                    this.semester = extras.getString("semester");
                }
            } else
            {
                this.courseCode = (String) savedInstanceState.getSerializable("courseCode");
                this.year = (String) savedInstanceState.getSerializable("year");
                this.semester = (String) savedInstanceState.getSerializable("semester");
            }
            if (this.courseCode != null && this.year != null && this.semester != null)
            {
                this.course = Constants.userDatabase.getCourseDAO().getCourseByCourseCode(year, semester, courseCode, Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                this.courseTitle.setText(course.getCourseTitle());
                this.mCourseCode.setText(course.getCourseCode());
                this.creditUnit.setText(String.valueOf(course.getCreditUnit()));
            } else
            {
                course = null;
            }
        } catch (Exception e)
        {
            super.onBackPressed();
        }
    }

    private void updateCourse()
    {
        try
        {
            if (course != null)
            {
                this.updateBtn.setEnabled(false);
                String newCourseTitle = this.courseTitle.getText().toString().trim();
                String newCourseCode = this.mCourseCode.getText().toString().trim();
                int newCreditUnit = this.creditUnit.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(creditUnit.getText().toString().trim());
                if (TextUtils.isEmpty(newCourseTitle))
                    throw new IllegalArgumentException("Course Title can not be empty");
                else if (TextUtils.isEmpty(newCourseCode))
                    throw new IllegalArgumentException("Course Code can not be empty");
                else if (newCreditUnit <= 0)
                    throw new IllegalArgumentException("Credit Unit can not be empty");
                else if (!newCourseTitle.equals(course.getCourseTitle()) || !newCourseCode.equals(course.getCourseCode()) || newCreditUnit != course.getCreditUnit())
                {
                    int totalCreditUnit = AppUtil.getTotalCreditUnits(this.year, this.semester) - course.getCreditUnit();
                    if (totalCreditUnit > 24)
                    {
                        throw new IllegalArgumentException("Credit Unit max reached for the semester and year");
                    } else if (totalCreditUnit + newCreditUnit <= 24)
                    {
                        this.course.setCourseTitle(newCourseTitle);
                        this.course.setCourseCode(newCourseCode);
                        this.course.setCreditUnit(newCreditUnit);
                        Constants.userDatabase.getCourseDAO().updateCourse(this.course);
                        String message = totalCreditUnit + newCreditUnit == 24 ? "Course updated successfully and credit unit max reached, you can't increase credit unit for this session"
                                : "Course updated successfully, you can increase credit unit for this session";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        this.updateBtn.setEnabled(true);
                        EditCourseActivity.super.onBackPressed();
                    } else {
                        this.updateBtn.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "You can't register another course, credit unit > 24", Toast.LENGTH_LONG).show();
                    }
                } else
                {
                    throw new IllegalArgumentException("Not changes made!");
                }
            }
        }
         catch(Exception e)
            {
                this.updateBtn.setEnabled(true);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
