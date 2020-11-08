package com.example.vmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Request Maintenance");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String RoomNo = edRoomNumber.getText().toString();
                final String ReqType = spinnerReqType.getSelectedItem().toString();
                final String Desc = edDescription.getText().toString();
                String username = mFirebaseAuth.getCurrentUser().getDisplayName();
                String userEmail = mFirebaseAuth.getCurrentUser().getEmail();
                userID = mFirebaseAuth.getCurrentUser().getUid();

                Map<String, Object> user = new HashMap<>();
                user.put("Room Number", RoomNo);
                user.put("Request Type", ReqType);
                user.put("Issue Description", Desc);
                user.put("Status", "Pending");
                user.put("Username", username);
                user.put("UserEmail", userEmail);
                user.put("created_By",userID);
                db.collection("Service Requests").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "New Maintenance Service Request has been created. Redirecting to Homepage..", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Homepage.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error while booking the request. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
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
//        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, Homepage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}