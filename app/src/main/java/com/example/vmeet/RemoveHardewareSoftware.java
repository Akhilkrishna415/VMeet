package com.example.vmeet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RemoveHardewareSoftware extends AppCompatActivity {
    final List<HwSwModel> hwSwList = new ArrayList<>();
    RecyclerView prodItemRecycler;
    HwSwAdapter hwsoftwareAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_hardeware_software);


        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Remove Hw/Sw");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        prodItemRecycler = findViewById(R.id.product_recycler);
        loadhardwareSoftware();
    }

    private void loadhardwareSoftware() {
        db = FirebaseFirestore.getInstance();
        db.collection("Equipment").orderBy("Request Type")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("sanjay", document.getId() + " => " + document.getData());
                                String initials = "Hw";
                                if( document.getData().get("Request Type").toString().equalsIgnoreCase("software")){
                                    initials = "Sw";
                                }
                                String title = (String) initials +" - " + document.getData().get("Title");
                                String version = (String) document.getData().get("Version");
                                boolean isActive = (boolean) document.getData().get("isActive");
                                String documentID = document.getId();
                                hwSwList.add(new HwSwModel(title, version, documentID, isActive));
                                setProdItemRecycler(hwSwList);
                            }

                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void setProdItemRecycler(List<HwSwModel> hwSwList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        hwsoftwareAdapter = new HwSwAdapter(this, hwSwList);
        prodItemRecycler.setAdapter(hwsoftwareAdapter);
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