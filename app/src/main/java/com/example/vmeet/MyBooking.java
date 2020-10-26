package com.example.vmeet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmeet.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyBooking extends AppCompatActivity {

    final List<MyBookingModel> RecycleList = new ArrayList<>();
    private HomeViewModel homeViewModel;

    RecyclerView recycler;
    RecyclerView.Adapter adapterv2;
    RecyclerView.LayoutManager layoutManager;
    TextView noMeetingTexts;
    String name, userId, getCurrentDate;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

   /* String[] MeetingTitle = {"MAD315 exam", "CST 2020 Orientation","MAD415 Orientation","CST 2019 Workshop","Meeting with Student"};
    String[] RoomNo = {"205", "103","104","113","94"};
    String[] Timev2 = {"11:00 AM", "1:30 PM","2:30 PM","4:00 PM","5:30 PM"};

    */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
       myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
//        getSupportActionBar().setTitle("Registration");
        /*Toolbar configuration and back button End */

        recycler = findViewById(R.id.mybooking_recycler);
        loadinformation();

    }

    private void loadinformation()
    {
        /*
        db = FirebaseFirestore.getInstance();
        Log.d("nik", "Entering Home menu");
        db.collection("NewRoomRequest")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String type = (String) document.getData().get("event_Type");
                                String room = (String) "Room No.-  " + document.getData().get("event_room_number");
                                String time = (String) document.getData().get("event_start_time") + " - " + document.getData().get("event_end_time");
                                RecycleList.add(new MyBookingModel(type, room, time));
                                setProdItemRecycler(RecycleList);
//                                System.out.println("Hello" + document.getId() + " => " + document.getData() + "==> " + RecycleList.toString());
                            }

                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });
*/
        db = FirebaseFirestore.getInstance();
        db.collection("NewRoomRequest")
                .whereEqualTo("userID", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String type = (String) document.getData().get("event_Type");
                                    String room = (String) "Room No.-  " + document.getData().get("event_room_number");
                                    String time = (String) document.getData().get("event_start_time") + " - " + document.getData().get("event_end_time");
                                    //String room_img_url = (String) document.getData().get("room_img_url");
                                    RecycleList.add(new MyBookingModel(type, room, time));
                                    setProdItemRecycler(RecycleList);
//                                System.out.println("Hello" + document.getId() + " => " + document.getData() + "==> " + RecycleList.toString());
                                }
                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setProdItemRecycler(List<MyBookingModel> RecycleList) {

        layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterv2 = new MyBookingAdapter(RecycleList);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapterv2);
        /*
        //Other meetings recycler view adapter
        adapterv2 = new AppAdapter(RecycleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterv2);

         */
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