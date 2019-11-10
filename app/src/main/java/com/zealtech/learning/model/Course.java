/*
 * Copyright (c) 2019 , Zealnetworks Technologies. All rights reserved. DO NOT ALTER OR REMOVE
 * COPYRIGHT NOTICES OR THIS FILE HEADER. You are not meant to edit or modify this source code unless you are
 * authorized to do so.  Please contact me at contact@zealtech.com.ng or visit www.zealtech.com.ng if you need
 * additional information or have any questions.
 */

package com.zealtech.learning.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course
{
    @PrimaryKey(autoGenerate = true)
    private int sn;
    private String userId;
    private String userEmail;
    private String semester;
    private String yearOffered;
    private String courseCode;
    private String courseTitle;
    private int creditUnit;
    private String synchronize;

    public String getSynchronize()
    {
        return synchronize;
    }

    public void setSynchronize(String synchronize)
    {
        this.synchronize = synchronize;
    }

    public int getSn()
    {
        return sn;
    }

    public void setSn(int sn)
    {
        this.sn = sn;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getSemester()
    {
        return semester;
    }

    public void setSemester(String semester)
    {
        this.semester = semester;
    }

    public String getYearOffered()
    {
        return yearOffered;
    }

    public void setYearOffered(String yearOffered)
    {
        this.yearOffered = yearOffered;
    }

    public String getCourseCode()
    {
        return courseCode;
    }

    public void setCourseCode(String courseCode)
    {
        this.courseCode = courseCode;
    }

    public String getCourseTitle()
    {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle)
    {
        this.courseTitle = courseTitle;
    }

    public int getCreditUnit()
    {
        return creditUnit;
    }

    public void setCreditUnit(int creditUnit)
    {
        this.creditUnit = creditUnit;
    }

}
