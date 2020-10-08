package com.example.vmeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RequestMaintenance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String TAG = "TAG";
    Button btnSubmit;
    EditText edRoomNumber, edDescription;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_maintenance);
        db = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        btnSubmit = findViewById(R.id.buttonSubmit);
        edRoomNumber = findViewById(R.id.editTextRoomNumber);
        edDescription = findViewById(R.id.editTextDescription);
        final Spinner spinnerReqType = findViewById(R.id.spinnerReqType);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String RoomNo = edRoomNumber.getText().toString();
                final String ReqType = spinnerReqType.getSelectedItem().toString();
                final String Desc = edDescription.getText().toString();
                String  username = mFirebaseAuth.getCurrentUser().getDisplayName();
                String userEmail = mFirebaseAuth.getCurrentUser().getEmail();
                userID = mFirebaseAuth.getCurrentUser().getUid();
                DocumentReference documentReference = db.collection("Service Requests").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("Room Number", RoomNo);
                user.put("Request Type", ReqType);
                user.put("Issue Description", Desc);
                user.put("Status", "pending");
                user.put("Username", username);
                user.put("UserEmail", userEmail);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Service request added for" + userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding request", e);
                    }
                });

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerReqType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReqType.setAdapter(adapter);
        spinnerReqType.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}