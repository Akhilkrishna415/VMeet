package com.example.vmeet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this class is to store the service requests made by the user into the database
 */
public class ReceiveServiceRequests extends AppCompatActivity {
    final List<ServiceReqModel> ServiceReqlist = new ArrayList<>();
    RecyclerView reqItemRecycler;
    ServiceReqAdapter reqServiceAdapter;
    FirebaseFirestore db;
    Uri url;
    StorageReference storageReference;

    /**
     * this method is the main which triggers when you called this activity
     *
     * @param savedInstanceState : bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_service_requests);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Pending Service Requests");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        reqItemRecycler = findViewById(R.id.request_recycler);
        loadServiceRequests();
    }

    /*
     * Load all the service requests which are in pending and In Progress
     * so that the admin can update the status for the respective.
     * @params: none
     * */
    private void loadServiceRequests() {

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        db.collection("Service Requests")
                .whereIn("Status", Arrays.asList("Pending", "In Progress"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("sanjay", document.getId() + " => " + document.getData());
//                                System.out.println("Hello" + document.getId() + " => " + document.getData());
                                String roomNumber = "Issue with " + (String) document.getData().get("Room Number") + " - " + document.getData().get("Request Type");
                                String description = (String) document.getData().get("Issue Description");
                                String status = (String) document.getData().get("Status");
                                String userEmail = (String) document.getData().get("UserEmail");
                                String docuId = document.getId();

//                                final StorageReference profileRef=storageReference.child("Users/"+docuId+"profile.jpg");
//                                profileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Uri> task) {
//                                        if (task.isSuccessful())
//                                      url = task.getResult();
//                                    }
//                                });

                                System.out.println("Sanjay Image url " + url);
                                ServiceReqlist.add(new ServiceReqModel(roomNumber, description, url, status, userEmail, docuId));
                                setreqItemRecycler(ServiceReqlist);
//                                System.out.println("Hello" + document.getId() + " => " + document.getData() + "==> " + ServiceReqlist.toString());
                            }

                        } else {
                            Log.d("", "Error getting services: ", task.getException());
                        }
                    }
                });

    }

    /**
     * @param ServiceReqlist : refers the serviceReq model
     */
    private void setreqItemRecycler(List<ServiceReqModel> ServiceReqlist) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        reqItemRecycler.setLayoutManager(layoutManager);
        reqServiceAdapter = new ServiceReqAdapter(this, ServiceReqlist) {
            @NonNull
            @Override
            public requestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return super.onCreateViewHolder(parent, viewType);
            }
        };
        reqItemRecycler.setAdapter(reqServiceAdapter);
    }

    /**
     * it helps user to go back to the
     *
     * @param item : refers menuitem
     * @return : return item
     */
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
