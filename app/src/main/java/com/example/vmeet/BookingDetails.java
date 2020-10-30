package com.example.vmeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class BookingDetails extends AppCompatActivity {

    FirebaseFirestore db;
    TextView User, Date, Title, StartTime, EndTime, RoomNo, ImgUrl, Hardware, SoftWare, Comments;
    Spinner spinStatus;
    private FirebaseUser curUser;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Booking Detail");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        User = findViewById(R.id.ShowId);
        Date = findViewById(R.id.ShowDate);
        Title = findViewById(R.id.ShowTitle);
        StartTime = findViewById(R.id.ShowStartTime);
        EndTime = findViewById(R.id.ShowEndTime);
        RoomNo = findViewById(R.id.ShowRoom);
        Hardware = findViewById(R.id.ShowHardwares);
        SoftWare = findViewById(R.id.ShowSoftwares);
        Comments = findViewById(R.id.ShowComments);
        //ImgUrl = findViewById(R.id.ShowImage);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final Bundle b = getIntent().getExtras();
        String[] getBookingDetail = b.getString("getBookingDetail").split(",");
//         Title + "," +  start_time + "," + end_time + ","  + Hardwares + ","  + Softwares + ","  + AddComments + ","  + Username + "," + Room + "," + date;
//        final String FUser = b.getString("user_name");
//        final String FTitle = b.getString("event_Type");
//        final String FRoomno = b.getString("event_room_number");
//        final String FDate = b.getString("event_date");
//
//        final String FSTime = b.getString("event_start_time");
//        final String FETime = b.getString("event_end_time");
//        final String FHardware = b.getString("hardware_Requirements");
//        final String FSoftware = b.getString("software_Requirements");
//        final String FComments = b.getString("additional_comments");

        Title.setText(getBookingDetail[0]);
        StartTime.setText(getBookingDetail[1]);
        EndTime.setText(getBookingDetail[2]);
        Hardware.setText(getBookingDetail[3]);
        SoftWare.setText(getBookingDetail[4]);
        Comments.setText(getBookingDetail[5]);
        User.setText(getBookingDetail[6]);
        RoomNo.setText(getBookingDetail[7]);
        Date.setText(getBookingDetail[8]);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MyBooking.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}