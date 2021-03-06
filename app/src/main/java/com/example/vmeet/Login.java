package com.example.vmeet;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * This class manages user authentication
 */

public class Login extends AppCompatActivity {
    Button btnlogin;
    public static final String TAG = "TAG";
    TextView goToRegister, forgotPassword;
    EditText email, password;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnlogin = findViewById(R.id.login);
        goToRegister = findViewById(R.id.gotoRegister);
        forgotPassword = findViewById(R.id.ForgetPass);
        mAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

                if (mFirebaseUser != null && !mFirebaseUser.getEmail().equalsIgnoreCase("vmeetadmin@gmail.com")) {
                    Toast.makeText(Login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, Homepage.class);
                    startActivity(i);
                }
                if (mFirebaseUser != null && mFirebaseUser.getEmail().equalsIgnoreCase("vmeetadmin@gmail.com")) {
//                    Toast.makeText(Login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, AdminHome.class);
                    startActivity(i);
                } else {
//                    Toast.makeText(Login.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        //Login Button:Checks the username and paswod from database and redirects to home page

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText, pass;
                emailText = email.getText().toString();
                pass = password.getText().toString();
                if (emailText.isEmpty()){
                    email.setError("Please enter the email ID");
                    email.requestFocus();
                } else if (pass.isEmpty()){
                    password.setError("Please enter the password");
                    password.requestFocus();
                } else if (emailText.isEmpty() && pass.isEmpty()){
                    Toast.makeText(Login.this,"Fields are Empty!",Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(emailText, pass)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "signInWithEmail:success");
                                        Toast.makeText(Login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String userEmail = mAuth.getCurrentUser().getEmail();
                                        Toast.makeText(Login.this, "You are logged in" + userEmail, Toast.LENGTH_SHORT).show();
                                        Intent i;
                                        if (userEmail.equalsIgnoreCase("vmeetAdmin@gmail.com")) {
                                            i = new Intent(getApplicationContext(), AdminHome.class);
                                        } else {
                                            i = new Intent(getApplicationContext(), Homepage.class);
                                        }
                                        startActivity(i);
//
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
//
                                    }
                                }
                            });
                }
            }
        });
        //Redirects to Signup Activity
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
            }
        });

        //Redirects to Reset Activity
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ResetActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth != null)
            mAuth.addAuthStateListener(mAuthStateListener);
    }

}