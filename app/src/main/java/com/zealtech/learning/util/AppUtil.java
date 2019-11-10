package com.zealtech.learning.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

import com.example.kamran.calculator.R;
import com.google.firebase.auth.FirebaseAuth;
import com.zealtech.learning.dao.CourseDAO;
import com.zealtech.learning.model.Course;

import java.util.List;
import java.util.Objects;

public class AppUtil
{
    private static FirebaseAuth firebaseAuth;
    public static AlertDialog getAlertView(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.loading_progress_bar);
        builder.setCancelable(false);
        AlertDialog progressbar = builder.create();
        return progressbar;
    }

    public static boolean isConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo  networkInfo = null;
        if (!(connectivityManager == null))
            networkInfo = connectivityManager.getActiveNetworkInfo();
        return(networkInfo != null && networkInfo.isConnected());
    }

    public static int getTotalCreditUnits(String year, String semester)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        int size = 0;
        CourseDAO courseDAO = Constants.userDatabase.getCourseDAO();
        List<Course> courses = courseDAO.getCourses(year, semester, Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
        if(courses.size() <= 0)
            return 0;
        else
            {
            for(Course course: courses)
            {
                size += course.getCreditUnit();
            }
        }
        return size;
    }
}
