package com.example.vmeet.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    final List<RecycleModel> RecycleList = new ArrayList<>();
    final List<RecycleModel> otherMeetingsToday = new ArrayList<>();
    /*  String[] MeetingTitle = {"MAD315 exam", "CST 2020 Orientation"};
      String[] RoomNo = {"205", "103"};
      String[] Timev2 = {"3:00 PM", "2:30 PM"};

     */
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    TextView welcomeStr, welcomedate, noMeetingsText, noMeetingsText2;
    RecyclerView recycler, recyclerView;
    RecyclerView.Adapter adapterv2;
    RecyclerView.LayoutManager layoutManager;
    String name, userId, getCurrentDate;
    private HomeViewModel homeViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        welcomeStr = (TextView) root.findViewById(R.id.welcomeString);
        welcomedate = (TextView) root.findViewById(R.id.date);
        noMeetingsText = (TextView) root.findViewById(R.id.noMeetingsText);
        noMeetingsText2 = (TextView) root.findViewById(R.id.noMeetingsText2);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        getCurrentDate = sdf.format(cal.getTime());

        setHeaderInfo();
        loadInformation();


        recycler = root.findViewById(R.id.meetings1);
        recyclerView = root.findViewById(R.id.meetings2);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    private void setHeaderInfo() {


        Task<DocumentSnapshot> docRef = db.collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                name = task.getResult().getString("Name");
                welcomeStr.setText("Hi " + name + ",");
            }
        });

        Date date = new Date();
        String pattern = "dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        welcomedate.setText(simpleDateFormat.format(date));


    }

    private void loadInformation() {
        db.collection("NewRoomRequest")
                .whereEqualTo("userID", userId)
                .whereEqualTo("event_date", getCurrentDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//                            System.out.println("Hello" + );
                            if (task.getResult().size() == 0) {
                                noMeetingsText.setVisibility(View.VISIBLE);
                                recycler.setVisibility(View.GONE);
                            } else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String type = (String) document.getData().get("event_Type");
                                    String room = (String) "Room No.-  " + document.getData().get("event_room_number");
                                    String time = (String) document.getData().get("event_start_time") + " - " + document.getData().get("event_end_time");
                                    String room_img_url = (String) document.getData().get("room_img_url");
                                    RecycleList.add(new RecycleModel(type, room, time, room_img_url));
                                    setProdItemRecycler(RecycleList);
//                                System.out.println("Hello" + document.getId() + " => " + document.getData() + "==> " + RecycleList.toString());
                                }
                            }
                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });


        //Other meetings today
        db.collection("NewRoomRequest")
                .whereEqualTo("event_date", getCurrentDate)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().size() == 0) {
                                noMeetingsText2.setVisibility(View.VISIBLE);
                            } else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String type = (String) document.getData().get("event_Type");
                                    String room = (String) "Room No.-  " + document.getData().get("event_room_number");
                                    String time = (String) document.getData().get("event_start_time") + " - " + document.getData().get("event_end_time");
                                    String room_img_url = (String) document.getData().get("room_img_url");
                                    otherMeetingsToday.add(new RecycleModel(type, room, time, room_img_url));
                                    setProdItemRecycler2(otherMeetingsToday);
//                                System.out.println("Hello" + document.getId() + " => " + document.getData() + "==> " + RecycleList.toString());
                                }
                            }
                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void setProdItemRecycler2(List<RecycleModel> otherMeetingsToday) {
        //Other meetings recycler view adapter
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapterv2 = new AppAdapter(otherMeetingsToday);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterv2);
    }


    private void setProdItemRecycler(List<RecycleModel> RecycleList) {

        layoutManager = new LinearLayoutManager(this.getActivity());
        recycler.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapterv2 = new AppAdapter(RecycleList);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapterv2);


    }

}