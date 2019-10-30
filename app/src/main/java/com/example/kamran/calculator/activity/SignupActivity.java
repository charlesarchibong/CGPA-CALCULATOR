package com.example.kamran.calculator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.kamran.calculator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity
{
    private ImageView sback;
    private FirebaseAuth firebaseAuth;
    private EditText fname, mail, username, password;
    private TextView sinUp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();
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
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
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

    public void signUpUser()
    {
        boolean error = true;
        String fullName = fname.getText().toString().trim();
        String email = mail.getText().toString().trim();
        String userName = username.getText().toString().trim();
        String passWord = password.getText().toString().trim();
        if (TextUtils.isEmpty(fullName))
        {
            Toast.makeText(SignupActivity.this, "Full Name can not be empty", Toast.LENGTH_LONG)
                    .show();
            error = false;
        } else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(SignupActivity.this, "Email can not be empty", Toast.LENGTH_LONG)
                    .show();
            error = false;
        } else if (TextUtils.isEmpty(userName))
        {
            Toast.makeText(SignupActivity.this, "Username can not be empty", Toast.LENGTH_LONG)
                    .show();
            error = false;
        } else if (TextUtils.isEmpty(passWord))
        {
            Toast.makeText(SignupActivity.this, "Password can not be empty", Toast.LENGTH_LONG)
                    .show();
            error = false;
        } else if (passWord.length() <= 6)
        {
            Toast.makeText(SignupActivity.this, "Password must contain at least 7 characters", Toast.LENGTH_LONG)
                    .show();
            error = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(SignupActivity.this, "Please enter a correct email address", Toast.LENGTH_LONG)
                    .show();
            error = false;
        }

        if (error)
        {
            sinUp.setEnabled(false);
            sinUp.setText("Please wait....");
//            progressBar.se
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email, passWord)
                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);
                                sinUp.setText("Success!");
                                Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            } else
                            {
                                progressBar.setVisibility(View.GONE);
                                sinUp.setEnabled(true);
                                sinUp.setText("Sign Up");
                                Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            progressBar.setVisibility(View.GONE);
                            Log.i(SignupActivity.class.getName(), e.getLocalizedMessage());
                            sinUp.setEnabled(true);
                            sinUp.setText("Sign Up");
                            Toast.makeText(SignupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG);
                        }
                    });
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
