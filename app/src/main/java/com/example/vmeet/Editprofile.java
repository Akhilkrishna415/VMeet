package com.example.vmeet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Editprofile extends AppCompatActivity {
    Button btn_save,btn_cancel;
    EditText Ep_email,Ep_phone,Ep_dept,Ep_staffid,Ep_designation;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    ImageView profileImageView;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent data=getIntent();

        /*Toolbar configuration and back button start */
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
//        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
//        setSupportActionBar(myToolbar);
//        getSupportActionBar().setTitle("Edit profile");
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        Ep_email=findViewById(R.id.editTextEmailAddress);
        Ep_phone=findViewById(R.id.editTextPhone);
        Ep_dept=findViewById(R.id.editTextDepartment);
        Ep_staffid=findViewById(R.id.editTextStaffID);
        Ep_designation=findViewById(R.id.editTextDesignation);
        profileImageView=findViewById(R.id.editprofileimage);


        Ep_email.setText(data.getStringExtra("email"));
        Ep_phone.setText(data.getStringExtra("phonenumber"));
        Ep_dept.setText(data.getStringExtra("dept"));
        Ep_staffid.setText(data.getStringExtra("staffid"));
        Ep_designation.setText(data.getStringExtra("designation"));

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        user=fAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();


        btn_save=findViewById(R.id.btn_save);
        btn_cancel=findViewById(R.id.btn_cancel);

        StorageReference profileRef=storageReference.child("Users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opengalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opengalleryintent,1000);
            }
        });


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Ep_email.getText().toString().isEmpty() || Ep_phone.getText().toString().isEmpty() || Ep_dept.getText().toString().isEmpty() ||
                        Ep_staffid.getText().toString().isEmpty() || Ep_designation.getText().toString().isEmpty()) {
                    Toast.makeText(Editprofile.this, "one or many fields are empty", Toast.LENGTH_SHORT).show();
                } else {

                    final String email = Ep_email.getText().toString();
                    user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override

                        public void onSuccess(Void aVoid) {
                            DocumentReference docref = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> edited = new HashMap<>();
                            edited.put("Email", email);
                            edited.put("Phone Number", Ep_phone.getText().toString());
                            edited.put("Staffid", Ep_staffid.getText().toString());
                            edited.put("Department", Ep_dept.getText().toString());
                            edited.put("Designation", Ep_designation.getText().toString());
                            docref.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Editprofile.this, "Profile updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Profile_settings.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Editprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            Toast.makeText(Editprofile.this, "Email is changed ", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Editprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Settings.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageuri = data.getData();
                profileImageView.setImageURI(imageuri);

                uploadImagetoFirebase(imageuri);
            }
        }
    }

    private void uploadImagetoFirebase(Uri imageUri) {
        //upload image to firebase storage
        final StorageReference fileref=storageReference.child("Users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        fileref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
            }
        });


    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // app icon in action bar clicked; go home
//                Intent intent = new Intent(this, Profile_settings.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    }
