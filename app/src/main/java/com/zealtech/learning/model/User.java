package com.zealtech.learning.model;

public class User
{
    private String email;
    private String username;
    private String fullName;
    private String school;
    private String department;
    private String level;

    public static final String FIRST_YEAR = "100 Level";
    public static final String SECOND_YEAR = "200 Level";
    public static final String THIRD_YEAR = "300 Level";
    public static final String FOURTH_YEAR = "400 Level";
    public static final String FIFTH_YEAR = "500 Level";
    public static final String GRADUATE = "Graduated";

    public User()
    {
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }
}
