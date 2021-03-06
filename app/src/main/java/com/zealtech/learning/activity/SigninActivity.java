package com.zealtech.learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kamran.calculator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.zealtech.learning.dao.UserDAO;
import com.zealtech.learning.model.User;
import com.zealtech.learning.util.AppUtil;
import com.zealtech.learning.util.Constants;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView sback;
    private TextView forgetPassword, loginBtn;
    private EditText email, password;
    private TextInputLayout emailLayout, passwordLayout;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private ProgressBar loginProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_signin);
        sback = findViewById(R.id.sinb);
        loginBtn = findViewById(R.id.loginBtn);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        sback.setOnClickListener(this);
        forgetPassword = findViewById(R.id.forgetAcct);
        emailLayout = findViewById(R.id.emailLoginLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        passwordLayout = findViewById(R.id.passwordLoginLayout);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        forgetPassword.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        authStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if (firebaseAuth.getCurrentUser() != null)
                {
                    Intent intent = new Intent(getApplicationContext(), DashboardHome.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            }
        };
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.sinb:
            {
                SigninActivity.super.onBackPressed();
            } break;
            case R.id.loginBtn:
            {
                loginUserWithEmailAndPassword();
            } break;
            case R.id.forgetAcct:
            {
                Intent it = new Intent(SigninActivity.this, ForgetPassword.class);
                startActivity(it);
            } break;
        }


    }

    private void loginUserWithEmailAndPassword()
    {
        boolean status = true;
        loginBtn.setEnabled(false);
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
        {
            emailLayout.setError("Please enter a valid email");
            status = false;
            loginBtn.setEnabled(true);
        } else
        {
            status = true;
            emailLayout.setErrorEnabled(false);
        }
        if (userEmail.isEmpty())
        {
            emailLayout.setError("Email can not be empty");
            status = false;
            loginBtn.setEnabled(true);
        } else
        {
            status = true;
            emailLayout.setErrorEnabled(false);
        }
        if (userPassword.isEmpty())
        {
            passwordLayout.setError("Password can not be empty");
            status = false;
            loginBtn.setEnabled(true);
        } else
        {
            status = true;
            passwordLayout.setErrorEnabled(false);
        }
        if (status)
        {
            if (!AppUtil.isConnected(getApplicationContext()))
            {
                Toast.makeText(this, "Please Connected to the internet", Toast.LENGTH_LONG).show();
            } else
            {
                loginBtn.setEnabled(false);
                loginBtn.setText("Please wait...");
                final AlertDialog dialog = AppUtil.getAlertView(this);
                dialog.show();
                firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    dialog.dismiss();
                                    loginBtn.setText("Success!");
                                    UserDAO userDAO = Constants.userDatabase.getUserDAO();
                                    User user = userDAO.getUserByUid(firebaseAuth.getCurrentUser().getUid());
                                    User.currentAppUser = user;
                                } else
                                {
                                    dialog.dismiss();
                                    loginBtn.setEnabled(true);
                                    loginBtn.setText("Sign In");
                                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });
            }
        }

    }
}
