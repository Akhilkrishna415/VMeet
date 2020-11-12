package com.example.vmeet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MyServiceRequests extends AppCompatActivity {

    final List<ViewServiceReqmodel> ServiceReqlist = new ArrayList<>();
    RecyclerView reqItemRecycler;
    ViewServiceReqAdapter serviceReqAdapter;
    FirebaseFirestore db;
    Uri url;
    String created_By;
    TextView noservicereqText;
    StorageReference storageReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service_requests);
        mAuth = FirebaseAuth.getInstance();
        created_By = mAuth.getCurrentUser().getUid();
        noservicereqText = findViewById(R.id.noservicereqsText);

        /*Toolbar configuration and back button start */
        Toolbar myToolbar = findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        myToolbar.setNavigationIcon(R.drawable.iconbackarrowcustom);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("My Service Requests");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*Toolbar configuration and back button End */

        reqItemRecycler = findViewById(R.id.viewServiceReq);
        loadServiceRequests();
    }

    /*
     * Load all the service requests created ny the logged in user.
     * @params: none
     * */
    private void loadServiceRequests() {

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        db.collection("Service Requests")
//                .whereIn("Status", Arrays.asList("Pending", "In Progress"))
                .whereEqualTo("created_By", created_By)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("sanjay", document.getId() + " => " + document.getData());
                                String roomNumber = "Room " + document.getData().get("Room Number");// + " - " + document.getData().get("Request Type");
                                String description = (String) document.getData().get("Issue Description");
                                String status = (String) document.getData().get("Status");
                                String userEmail = (String) document.getData().get("UserEmail");
                                String docuId = document.getId();

                                ServiceReqlist.add(new ViewServiceReqmodel(roomNumber, description, url, status, userEmail, docuId));
                                setreqItemRecycler(ServiceReqlist);
                            }

                        } else {
                            Log.d("", "Error getting services: ", task.getException());
                        }
                    }
                });

    }

    private void setreqItemRecycler(List<ViewServiceReqmodel> ServiceReqlist) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        reqItemRecycler.setLayoutManager(layoutManager);
        serviceReqAdapter = new ViewServiceReqAdapter(this, ServiceReqlist) {
            @NonNull
            @Override
            public serviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return super.onCreateViewHolder(parent, viewType);
            }
        };
        reqItemRecycler.setAdapter(serviceReqAdapter);
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

}
