package com.example.vmeet.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmeet.AppAdapter;
import com.example.vmeet.R;
import com.example.vmeet.RecycleModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    final List<RecycleModel> RecycleList = new ArrayList<>();
    private HomeViewModel homeViewModel;
    String[] MeetingTitle = {"MAD315 exam", "CST 2020 Orientation"};
    String[] RoomNo = {"205", "103"};
    String[] Timev2 = {"3:00 PM", "2:30 PM"};
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    TextView welcomeStr,welcomedate;
    private RecyclerView recycler, recyclerView;
    private RecyclerView.Adapter adapterv2;
    private RecyclerView.LayoutManager layoutManager;
    String name;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        welcomeStr = (TextView)  root.findViewById(R.id.welcomeString);
        welcomedate = (TextView)  root.findViewById(R.id.date);
        layoutManager = new LinearLayoutManager(getContext());
        recycler = root.findViewById(R.id.meetings1);
        recyclerView = root.findViewById(R.id.meetings2);
        adapterv2 = new AppAdapter(MeetingTitle, RoomNo, Timev2);
        setHeaderInfo();
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapterv2);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterv2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    private void setHeaderInfo() {

        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        String userId = mAuth.getCurrentUser().getUid();
        Task<DocumentSnapshot> docRef=fStore.collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               name = task.getResult().getString("Name");
               welcomeStr.setText("Welcome " + name +",");
            }
        });

        Date date = new Date();
        String pattern = "EEEE MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        welcomedate.setText(simpleDateFormat.format(date));
    }


}