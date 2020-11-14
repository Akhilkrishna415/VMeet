package com.example.vmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * using this class, admin can update the status of the service requests made by the user
 */
public class ServiceReqStatus extends AppCompatActivity {

    Button btnupdate;
    FirebaseFirestore db;
    TextView TVdesc, TVreqtype, TVroomnumber, TVuseremail, TVusername;
    Spinner spinStatus;
    private FirebaseUser curUser;
    private FirebaseAuth auth;
    String[] progress = new String[]{"Pending", "In Progress", "Completed"};

    @Override

    /**
     * this method is the main which triggers when you called this activity
     * @param savedInstanceState : bundle object
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_req_status);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Request Status");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        TVdesc = findViewById(R.id.DisplayDesc);
        TVreqtype = findViewById(R.id.DisplayReq);
        TVroomnumber = findViewById(R.id.DisplayRoomNo);
        TVuseremail = findViewById(R.id.DisplayUserEmail);
//        TVusername = findViewById(R.id.DisplayUserName);
        btnupdate = findViewById(R.id.ButtonUpdate);
        spinStatus = findViewById(R.id.SpinnerReqStatus);


//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerReqStatus, android.R.layout.simple_spinner_item);
        final List<String> plantsList = new ArrayList<>(Arrays.asList(progress));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,plantsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(adapter);
//        spinStatus.setOnItemSelectedListener(this);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final Bundle b = getIntent().getExtras();

        final String description = b.getString("Description");
        final String requestType = b.getString("Request Type");
        final String roomnumber = b.getString("Room Number");
        final String useremail = b.getString("User Email");
//        final String username = b.getString("User Name");
        final String status = b.getString("Status");
        final String documentID = b.getString("documentID");

        String[] arr = roomnumber.split(" - ");


        TVdesc.setText(description);
        TVreqtype.setText(arr[1]);
        TVroomnumber.setText(arr[0]);
        TVuseremail.setText(useremail);

        if (status.equalsIgnoreCase("pending"))
            spinStatus.setSelection(0);
        else if (status.equalsIgnoreCase("in progress"))
            spinStatus.setSelection(1);
        else if (status.equalsIgnoreCase("completed"))
            spinStatus.setSelection(2);

//        TVusername.setText(username);
        btnupdate.findViewById(R.id.ButtonUpdate);


        /*
         * Method to Update the service status for the maintenance Request page functionality takes the parameters from the xml
         * and created a record in Firestore Users table
         * */
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference washingtonRef = db.collection("Service Requests").document(documentID);

                washingtonRef
                        .update("Status", spinStatus.getSelectedItem().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Record updated successfully.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error updating document.", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }

    /**
     * this method used to enable the back button
     *
     * @param item : menu item object
     * @return : it return to the previous activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, ReceiveServiceRequests.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}