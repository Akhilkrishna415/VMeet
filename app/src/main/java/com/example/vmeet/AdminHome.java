package com.example.vmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity {

    CardView remvHwSw, remvRooms, addRooms, addHwSw, viewRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        remvHwSw = findViewById(R.id.remvHwSw);
        remvRooms = findViewById(R.id.remvRoom);
        addRooms = findViewById(R.id.addRooms);
        addHwSw = findViewById(R.id.addhwSw);
        viewRequests = findViewById(R.id.viewRequests);


        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.adtoolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.logoutroundeddown);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Admin Dashboard");

        /*Toolbar configuration and back button End */
        remvHwSw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RemoveHardewareSoftware.class));
            }
        });
        remvRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RemoveRooms.class));
            }
        });
        addRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), adminHardware.class));
            }
        });
        addHwSw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminAddHardwareSoftware.class));
            }
        });
        viewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ReceiveServiceRequests.class));
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//makesure user cant go back
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}