package com.example.vmeet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyBooking extends AppCompatActivity {


    final List<RecycleModel> RecycleList = new ArrayList<>();
    String[] MeetingTitle = {"MAD315 exam", "CST 2020 Orientation","MAD415 Orientation","CST 2019 Workshop","Meeting with Student"};
    String[] RoomNo = {"205", "103","104","113","94"};
    String[] Timev2 = {"11:00 AM", "1:30 PM","2:30 PM","4:00 PM","5:30 PM"};

    private RecyclerView recycler;
    private RecyclerView.Adapter adapterv2;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
       myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
//        getSupportActionBar().setTitle("Registration");
        /*Toolbar configuration and back button End */

        layoutManager = new LinearLayoutManager(this);
        recycler = findViewById(R.id.mybooking_recycler);
        adapterv2 = new MyBookingAdapter(MeetingTitle, RoomNo, Timev2);

        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapterv2);


    }
}