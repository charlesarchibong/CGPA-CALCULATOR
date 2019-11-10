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

import com.zealtech.learning.model.User;

import java.util.List;

@Dao
public interface UserDAO
{
    @Insert
    void insertUser(User... users);
    @Update
    void updateUser(User... users);
    @Delete
    void deleteUser(User... users);
    @Query("SELECT * FROM users")
    List<User> getAllusers();
    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByEmail (String email);
    @Query("SELECT * FROM users WHERE uid = :uid")
    User getUserByUid(String uid);
}
