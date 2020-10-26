package com.example.vmeet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class adminHardware extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button btnaddRoom;
    //    TextView goToRegister, forgotPassword;
    EditText roomnum, roomTypeVal, floorOrUnit;
    String imageURI;

    FirebaseFirestore fstore;
    String userID;
    private FirebaseAuth mAuth;
    ImageView RoomImageView;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hardware);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Add Rooms");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */


        btnaddRoom = findViewById(R.id.addRoom);
        roomnum = findViewById(R.id.roomnum);
        roomTypeVal = findViewById(R.id.roomTypeVal);
        floorOrUnit = findViewById(R.id.floorOrUnit);
        RoomImageView = findViewById(R.id.UploadRoomImg);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        fstore = FirebaseFirestore.getInstance();


        RoomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opengalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opengalleryintent, 1000);
            }
        });


        btnaddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomnumber, roomTypeValue, floor;
                roomnumber = roomnum.getText().toString();
                roomTypeValue = roomTypeVal.getText().toString();
                floor = floorOrUnit.getText().toString();
                storageReference = FirebaseStorage.getInstance().getReference();


                if (roomnumber.isEmpty() && roomTypeValue.isEmpty() && floor.isEmpty()) {
                    Toast.makeText(adminHardware.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                } else {

                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fstore.collection("Rooms").document(roomnumber);
                    Map<String, Object> room = new HashMap<>();
                    room.put("Room Number", roomnumber);
                    room.put("Room Type", roomTypeValue);
                    room.put("Floor", floor);
                    room.put("Room URI",imageURI);
                    documentReference.set(room).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(adminHardware.this, "Room has been added to the database..!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess: user profile is created for" + userID);
                            finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageuri = data.getData();
                RoomImageView.setImageURI(imageuri);

                uploadImagetoFirebase(imageuri);
//                imageURI = imageuri.toString();
            }
        }
    }

    private void uploadImagetoFirebase(Uri imageUri) {
        //upload image to firebase storage

        final StorageReference fileref = storageReference.child("Room/" +roomnum.getText().toString() + "/"+roomnum.getText().toString()+"room.jpg");
        fileref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(RoomImageView);

                        fileref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                imageURI=task.getResult().toString();
                                Log.i("URL",imageURI);
                            }
                            });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, AdminHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}