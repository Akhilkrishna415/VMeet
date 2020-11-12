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
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * This class manages user SignUp
 */

public class SignUp extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextView userExist;
    Button btnReg;
    EditText etemail, etpwd, etphone, etstaffid, etdpart, etdesign, etName;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userExist = findViewById(R.id.ExistingUser);
        btnReg = findViewById(R.id.buttonRegister);
        etemail = findViewById(R.id.editTextEmailAddress);
        etpwd = findViewById(R.id.editTextPassword);
        etphone = findViewById(R.id.editTextPhone);
        etstaffid = findViewById(R.id.editTextStaffID);
        etdpart = findViewById(R.id.editTextDepartment);
        etdesign = findViewById(R.id.editTextDesignation);
        etName = findViewById(R.id.editTextName);
        fstore = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Registration");
        /*Toolbar configuration and back button End */

        /*
         * Method to signup functionality takes the parameters from the xml
         * and created a record in Firestore Users table
         * */

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etemail.getText().toString();
                final String pwd = etpwd.getText().toString();
                final String phone = etphone.getText().toString();
                final String staffID = etstaffid.getText().toString();
                final String depart = etdpart.getText().toString();
                final String design = etdesign.getText().toString();
                final String name = etName.getText().toString();
                if (email.isEmpty()) {
                    etemail.setError("Please enter the email ID");
                    etemail.requestFocus();
                } else if (pwd.isEmpty() && pwd.length() < 6) {
                    etpwd.setError("Please enter the password with more than 6 characters");
                    etpwd.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty() && phone.isEmpty() && staffID.isEmpty() && depart.isEmpty() && design.isEmpty()) {
                    Toast.makeText(SignUp.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!email.isEmpty() && !pwd.isEmpty()) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "SignUp Unsuccessful, PLease try again using another email!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUp.this, "Registration Successful. You will be redirected to login page now.", Toast.LENGTH_SHORT).show();
                                userID = mFirebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("Users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Email", email);
                                user.put("Name", name);
                                user.put("Staffid", staffID);
                                user.put("Phone Number", phone);
                                user.put("Department", depart);
                                user.put("Designation", design);
                                user.put("profile_img_url","gs://vmeet-2fd0d.appspot.com/Users/defaultprofileimg.png");
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user profile is created for" + userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                                startActivity(new Intent(SignUp.this, Login.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUp.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        userExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });
    }
}