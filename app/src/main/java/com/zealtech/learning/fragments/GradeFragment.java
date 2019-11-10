/*
 * Copyright (c) 2019 , Zealnetworks Technologies. All rights reserved. DO NOT ALTER OR REMOVE
 * COPYRIGHT NOTICES OR THIS FILE HEADER. You are not meant to edit or modify this source code unless you are
 * authorized to do so.  Please contact me at contact@zealtech.com.ng or visit www.zealtech.com.ng if you need
 * additional information or have any questions.
 */

package com.zealtech.learning.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.kamran.calculator.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradeFragment extends Fragment
{


    public GradeFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grade, container, false);
    }

}
