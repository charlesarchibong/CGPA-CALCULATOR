package com.zealtech.learning.util;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferanceManager
{
    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferanceManager(Context context)
    {
        this.context = context;
        getPreferance();
    }

    private void getPreferance()
    {
        sharedPreferences = context.getSharedPreferences("com.zealtech.learning.MyPref", Context.MODE_PRIVATE);
    }

    public void writePreference()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("com.zealtech.learning.MyPrefKey", "INIT_OK");
        editor.commit();
    }

    public boolean checkPreference()
    {
        boolean status = false;
        if(sharedPreferences.getString("com.zealtech.learning.MyPrefKey", "null").equals("null"))
        {
            status = false;

        }
        else
        {
            status = true;
        }
        return status;
    }

    public  void clearPreference()
    {
        sharedPreferences.edit().clear().commit();
    }
}
