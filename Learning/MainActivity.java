package com.zealtech.learning;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                return itemSelected(item);
            }
        });
        toolbar.setTitle("CGPA Calculator");
        Button newIntent = findViewById(R.id.new_intent);
        newIntent.setEnabled(true);
        newIntent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               openLoadActivity();
            }
        });
    }

    public void openLoadActivity()
    {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(MainActivity.this, LoadActivity.class);
        startActivity(intent, options.toBundle());
    }

    public boolean itemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case (R.id.action_settings):
            {
                return true;
            }
            case (R.id.action_exit):
            {
                finish();
                System.exit(1);
            } break;
        }

        return super.onOptionsItemSelected(item);
    }
}
