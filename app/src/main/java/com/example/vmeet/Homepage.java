package com.example.vmeet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore fStore;
    ImageView profileImg;
    TextView profileName, profileEmail;
    StorageReference storageReference;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreatePostActivity.class);
                startActivity(i);
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        //navigation view user Details
        drawer = findViewById(R.id.drawer_layout);
        profileName = (TextView) headerView.findViewById(R.id.profilename);
        profileEmail = (TextView) headerView.findViewById(R.id.profileemail);
        profileImg = (ImageView) headerView.findViewById(R.id.profileimg);
        setUserDetails();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setUserDetails();
            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

       /* // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        */


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setCheckable(true);
        int id = item.getItemId();
        switch (id) {
            case R.id.menu2settings:
                drawer.close();
                drawer.animate();
                startActivity(new Intent(Homepage.this, Settings.class));
                break;

            case R.id.menu3logout:
                drawer.close();
                drawer.animate();
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//makesure user cant go back
                break;

            case R.id.nav_roombooking:
                drawer.close();
                drawer.animate();
                startActivity(new Intent(Homepage.this, CreatePostActivity.class));
                break;

            case R.id.nav_mybookings:
                drawer.close();
                drawer.animate();
                startActivity(new Intent(Homepage.this, MyBooking.class));
                break;
            case R.id.nav_requestservice:
                drawer.close();
                drawer.animate();
                startActivity(new Intent(Homepage.this, RequestMaintenance.class));
                break;
        }
        return true;
    }

    /*
     * Setting the user details on the homepage navigation Drawer with
     * User full Name
     * User Designation
     * User Profile Image
     * */
    private void setUserDetails() {

        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        String userId = mAuth.getCurrentUser().getUid();

        try {
            StorageReference profileRef = storageReference.child("Users/" + mAuth.getCurrentUser().getUid() + "profile.jpg");
//            System.out.println("sanjay" + profileRef.toString());
                profileRef.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        if (exception instanceof StorageException &&
                                ((StorageException) exception).getErrorCode() == StorageException.ERROR_OBJECT_NOT_FOUND) {
                            Log.d("EXCEPTION", "Profile photo does not exist");
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImg);
                    }
                });
        } catch (Exception e) {
            Log.v("EXCEPTION : ", e.getMessage());
        }


        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                String email = ;
//                String name = ;

                profileName.setText("");
                profileEmail.setText("");
                if (!Objects.requireNonNull(task.getResult().getString("Name")).isEmpty()) {
                    profileName.setText(task.getResult().getString("Name"));
                }
                if (!Objects.requireNonNull(task.getResult().getString("Designation")).isEmpty()) {
                    profileEmail.setText(task.getResult().getString("Designation"));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("###### ERROR", "Profile information does not exist");
            }
        });
    }

}