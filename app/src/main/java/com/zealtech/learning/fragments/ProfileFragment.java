/*
 * Copyright (c) 2019 , Zealnetworks Technologies. All rights reserved. DO NOT ALTER OR REMOVE
 * COPYRIGHT NOTICES OR THIS FILE HEADER. You are not meant to edit or modify this source code unless you are
 * authorized to do so.  Please contact me at contact@zealtech.com.ng or visit www.zealtech.com.ng if you need
 * additional information or have any questions.
 */

package com.zealtech.learning.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.example.kamran.calculator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.zealtech.learning.dao.UserDAO;
import com.zealtech.learning.model.User;
import com.zealtech.learning.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
{
    public ImageView profilepic;
    public TextView fullName, department, level, school, email;
    User user;
    FirebaseAuth firebaseAuth;
    public ProfileFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_profile, container, false);
            profilepic = view.findViewById(R.id.uprofilePic);
            firebaseAuth = FirebaseAuth.getInstance();
            Bitmap batmapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.man_placeholder);
            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), batmapBitmap);
            circularBitmapDrawable.setCircular(true);
            profilepic.setImageDrawable(circularBitmapDrawable);
            fullName = view.findViewById(R.id.uFullName);
            department = view.findViewById(R.id.udeparment);
            level = view.findViewById(R.id.ulevel);
            UserDAO userDAO = Constants.userDatabase.getUserDAO();
            user = userDAO.getUserByUid(firebaseAuth.getCurrentUser().getUid());
            school = view.findViewById(R.id.uschool);
            email = view.findViewById(R.id.uemail);
        try
        {
            initUserInfo();

        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
        return view;
    }

    public void initUserInfo()
    {
        fullName.setText(user.getFullName());
        department.setText(user.getDepartment());
        level.setText(user.getLevel());
        school.setText(user.getSchool());
        email.setText(user.getEmail());
    }

    @Override
    public void onStart()
    {
        super.onStart();
        initUserInfo();
        UserDAO userDAO = Constants.userDatabase.getUserDAO();
        user = userDAO.getUserByUid(firebaseAuth.getCurrentUser().getUid());
    }

    @Override
    public void onResume()
    {
        super.onResume();
       initUserInfo();
        UserDAO userDAO = Constants.userDatabase.getUserDAO();
        user = userDAO.getUserByUid(firebaseAuth.getCurrentUser().getUid());
    }
}
