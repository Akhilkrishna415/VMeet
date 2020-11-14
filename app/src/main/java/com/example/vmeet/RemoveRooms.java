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

/**
 * this class is used for admin to remove rooms from the application
 */

public class RemoveRooms extends AppCompatActivity {
    final List<RoomsModel> roomList = new ArrayList<>();

    RecyclerView prodItemRecycler;
    RoomsAdapter roomsAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_rooms);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Remove Rooms");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        prodItemRecycler = findViewById(R.id.product_recycler);
        loadhardwareSoftware();

    }

    /*
     * Load all the hardware and software items created with in the Equipment table in Firestore
     * @params: none
     * */
    private void loadhardwareSoftware() {
        db = FirebaseFirestore.getInstance();
        db.collection("Rooms").orderBy("Room Type")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("sanjay", document.getId() + " => " + document.getData());
                                String roomType = (String) document.getData().get("Room Type");
                                String floor = (String) document.getData().get("Floor");
                                String roomnumber = (String) document.getData().get("Room Number");
                                String documentID = document.getId();
                                roomList.add(new RoomsModel(floor, roomType, roomnumber, documentID));
                                setProdItemRecycler(roomList);
//                                System.out.println("Hello" + document.getId() + " => " + document.getData() + "==> " + hwSwList.toString());
                            }

                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void setProdItemRecycler(List<RoomsModel> roomList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        roomsAdapter = new RoomsAdapter(this, roomList);
        prodItemRecycler.setAdapter(roomsAdapter);
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