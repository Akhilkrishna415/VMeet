package com.example.vmeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button btnfeedback,btnratenow;
    EditText et_feedback;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        db = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        btnfeedback = findViewById(R.id.btnsubmit);
        et_feedback = findViewById(R.id.edittextfeedback);
        btnratenow=findViewById(R.id.btnGet);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        btnratenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Feedback.this, "Thankyou for your rating!", Toast.LENGTH_SHORT).show();
            }
        });


        btnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String feedback = et_feedback.getText().toString();
                String username = mFirebaseAuth.getCurrentUser().getDisplayName();
                String userEmail = mFirebaseAuth.getCurrentUser().getEmail();
                UserID = mFirebaseAuth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("Feedback").document(UserID);
                Map<String, Object> user = new HashMap<>();
                user.put("User Feedback", feedback);
                user.put("Username", username);
                user.put("UserEmail", userEmail);
                user.put("User ID",UserID);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Feedback added for" + UserID);
                        Toast.makeText(Feedback.this, "Thankyou for your valuable feedback!", Toast.LENGTH_SHORT).show();
                        Intent i =new Intent(getApplicationContext(),Settings.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding feedback", e);
                    }
                });


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}