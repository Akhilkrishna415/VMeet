package com.example.vmeet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profile_settings extends AppCompatActivity {
    Button btn_editprofile;
    TextView email,phonenumber,staffid,dept,designation;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    ImageView profileimage;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Profile Settings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        btn_editprofile= findViewById(R.id.edit_profile);
        email=findViewById(R.id.Profile_email);
        phonenumber=findViewById(R.id.Profile_number);
        staffid=findViewById(R.id.Profile_id);
        dept=findViewById(R.id.Profile_department);
        designation=findViewById(R.id.Profile_designation);
        profileimage=findViewById(R.id.profileimageView);


        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        StorageReference profileRef=storageReference.child("Users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileimage);
            }
        });


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, Settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}