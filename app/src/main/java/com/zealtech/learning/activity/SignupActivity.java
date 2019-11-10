package com.zealtech.learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kamran.calculator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.zealtech.learning.dao.UserDAO;
import com.zealtech.learning.model.User;
import com.zealtech.learning.util.AppUtil;
import com.zealtech.learning.util.Constants;

public class SignupActivity extends AppCompatActivity
{
    private ImageView sback;
    private FirebaseAuth firebaseAuth;
    private EditText fname, mail, school, department, level, password;
    private TextView sinUp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(this, DashboardHome.class));
            finish();
        }
        sback = findViewById(R.id.sback);
        sback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(it);

            }
        });
        fname = findViewById(R.id.fname);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        school = findViewById(R.id.schoolName);
        department = findViewById(R.id.department);
        level = findViewById(R.id.levelEdu);
        sinUp = findViewById(R.id.sinUp);
        sinUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signUpUser();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    public void signUpUser()
    {
        boolean error = false;
        final String fullName = fname.getText().toString().trim();
        final String email = mail.getText().toString().trim();
        final String schooName = school.getText().toString().trim();
        final String dept = department.getText().toString().trim();
        final String levelEdu = level.getText().toString().trim();
        String passWord = password.getText().toString().trim();
        if (TextUtils.isEmpty(fullName))
        {
            Toast.makeText(SignupActivity.this, "Full Name can not be empty", Toast.LENGTH_LONG)
                    .show();
            error = true;
        } else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(SignupActivity.this, "Email can not be empty", Toast.LENGTH_LONG)
                    .show();
            error = true;
        } else if (TextUtils.isEmpty(schooName))
        {
            Toast.makeText(SignupActivity.this, "School can not be empty", Toast.LENGTH_LONG)
                    .show();
            error = true;
        } else if (TextUtils.isEmpty(passWord))
        {
            Toast.makeText(SignupActivity.this, "Password can not be empty", Toast.LENGTH_LONG)
                    .show();
            error = true;
        } else if (passWord.length() <= 6)
        {
            Toast.makeText(SignupActivity.this, "Password must contain at least 7 characters", Toast.LENGTH_LONG)
                    .show();
            error = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(SignupActivity.this, "Please enter a correct email address", Toast.LENGTH_LONG)
                    .show();
            error = true;
        }

        if (!error)
        {
            if (!AppUtil.isConnected(getApplicationContext()))
            {
                Toast.makeText(this, "Please Connected to the internet", Toast.LENGTH_LONG).show();
            } else
            {
                final AlertDialog progressbar = AppUtil.getAlertView(this);
                progressbar.show();
                sinUp.setEnabled(false);
                sinUp.setText("Loading....");
                firebaseAuth.createUserWithEmailAndPassword(email, passWord)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    progressbar.dismiss();
                                    sinUp.setText("Success!");
                                    Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    User user = new User();
                                    user.setDepartment(dept);
                                    user.setEmail(email);
                                    user.setFullName(fullName);
                                    user.setSchool(schooName);
                                    user.setSynchronize("false");
                                    user.setLevel(levelEdu);
                                    user.setUid(firebaseAuth.getCurrentUser().getUid());
                                    UserDAO userDAO = Constants.userDatabase.getUserDAO();
                                    userDAO.insertUser(user);
                                    User.currentAppUser = user;
                                    startActivity(new Intent(getApplicationContext(), DashboardHome.class));
                                    finish();
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                } else
                                {
                                    progressbar.dismiss();
                                    sinUp.setText("Sign Up");
                                    sinUp.setEnabled(true);
                                    Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    public static void userSignUp()
    {

    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
