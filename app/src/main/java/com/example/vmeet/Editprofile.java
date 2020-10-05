package com.example.vmeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Editprofile extends AppCompatActivity {
    Button btn_save,btn_cancel;
    EditText Ep_email,Ep_phone,Ep_dept,Ep_staffid,Ep_designation;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent data=getIntent();

        Ep_email=findViewById(R.id.editTextEmailAddress);
        Ep_phone=findViewById(R.id.editTextPhone);
        Ep_dept=findViewById(R.id.editTextDepartment);
        Ep_staffid=findViewById(R.id.editTextStaffID);
        Ep_designation=findViewById(R.id.editTextDesignation);


        Ep_email.setText(data.getStringExtra("email"));
        Ep_phone.setText(data.getStringExtra("phonenumber"));
        Ep_dept.setText(data.getStringExtra("dept"));
        Ep_staffid.setText(data.getStringExtra("staffid"));
        Ep_designation.setText(data.getStringExtra("designation"));

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        user=fAuth.getCurrentUser();



        btn_save=findViewById(R.id.btn_save);
        btn_cancel=findViewById(R.id.btn_cancel);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Ep_email.getText().toString().isEmpty() || Ep_phone.getText().toString().isEmpty() || Ep_dept.getText().toString().isEmpty() ||
                        Ep_staffid.getText().toString().isEmpty() || Ep_designation.getText().toString().isEmpty()) {
                    Toast.makeText(Editprofile.this, "one or many fields are empty", Toast.LENGTH_SHORT).show();
                } else {

                    final String email = Ep_email.getText().toString();
                    user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override

                        public void onSuccess(Void aVoid) {
                            DocumentReference docref = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> edited = new HashMap<>();
                            edited.put("Email", email);
                            edited.put("Phone Number", Ep_phone.getText().toString());
                            edited.put("Staffid", Ep_staffid.getText().toString());
                            edited.put("Department", Ep_dept.getText().toString());
                            edited.put("Designation", Ep_designation.getText().toString());
                            docref.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Editprofile.this, "Profile updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Profile_settings.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Editprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            Toast.makeText(Editprofile.this, "Email is changed ", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Editprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Settings.class);
                startActivity(i);
            }
        });




    }

    }
