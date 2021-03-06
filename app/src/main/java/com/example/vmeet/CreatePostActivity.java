package com.example.vmeet;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.firebase.firestore.FieldValue.serverTimestamp;

/**
 * this class is used to make a new booking request for the user
 */

public class CreatePostActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    Button btnsubmit, btnSoftware, btnHardware;
    TextView sf, txtHardware;


    EditText editTextTitle, editTextstaffID, editTextDept, eventdate, noOfPersons, editTextReason, enterEmailId;
    TextView software, username, userDepartment, userStaffId, txthardware;
    String title, staffId, department, eventDate, startTime, endTime, roomNumber, persons, reason, event, softwareReq, hardwareReq, userId;
    Spinner eventsp, roomNoSp, stime, etime;
    int startTimeValue, endTimeValue;
    boolean isAllValid;
    String tempurlString, room_uri;


    String[] listSoftware, listHardware;
    boolean[] checkeditems, hcheckeditems;
    ArrayList<Integer> mUserSoftware = new ArrayList<>();
    ArrayList<Integer> mUserHardware = new ArrayList<>();
    FirebaseFirestore firestore;
    FirebaseAuth fAuth;
    ArrayList<Integer> roomNo = new ArrayList<>();
    StorageReference storageReference;
    List<String> subjectswithRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("New Booking");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        firestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();


        eventdate = findViewById(R.id.eventdate);
        stime = findViewById(R.id.stime);
        etime = findViewById(R.id.etime);
        roomNoSp = findViewById(R.id.roomNoSp);
        eventsp = findViewById(R.id.eventsp);
        txtHardware = findViewById(R.id.txthardware);
        noOfPersons = findViewById(R.id.noOfPersons);
        username = findViewById(R.id.username);
        userDepartment = findViewById(R.id.userDepartment);
        userStaffId = findViewById(R.id.userStaffId);
        editTextReason = findViewById(R.id.editTextReason);
        enterEmailId = findViewById(R.id.enterEmailId);

        showRooms();
        showUserInfo();
        /*
        user can pick a date by using the calender image
         */

        ImageView calender = (ImageView) findViewById(R.id.calander);
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        /*
         It displays the available software from the database to the user while booking a room
         */
        btnSoftware = (Button) findViewById(R.id.btnsoftware);
        software = (TextView) findViewById(R.id.software);
        final ArrayList<String> arrSoftware = new ArrayList<String>();
        firestore.collection("Equipment").whereEqualTo("Request Type", "Software")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                                arrSoftware.add(documentSnapshot.getString("Title"));
                                Object[] gfg = arrSoftware.toArray();
                                String[] str = Arrays.copyOf(gfg,
                                        gfg.length,
                                        String[].class);
                                listSoftware = str;
                                checkeditems = new boolean[listSoftware.length];
                            }
                        }
                    }
                });

          /*
          here user can choose his required software from the list provided
           */
        btnSoftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuiler = new AlertDialog.Builder(CreatePostActivity.this);
                mBuiler.setTitle(R.string.As);
                mBuiler.setMultiChoiceItems(listSoftware, checkeditems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!mUserSoftware.contains(position)) {
                                mUserSoftware.add(position);
                            } else {
                                mUserSoftware.remove(position);
                            }
                        }
                    }
                });
                mBuiler.setCancelable(false);
                mBuiler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < mUserSoftware.size(); i++) {
                            item = item + listSoftware[mUserSoftware.get(i)];
                            if (i != mUserSoftware.size() - 1) {
                                item = item + ",";
                            }
                        }
                        software.setText(item);
                    }
                });
                mBuiler.setNegativeButton(R.string.Dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuiler.setNeutralButton(R.string.ClearAll, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkeditems.length; i++) {
                            checkeditems[i] = false;
                            mUserSoftware.clear();
                            software.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuiler.create();
                mBuiler.show();
            }
        });


       /*
         It displays the available hardware from the database to the user while booking a room
         */

        btnHardware = (Button) findViewById(R.id.btnHardware);
        txtHardware = (TextView) findViewById(R.id.txthardware);
        final ArrayList<String> arrHardware = new ArrayList<String>();
        firestore.collection("Equipment").whereEqualTo("Request Type", "Hardware")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                                arrHardware.add(documentSnapshot.getString("Title"));
                                Object[] gfg = arrHardware.toArray();
                                String[] str = Arrays.copyOf(gfg,
                                        gfg.length,
                                        String[].class);
                                listHardware = str;
                                hcheckeditems = new boolean[listHardware.length];
                            }
                        }
                    }
                });

         /*
          here user can choose his required hardware from the list provided
           */
        btnHardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder hBuiler = new AlertDialog.Builder(CreatePostActivity.this);
                hBuiler.setTitle("Available Hardware");
                hBuiler.setMultiChoiceItems(listHardware, hcheckeditems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!mUserHardware.contains(position)) {
                                mUserHardware.add(position);
                            } else {
                                mUserHardware.remove(position);
                            }
                        }
                    }
                });
                hBuiler.setCancelable(false);
                hBuiler.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < mUserHardware.size(); i++) {
                            item = item + listHardware[mUserHardware.get(i)];
                            if (i != mUserHardware.size() - 1) {
                                item = item + ",";
                            }
                        }
                        txtHardware.setText(item);
                    }
                });
                hBuiler.setNegativeButton(R.string.Dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                hBuiler.setNeutralButton(R.string.ClearAll, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < hcheckeditems.length; i++) {
                            hcheckeditems[i] = false;
                            mUserHardware.clear();
                            txtHardware.setText("");
                        }
                    }
                });
                AlertDialog mDialog = hBuiler.create();
                hBuiler.show();
            }
        });


        /**
         * Submit button: on submit send data to firestore New Room Request functionality
         */
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllValid = true;
                title = username.getText().toString();
                department = userDepartment.getText().toString();
                staffId = userStaffId.getText().toString();
                event = eventsp.getSelectedItem().toString();
                eventDate = eventdate.getText().toString();
                startTime = stime.getSelectedItem().toString();
                endTime = etime.getSelectedItem().toString();
                roomNumber = roomNoSp.getSelectedItem().toString();

                softwareReq = software.getText().toString();
                hardwareReq = txtHardware.getText().toString();
                persons = noOfPersons.getText().toString();
                reason = editTextReason.getText().toString();
                tempurlString = subjectswithRooms.get(roomNoSp.getSelectedItemPosition());
                String[] room_url = tempurlString.split(",,");
