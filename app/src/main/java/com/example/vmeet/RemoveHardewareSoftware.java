package com.example.vmeet;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    //    ListView BreakFastlist;
    RecyclerView prodItemRecycler;
    HwSwAdapter hwsoftwareAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_hardeware_software);

        prodItemRecycler = findViewById(R.id.product_recycler);
        Log.d("sanjay", "Before finding Breakfast list view id");
//        BreakFastlist = findViewById(R.id.product_recycler);
        Log.d("sanjay", "Before finding Breakfast list loadMenu");
        loadhardwareSoftware();
    }

    private void loadhardwareSoftware() {
        db = FirebaseFirestore.getInstance();
        Log.d("sanjay", "Entering Breakfast list loadMenu");
        db.collection("Equipment")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("sanjay", document.getId() + " => " + document.getData());
//                                System.out.println("Hello" + document.getId() + " => " + document.getData());
                                String title = (String) document.getData().get("Title");
                                String version = (String) document.getData().get("Version");
                                boolean isActive = (boolean) document.getData().get("isActive");
                                hwSwList.add(new HwSwModel(title, version, isActive));
                                setProdItemRecycler(hwSwList);
//                                System.out.println("Hello" + document.getId() + " => " + document.getData() + "==> " + hwSwList.toString());
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
}