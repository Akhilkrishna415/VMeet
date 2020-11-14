package com.example.vmeet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.List;

import static com.example.vmeet.Login.TAG;

/**
 * this class is used to display the booking details of the user and helps to cancel the booking
 */

public class BookingDetails extends AppCompatActivity {

    FirebaseFirestore db;
    TextView User, Date, Title, StartTime, EndTime, RoomNo, ImgUrl, Hardware, SoftWare, Comments;
    Button CancelBooking;
    private FirebaseUser curUser;
    private FirebaseAuth auth;
    List<MyBookingModel> hwswList;
    String documentID;

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

        CancelBooking = findViewById(R.id.CancelBook);
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
       /*
       to initialize the firebase instance
        */
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        final Bundle b = getIntent().getExtras();
        String[] getBookingDetail = b.getString("getBookingDetail").split(",");
//         Title + "," +  start_time + "," + end_time + ","  + Hardwares + ","  + Softwares + ","
//         + AddComments + ","  + Username + "," + Room + "," + date;
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
        documentID = getBookingDetail[9];


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

    /*
     * this helps user to view about his booking request
     * It allows him to cancel the booking request he made
     */
    public void Cancel(View view) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        final int position = 0;
                        db = FirebaseFirestore.getInstance();
                        DocumentReference reference = db.collection("NewRoomRequest").document(documentID);
                        reference.update("Active", false)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent = new Intent(getApplicationContext(), MyBooking.class);
                                        Toast.makeText(getApplicationContext(), "Booking has been cancelled successfully.", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error updating document", e);
                                    }
                                });
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.dismiss();
                        break;
                }
            }
        };
        /*
         * It gives an alert to the user  while cancelling the booking request whether to cancel ot not
         */

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel this booking?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }
}