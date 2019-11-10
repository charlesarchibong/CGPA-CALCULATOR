/*
 * Copyright (c) 2019 , Zealnetworks Technologies. All rights reserved. DO NOT ALTER OR REMOVE
 * COPYRIGHT NOTICES OR THIS FILE HEADER. You are not meant to edit or modify this source code unless you are
 * authorized to do so.  Please contact me at contact@zealtech.com.ng or visit www.zealtech.com.ng if you need
 * additional information or have any questions.
 */

package com.zealtech.learning.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zealtech.learning.model.Course;

import java.util.List;

@Dao
public interface CourseDAO
{
    @Insert
    void insertCourse(Course... courses);
    @Update
    void updateCourse(Course... courses);
    @Delete
    void deleteCourse(Course... courses);
    @Query("SELECT * FROM courses WHERE yearOffered = :year AND semester = :semester AND userId = :uId")
    List<Course> getCourses(String year, String semester, String uId);
    @Query("SELECT * FROM courses WHERE userId = :uId")
    List<Course> getCourses(String uId);
    @Query("SELECT * FROM courses WHERE yearOffered = :year AND semester = :semester AND courseCode = :courseCode AND userId = :uId")
    Course getCourseByCourseCode(String year, String semester, String courseCode, String uId);
}
