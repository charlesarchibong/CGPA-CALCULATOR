package com.zealtech.learning.activity;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuth;
import com.zealtech.learning.util.AppUtil;

public class ForgetPassword extends AppCompatActivity
{
    private TextView forgetBtn;
    private EditText emailForget;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private ImageView moveUp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_forget_password);
        forgetBtn = findViewById(R.id.forgetBtn);
        emailForget = findViewById(R.id.emailForget);
        progressBar = findViewById(R.id.forgetPasswordBar);
        moveUp = findViewById(R.id.moveUp);
        firebaseAuth = FirebaseAuth.getInstance();
        final AlertDialog dialog = AppUtil.getAlertView(this);
        moveUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ForgetPassword.super.onBackPressed();
            }
        });
        forgetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean status = true;
                forgetBtn.setEnabled(false);
                forgetBtn.setText("Resetting...");
                String email = emailForget.getText().toString().trim();
                if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    status = false;
                    forgetBtn.setEnabled(true);
                    forgetBtn.setText("Reset Password");
                    Toast.makeText(ForgetPassword.this, "Enter a registered email address", Toast.LENGTH_LONG)
                            .show();
                }
                if(status)
                {
                    dialog.show();
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(ForgetPassword.this, new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        forgetBtn.setEnabled(false);
                                        forgetBtn.setText("Done!");
                                        dialog.dismiss();
                                        new AlertDialog.Builder(ForgetPassword.this)
                                                .setTitle("Success!")
                                                .setMessage("We have sent a password reset email, check your inbox for instructions.")
                                                .setIcon(R.drawable.logo)
                                                .show();
                                    } else
                                    {

                                        forgetBtn.setEnabled(true);
                                        forgetBtn.setText("Reset Password");
                                        dialog.dismiss();
                                        new AlertDialog.Builder(ForgetPassword.this)
                                                .setTitle("Error!")
                                                .setMessage(task.getException().getLocalizedMessage())
                                                .setIcon(R.drawable.logo)
                                                .show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
