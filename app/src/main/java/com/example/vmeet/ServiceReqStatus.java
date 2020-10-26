package com.example.vmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ServiceReqStatus extends AppCompatActivity {

    Button btnupdate;
    FirebaseFirestore db;
    TextView TVdesc, TVreqtype, TVroomnumber, TVuseremail, TVusername;
    Spinner spinStatus;
    private FirebaseUser curUser;
    private FirebaseAuth auth;

    @Override
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
//        spinStatus = findViewById(R.id.SpinnerReqStatus);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final Bundle b = getIntent().getExtras();

        final String description = b.getString("Description");
        final String requestType = b.getString("Request Type");
        final String roomnumber = b.getString("Room Number");
        final String useremail = b.getString("User Email");
//        final String username = b.getString("User Name");
        final String status = b.getString("Status");

        String[] arr = roomnumber.split(" - ");


        TVdesc.setText(description);
        TVreqtype.setText(arr[1]);
        TVroomnumber.setText(arr[0]);
        TVuseremail.setText(useremail);
//        TVusername.setText(username);
        btnupdate.findViewById(R.id.ButtonUpdate);


    }

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