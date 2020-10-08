package com.example.vmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminHome extends AppCompatActivity {

    CardView remvHwSw, remvRooms, addRooms, addHwSw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        remvHwSw = findViewById(R.id.remvHwSw);
        remvRooms = findViewById(R.id.remvRoom);
        addRooms = findViewById(R.id.addRooms);
        addHwSw = findViewById(R.id.addhwSw);

        remvHwSw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RemoveHardewareSoftware.class));
            }
        });
        remvRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RemoveHardewareSoftware.class));
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


    }
}