//                Toast.makeText(getApplicationContext(),room_url[1], Toast.LENGTH_SHORT).show();
                room_uri = "";
                if (!room_url[1].isEmpty()) {
                    room_uri = room_url[1];
                }


                startTimeValue = returnTimeValidNumber(stime.getSelectedItem().toString());
                endTimeValue = returnTimeValidNumber(etime.getSelectedItem().toString());


                if (startTime.isEmpty() || endTime.isEmpty() || eventDate.isEmpty() || persons.isEmpty() || reason.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please make sure you entered all the required values.", Toast.LENGTH_SHORT).show();
                } else if (startTimeValue >= endTimeValue) {
                    Toast.makeText(getApplicationContext(), "Invalid Start time and End time. Please select appropriate values. " + endTimeValue, Toast.LENGTH_SHORT).show();
                    TextView errorText = (TextView) stime.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error

                } else {

                    firestore.collection("NewRoomRequest")
                            .whereEqualTo("event_room_number", roomNumber)
                            .whereEqualTo("event_date", eventDate)
//                            .whereGreaterThanOrEqualTo("eventStartTimeInNumber", startTimeValue)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "task.getResult().size() :  " + task.getResult().size(), Toast.LENGTH_SHORT).show();

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (document != null && document.exists()) {

                                                Log.d("sanjay", "Date :  " + eventDate
                                                        + "selectedTime ===> " + startTimeValue
                                                        + "eventStartTimeInNumber ===> " + document.getData().get("eventStartTimeInNumber")
                                                        + "eventEndTimeInNumber ===> " + document.getData().get("eventEndTimeInNumber")
                                                        + " ==> ", task.getException());
                                                int recordStartTime = Integer.parseInt(document.getData().get("eventStartTimeInNumber").toString());
                                                int recordEndTime = Integer.parseInt(document.getData().get("eventEndTimeInNumber").toString());


                                                if ((startTimeValue > recordStartTime) && (startTimeValue < recordEndTime)) {

                                                    Toast.makeText(getApplicationContext(), "Existing event please change the timings", Toast.LENGTH_SHORT).show();
//                                                    Log.d("sanjay", "Entering first IF: ", task.getException());
                                                    isAllValid = false;
                                                    break;
                                                } else if ((startTimeValue <= recordStartTime)) {
                                                    if ((endTimeValue > recordStartTime) && (endTimeValue < recordEndTime)) {
                                                        Toast.makeText(getApplicationContext(), "Existing event please change the timings", Toast.LENGTH_SHORT).show();
//                                                        Log.d("sanjay", "Entering second IF: ", task.getException());
                                                        isAllValid = false;
                                                        break;
                                                    }
                                                }
                                            }


//                                            Toast.makeText(getApplicationContext(), "Existing event please change the timings" + document.getData(), Toast.LENGTH_SHORT).show();
                                        }

                                        //submit if only all validations have passed successfully..
                                        if (isAllValid) {

                                            boolean isActive = true;

                                            Calendar cal = Calendar.getInstance();
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                            String getCurrentDate = sdf.format(cal.getTime());

                                            Map<String, Object> room = new HashMap<>();
                                            room.put("user_name", title);
                                            room.put("user_staffId", staffId);
                                            room.put("user_department", department);
                                            room.put("event_Type", event);
                                            room.put("event_date", eventDate);
                                            room.put("event_start_time", startTime);
                                            room.put("event_end_time", endTime);
                                            room.put("event_room_number", roomNumber);
                                            room.put("software_Requirements", softwareReq);
                                            room.put("hardware_Requirements", hardwareReq);
                                            room.put("no_Of_Attendees", persons);
                                            room.put("additional_comments", reason);
                                            room.put("room_img_url", room_uri);
                                            room.put("Active", isActive);
                                            room.put("created_at", getCurrentDate);
                                            room.put("created_timestamp", serverTimestamp());
                                            room.put("userID", userId);
                                            room.put("eventStartTimeInNumber", startTimeValue);
                                            room.put("eventEndTimeInNumber", endTimeValue);
                                            Toast.makeText(getApplicationContext(), "Booking Request has been made. Redirecting to Homepage..", Toast.LENGTH_LONG).show();
                                            firestore.collection("NewRoomRequest").add(room).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Booking Request has been made. Redirecting to Homepage..", Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(getApplicationContext(), Homepage.class));
                                                        sendMail();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Error while booking the request. Please try again later.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }

                                    } else {
                                        Log.d("sanjay", "Error getting documents: ", task.getException());
                                    }

                                }
                            });
                    Log.d("sanjay", "Error getting documents: " + isAllValid);
                }


            }
        });
    }

    private int returnTimeValidNumber(String stEndTime) {

        switch (stEndTime) {
            case "9:00 AM":
                return 900;
            case "9:30 AM":
                return 950;
            case "10:00 AM":
                return 1000;
            case "10:30 AM":
                return 1050;
            case "11:00 AM":
                return 1100;
            case "11:30 AM":
                return 1150;
            case "12:00 PM":
                return 1200;
            case "12:30 PM":
                return 1250;
            case "01:00 PM":
                return 1300;
            case "01:30 PM":
                return 1350;
            case "02:00 PM":
                return 1400;
            case "2:30 PM":
                return 1450;
            case "3:00 PM":
                return 1500;
            case "3:30 PM":
                return 1550;
            case "4:00 PM":
                return 1600;
            case "4:30 PM":
                return 1650;
        }

        return 0;
    }

    /**
     * This method used to send email comfirmation about event
     */
    private void sendMail() {
        try {
            String mEmail = enterEmailId.getText().toString();
            String mSubject = "Update about " + event;
            String mMessage = "You Have Scheduled an " + event + " start's at " + startTime + " to " + endTime + " on " + eventDate + "in room number" + roomNumber + "in CEGEP DE LA GASPESIE ET DES ILES, Montreal Campus";

            GMailSender gMailSender = new GMailSender(this, mEmail, mSubject, mMessage);
            gMailSender.execute();
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }

    /**
     * This method shows the user information stored in the firestoe to console
     */
    private void showUserInfo() {

        storageReference = FirebaseStorage.getInstance().getReference();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = firestore.collection("Users").document(userId);
        documentReference.addSnapshotListener(CreatePostActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                assert documentSnapshot != null;
                username.setText("");
                userDepartment.setText("");
                userStaffId.setText("");
                if (!Objects.requireNonNull(documentSnapshot.getString("Name")).isEmpty()) {
                    username.setText(documentSnapshot.getString("Name"));
                }
                if (!Objects.requireNonNull(documentSnapshot.getString("Department")).isEmpty()) {
                    userDepartment.setText(documentSnapshot.getString("Department"));
                }
                if (!Objects.requireNonNull(documentSnapshot.getString("Staffid")).isEmpty()) {
                    userStaffId.setText(documentSnapshot.getString("Staffid"));
                }
            }
        });
    }

    /**
     * This method used to show the rooms from database  added by the admin
     */
    private void showRooms() {
        CollectionReference subjectsRef = firestore.collection("Rooms");

        Spinner spinner = (Spinner) findViewById(R.id.roomNoSp);
        final List<String> subjects = new ArrayList<>();
        subjectswithRooms = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String subject = document.getString("Room Number");
                        subjectswithRooms.add(document.getString("Room Number") + ",," + document.getString("Room URI"));
                        subjects.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

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

    /**
     * Calendar Date picker
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
//        TextView textView = (TextView) findViewById(R.id.eventdate);
//        textView.setText(currentDateString);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = format.format(c.getTime());
        TextView textView = (TextView) findViewById(R.id.eventdate);
        textView.setText(strDate);


    }

}