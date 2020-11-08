package com.example.vmeet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Login;
    Button btnsubmit;
    FirebaseAuth mAuth;
   // Toolbar toolbar;
    EditText userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);


     //  toolbar = findViewById(R.id.toolbar);

        userEmail=findViewById(R.id.username);
        btnsubmit=findViewById(R.id.btnsubmit);

     //  toolbar.setTitle("ForgetPassword");
        mAuth = FirebaseAuth.getInstance();

        btnsubmit=findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(this);


        Login=findViewById(R.id.Login);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });
    }


    /*
     * ResetPassword:Sending reset password link o user entered email address
     * */
    @Override
    public void onClick(View v) {
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = userEmail.getText().toString();
                if (email.isEmpty()) {
                    userEmail.setError("Email is required");
                    userEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    userEmail.setError("Please enter valid email");
                    userEmail.requestFocus();
                    return;
                }


                mAuth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(ResetActivity.this, "Password Reset Link Sent Successfully", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(ResetActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}