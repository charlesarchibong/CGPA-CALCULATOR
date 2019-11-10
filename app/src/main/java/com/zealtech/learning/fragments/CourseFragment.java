/*
 * Copyright (c) 2019 , Zealnetworks Technologies. All rights reserved. DO NOT ALTER OR REMOVE
 * COPYRIGHT NOTICES OR THIS FILE HEADER. You are not meant to edit or modify this source code unless you are
 * authorized to do so.  Please contact me at contact@zealtech.com.ng or visit www.zealtech.com.ng if you need
 * additional information or have any questions.
 */

package com.zealtech.learning.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.kamran.calculator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.zealtech.learning.activity.EditCourseActivity;
import com.zealtech.learning.dao.CourseDAO;
import com.zealtech.learning.model.Course;
import com.zealtech.learning.model.User;
import com.zealtech.learning.util.AppUtil;
import com.zealtech.learning.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends Fragment
{
    private Spinner selectYear, selectSemester, editCourse, deleteCourse;
    private TextView registerCourseBtn, viewCoursesBtn, saveCourseBtn, creditUnitTableText;
    private EditText mCourseTitle, mCourseCode, mCreditUnit;
    private TableLayout coursesTable, creditUnitTable;
    private String yearSelected, semesterSelected;
    private FirebaseAuth firebaseAuth;

    public CourseFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        this.selectSemester = view.findViewById(R.id.selectSemesterSpinner);
        this.editCourse = view.findViewById(R.id.editCourseSpinner);
        this.selectYear = view.findViewById(R.id.selectYearSpinner);
        this.deleteCourse = view.findViewById(R.id.deleteCourseSpinner);
        this.registerCourseBtn = view.findViewById(R.id.registerCourseBtn);
        this.viewCoursesBtn = view.findViewById(R.id.viewCoursesBtn);
        this.coursesTable = view.findViewById(R.id.courseTable);
        this.creditUnitTableText = view.findViewById(R.id.crditUniiTableText);
        this.creditUnitTable = view.findViewById(R.id.creditUnitTable);
        this.saveCourseBtn = view.findViewById(R.id.saveCourseBtn);
        this.mCourseTitle = view.findViewById(R.id.courseTitleEditText);
        this.mCourseCode = view.findViewById(R.id.courseCodeEditText);
        this.mCreditUnit = view.findViewById(R.id.creditUnitEditText);
        hideRegisterAndViewCourseBtn();
        this.coursesTable.setVisibility(View.GONE);
        this.creditUnitTable.setVisibility(View.GONE);
        this.deleteCourse.setVisibility(View.GONE);
        this.editCourse.setVisibility(View.GONE);
        hideCourseRegistrationDetails();
        this.saveCourseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveCourseBtnClicked();
            }
        });
        this.registerCourseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerCourseBtnClicked();
            }
        });
        this.viewCoursesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                viewCourseBtnClicked();
            }
        });
        ArrayAdapter<CharSequence> semesterArray = ArrayAdapter.createFromResource(view.getContext(), R.array.semesters, R.layout.spinner_item);
        semesterArray.setDropDownViewResource(R.layout.spinner_item_dropdown);
        selectSemester.setAdapter(semesterArray);
        ArrayAdapter<CharSequence> yearArray = ArrayAdapter.createFromResource(view.getContext(), R.array.years, R.layout.spinner_item);
        yearArray.setDropDownViewResource(R.layout.spinner_item_dropdown);
        selectYear.setAdapter(yearArray);
        this.editCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (!parent.getSelectedItem().toString().equals("Edit Course"))
                {
                    Intent intent = new Intent(getContext(), EditCourseActivity.class);
                    intent.putExtra("courseCode", parent.getSelectedItem().toString());
                    intent.putExtra("year", yearSelected);
                    intent.putExtra("semester", semesterSelected);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        this.deleteCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try
                {
                    CourseDAO courseDAO = Constants.userDatabase.getCourseDAO();
                    if (!parent.getSelectedItem().toString().equals("Delete Course"))
                    {
                        Course course = courseDAO.getCourseByCourseCode(yearSelected, semesterSelected, parent.getSelectedItem().toString(), Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                        courseDAO.deleteCourse(course);
                        populateTables(yearSelected, semesterSelected);
                        porpulateCoursesSpinner(yearSelected, semesterSelected);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace(System.err);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        this.selectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                yearSelected = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        this.selectSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (!yearSelected.equals("Select Year"))
                {
                    semesterSelected = parent.getSelectedItem().toString();
                    if (!semesterSelected.equals("Select Semester"))
                    {
                        registerCourseBtn.setVisibility(View.VISIBLE);
                        viewCoursesBtn.setVisibility(View.VISIBLE);
                    } else
                    {
                        Toast.makeText(parent.getContext(), "Please select valid SEMESTER!", Toast.LENGTH_LONG).show();
                        registerCourseBtn.setVisibility(View.GONE);
                        viewCoursesBtn.setVisibility(View.GONE);
                        hideCourseRegistrationDetails();
                        creditUnitTable.setVisibility(View.GONE);
                        coursesTable.setVisibility(View.GONE);
                        deleteCourse.setVisibility(View.GONE);
                        editCourse.setVisibility(View.GONE);
                    }
                } else
                {
                    Toast.makeText(parent.getContext(), "Please select YEAR first before selecting SEMESTER!", Toast.LENGTH_LONG).show();
                    registerCourseBtn.setVisibility(View.GONE);
                    viewCoursesBtn.setVisibility(View.GONE);
                    hideCourseRegistrationDetails();
                    creditUnitTable.setVisibility(View.GONE);
                    coursesTable.setVisibility(View.GONE);
                    deleteCourse.setVisibility(View.GONE);
                    editCourse.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        return view;
    }

    private void saveCourseBtnClicked()
    {
        try
        {
            CourseDAO courseDAO = Constants.userDatabase.getCourseDAO();
            this.saveCourseBtn.setEnabled(false);
            String courseTitle = this.mCourseTitle.getText().toString().trim();
            String courseCode = this.mCourseCode.getText().toString().trim();
            int creditUnit = this.mCreditUnit.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(mCreditUnit.getText().toString().trim());
            if(TextUtils.isEmpty(courseTitle))
                throw new IllegalArgumentException("Course Title can not be empty");
            if(TextUtils.isEmpty(courseCode))
                throw new IllegalArgumentException("Course Code can not be empty");
            if(creditUnit <= 0)
                throw new IllegalArgumentException("Credit Unit can not be empty");
            int totalCreditUnit = AppUtil.getTotalCreditUnits(this.yearSelected, this.semesterSelected);
            if(totalCreditUnit > 24)
            {
                Toast.makeText(getContext(), "Credit Unit max reached for the semester and year", Toast.LENGTH_LONG).show();
            }else if(totalCreditUnit + creditUnit <= 24)
            {
                if(courseDAO.getCourseByCourseCode(this.yearSelected, this.semesterSelected, courseCode, firebaseAuth.getCurrentUser().getUid()) == null)
                {
                    this.saveCourseBtn.setText("Loading...");
                    Course course = new Course();
                    course.setCourseCode(courseCode);
                    course.setCourseTitle(courseTitle);
                    course.setCreditUnit(creditUnit);
                    course.setSemester(semesterSelected);
                    course.setYearOffered(yearSelected);
                    course.setUserEmail(User.currentAppUser.getEmail());
                    course.setUserId(User.currentAppUser.getUid());
                    course.setSynchronize("false");
                    courseDAO.insertCourse(course);
                    this.saveCourseBtn.setText("Save Course");
                    this.saveCourseBtn.setEnabled(true);
                    clearCourseRegostrationField();
                    String message = totalCreditUnit + creditUnit == 24 ? "Course registration is successful and credit unit max reached, you can't register another course for this session"
                            : "Course registration is successful, you can register more course!";
                    new AlertDialog.Builder(getContext())
                            .setIcon(R.drawable.logo)
                            .setTitle("Success")
                            .setMessage(message)
                            .show();
                    this.populateTables(this.yearSelected, this.semesterSelected);
                    this.coursesTable.setVisibility(View.VISIBLE);
                    this.creditUnitTable.setVisibility(View.VISIBLE);
                }
                else{
                    this.saveCourseBtn.setEnabled(true);
                    Toast.makeText(getContext(), "Course Already Exist!!!", Toast.LENGTH_LONG).show();
                }
            }
            else {
                this.saveCourseBtn.setEnabled(true);
                Toast.makeText(getContext(), "You can't register another course, credit unit > 24", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception xcp)
        {
            this.saveCourseBtn.setEnabled(true);
            if(xcp instanceof NumberFormatException)
                Toast.makeText(getContext(), "Credit Unit should be an integer!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getContext(), xcp.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void registerCourseBtnClicked()
    {
        if (!this.semesterSelected.equals("Select Semester") && !this.yearSelected.equals("Select Year"))
        {
            this.showRegisterAndViewCourseBtn();
            this.showCourseRegistrationDetails();
            this.creditUnitTable.setVisibility(View.GONE);
            this.coursesTable.setVisibility(View.GONE);
            this.deleteCourse.setVisibility(View.GONE);
            this.editCourse.setVisibility(View.GONE);
        } else
        {
            Toast.makeText(getContext(), "Please select valid SEMESTER and YEAR!", Toast.LENGTH_LONG).show();
            this.hideRegisterAndViewCourseBtn();
            this.hideCourseRegistrationDetails();
            this.creditUnitTable.setVisibility(View.GONE);
            this.coursesTable.setVisibility(View.GONE);
            this.deleteCourse.setVisibility(View.GONE);
            this.editCourse.setVisibility(View.GONE);
        }
    }

    private void populateTables(String year, String semester)
    {
        try
        {
            this.creditUnitTableText.setText("0");
            this.clearCoursesTable();
            CourseDAO courseDAO = Constants.userDatabase.getCourseDAO();
            List<Course> courses = courseDAO.getCourses(year, semester, Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
            if (courses.size() > 0)
            {
                TableRow.LayoutParams param = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
                for (Course course : courses)
                {
                    TableRow row = new TableRow(getContext());
                    customfonts.MyTextView courseTitle = new customfonts.MyTextView(getContext());
                    courseTitle.setText(course.getCourseTitle());
                    courseTitle.setLayoutParams(param);
                    courseTitle.setTextColor(Color.parseColor("#0B0B0C"));
                    customfonts.MyTextView creditUnit = new customfonts.MyTextView(getContext());
                    creditUnit.setText(String.valueOf(course.getCreditUnit()));
                    creditUnit.setLayoutParams(param);
                    creditUnit.setTextColor(Color.parseColor("#0B0B0C"));
                    row.addView(courseTitle);
                    row.addView(creditUnit);
                    this.coursesTable.addView(row);
                }
                this.creditUnitTableText.setText(String.valueOf(AppUtil.getTotalCreditUnits(yearSelected, semesterSelected)));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }

    private void viewCourseBtnClicked()
    {
        try
        {
            if (!semesterSelected.equals("Select Semester") && !yearSelected.equals("Select Year"))
            {
                this.showRegisterAndViewCourseBtn();
                this.hideCourseRegistrationDetails();
                this.populateTables(yearSelected, semesterSelected);
                this.coursesTable.setVisibility(View.VISIBLE);
                this.creditUnitTable.setVisibility(View.VISIBLE);
                this.porpulateCoursesSpinner(yearSelected, semesterSelected);
                this.deleteCourse.setVisibility(View.VISIBLE);
                this.editCourse.setVisibility(View.VISIBLE);
            } else
            {
                Toast.makeText(getContext(), "Please select valid SEMESTER and YEAR!", Toast.LENGTH_LONG).show();
                this.hideRegisterAndViewCourseBtn();
                this.hideCourseRegistrationDetails();
                this.coursesTable.setVisibility(View.GONE);
                this.creditUnitTable.setVisibility(View.GONE);
                this.deleteCourse.setVisibility(View.GONE);
                this.editCourse.setVisibility(View.GONE);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }

    private void porpulateCoursesSpinner(String year, String semester)
    {
        try
        {
            ArrayList<String> editCourseCode = new ArrayList<>();
            ArrayList<String> deleteCourseCode = new ArrayList<>();
            CourseDAO courseDAO = Constants.userDatabase.getCourseDAO();
            List<Course> courses = courseDAO.getCourses(year, semester, Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
            editCourseCode.add("Edit Course");
            deleteCourseCode.add("Delete Course");
            for (Course course : courses)
            {
                editCourseCode.add(course.getCourseCode());
                deleteCourseCode.add(course.getCourseCode());
            }
            ArrayAdapter<String> editSpinners = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.spinner_item, editCourseCode);
            ArrayAdapter<String> deleteSpinners = new ArrayAdapter<>(getContext(), R.layout.spinner_item, deleteCourseCode);
            editSpinners.setDropDownViewResource(R.layout.spinner_item_dropdown);
            deleteSpinners.setDropDownViewResource(R.layout.spinner_item_dropdown);
            this.editCourse.setAdapter(editSpinners);
            this.deleteCourse.setAdapter(deleteSpinners);
        }
        catch (Exception xcp)
        {
            xcp.printStackTrace(System.err);
        }
    }



    private void clearCoursesTable()
    {
        int count = this.coursesTable.getChildCount();
        for (int i = 1; i <= count; i++) {
            View child = this.coursesTable.getChildAt(i);
            if (child instanceof TableRow)
                ((ViewGroup) child).removeAllViews();
        }
    }
    private void hideRegisterAndViewCourseBtn()
    {
        this.registerCourseBtn.setVisibility(View.GONE);
        this.viewCoursesBtn.setVisibility(View.GONE);
    }
    private void showRegisterAndViewCourseBtn()
    {
        this.registerCourseBtn.setVisibility(View.VISIBLE);
        this.viewCoursesBtn.setVisibility(View.VISIBLE);
    }
    private void hideCourseRegistrationDetails()
    {
        this.saveCourseBtn.setVisibility(View.GONE);
        this.mCourseTitle.setVisibility(View.GONE);
        this.mCreditUnit.setVisibility(View.GONE);
        this.mCourseCode.setVisibility(View.GONE);
    }
    private void showCourseRegistrationDetails()
    {
        this.saveCourseBtn.setVisibility(View.VISIBLE);
        this.mCourseTitle.setVisibility(View.VISIBLE);
        this.mCreditUnit.setVisibility(View.VISIBLE);
        this.mCourseCode.setVisibility(View.VISIBLE);
    }

    private void clearCourseRegostrationField()
    {
        this.mCreditUnit.getText().clear();
        this.mCourseCode.getText().clear();
        this.mCourseTitle.getText().clear();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.porpulateCoursesSpinner(this.yearSelected, this.semesterSelected);
        this.populateTables(this.yearSelected, this.semesterSelected);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        this.porpulateCoursesSpinner(this.yearSelected, this.semesterSelected);
        populateTables(this.yearSelected, this.semesterSelected);
        populateTables(this.yearSelected, this.semesterSelected);
    }
}
