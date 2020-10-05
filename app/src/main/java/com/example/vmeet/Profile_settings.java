package com.example.vmeet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile_settings extends AppCompatActivity {
    Button btn_editprofile;
    TextView email,phonenumber,staffid,dept,designation;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
//    int TAKE_IMAGE_CODE=10001;
//    ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);


        btn_editprofile= findViewById(R.id.edit_profile);
        email=findViewById(R.id.Profile_email);
        phonenumber=findViewById(R.id.Profile_number);
        staffid=findViewById(R.id.Profile_id);
        dept=findViewById(R.id.Profile_department);
        designation=findViewById(R.id.Profile_designation);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userId=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fStore.collection("Users").document(userId);
        Log.v("naveen", String.valueOf(documentReference));
        documentReference.addSnapshotListener(Profile_settings.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                Log.v("naveen", String.valueOf(documentSnapshot));
                Log.v("naveen", String.valueOf(documentSnapshot.getString("Email")));
                email.setText(documentSnapshot.getString("Email"));
                phonenumber.setText(documentSnapshot.getString("Phone Number"));
                staffid.setText(documentSnapshot.getString("Staffid"));
                dept.setText(documentSnapshot.getString("Department"));
                designation.setText(documentSnapshot.getString("Designation"));

            }
        });



        btn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),Editprofile.class);
                i.putExtra("email",email.getText().toString());
                i.putExtra("phonenumber",phonenumber.getText().toString());
                i.putExtra("staffid",staffid.getText().toString());
                i.putExtra("dept",dept.getText().toString());
                i.putExtra("designation",designation.getText().toString());
                startActivity(i);
            }
        });


    }

//    public void handleimageclick(View view) {
//
//        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(intent.resolveActivity(getPackageManager())!=null){
//            startActivityForResult(intent,TAKE_IMAGE_CODE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==TAKE_IMAGE_CODE){
//            switch(resultCode){
//                case RESULT_OK:
//                    Bitmap bitmap=(Bitmap) data.getExtras().get("data");
//                    profileImageView.setImageBitmap(bitmap);
//
//
//            }
//        }
//    }
}