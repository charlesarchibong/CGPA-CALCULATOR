/*
 * Copyright (c) 2019 , Zealnetworks Technologies. All rights reserved. DO NOT ALTER OR REMOVE
 * COPYRIGHT NOTICES OR THIS FILE HEADER. You are not meant to edit or modify this source code unless you are
 * authorized to do so.  Please contact me at contact@zealtech.com.ng or visit www.zealtech.com.ng if you need
 * additional information or have any questions.
 */

package com.zealtech.learning.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.zealtech.learning.dao.CourseDAO;
import com.zealtech.learning.dao.UserDAO;
import com.zealtech.learning.model.Course;
import com.zealtech.learning.model.User;

@Database(entities = {User.class, Course.class}, version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase
{
    public abstract UserDAO getUserDAO();
    public abstract CourseDAO getCourseDAO();
}
