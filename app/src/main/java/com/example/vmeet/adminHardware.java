package com.example.vmeet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class adminHardware extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button btnaddRoom;
    //    TextView goToRegister, forgotPassword;
    EditText roomnum, roomTypeVal, floorOrUnit;
    FirebaseFirestore fstore;
    String userID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hardware);

        btnaddRoom = findViewById(R.id.addRoom);
        roomnum = findViewById(R.id.roomnum);
        roomTypeVal = findViewById(R.id.roomTypeVal);
        floorOrUnit = findViewById(R.id.floorOrUnit);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        btnaddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomnumber, roomTypeValue, floor;
                roomnumber = roomnum.getText().toString();
                roomTypeValue = roomTypeVal.getText().toString();
                floor = floorOrUnit.getText().toString();

                if (roomnumber.isEmpty() && roomTypeValue.isEmpty() && floor.isEmpty()) {
                    Toast.makeText(adminHardware.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                } else {

                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fstore.collection("Rooms").document(userID);
                    Map<String, Object> room = new HashMap<>();
                    room.put("Room Number", roomnumber);
                    room.put("Room Type", roomTypeValue);
                    room.put("Floor", floor);
                    documentReference.set(room).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(adminHardware.this, "Room has been added to the database..!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess: user profile is created for" + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding room. Please try again later", e);
                        }
                    });
                }
            }
        });
    }
}