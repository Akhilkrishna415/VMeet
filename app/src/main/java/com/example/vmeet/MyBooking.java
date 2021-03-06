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

 /**
  * MyBooking is used to display the bookings of the user
  */
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

     /**
      * this method is the main which triggers when you called this activity
      * @param savedInstanceState : bundle object
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

     /**
      * this method is used to load the information from the database
      */
     private void loadinformation() {

         db = FirebaseFirestore.getInstance();
         db.collection("NewRoomRequest")
                 .whereEqualTo("userID", userId)
                 .whereEqualTo("Active", true)
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful()) {
                             for (QueryDocumentSnapshot document : task.getResult()) {
                                 String room = (String) document.getData().get("event_room_number");
                                 String date = (String) document.getData().get("event_date");
                                 String title = (String) document.getData().get("event_Type");
                                 String start_time = (String) document.getData().get("event_start_time");
                                 String end_time = (String) document.getData().get("event_end_time");
                                 String hardwares = (String) document.getData().get("hardware_Requirements");
                                 String softwares = (String) document.getData().get("software_Requirements");
                                 String username = (String) document.getData().get("user_name");
                                 String addComments = (String) document.getData().get("additional_comments");
                                 String documentID = (String) document.getId();
                                 //String room_img_url = (String) document.getData().get("room_img_url");
                                 RecycleList.add(new MyBookingModel(title, start_time, end_time, hardwares, softwares, addComments,  username, room, date,documentID));
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
         adapterv2 = new MyBookingAdapter(this, RecycleList);
         recycler.setHasFixedSize(true);
         recycler.setAdapter(adapterv2);

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
                 Intent intent = new Intent(this, Homepage.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }
     }

}