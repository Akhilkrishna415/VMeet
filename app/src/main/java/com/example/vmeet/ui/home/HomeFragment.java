package com.example.vmeet.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    final List<RecycleModel> RecycleList = new ArrayList<>();
    private HomeViewModel homeViewModel;
    String[] MeetingTitle = {"MAD315 exam", "CST 2020 Orientation"};
    String[] RoomNo = {"205", "103"};
    String[] Timev2 = {"3:00 PM", "2:30 PM"};

    private RecyclerView recycler, recyclerView;
    private RecyclerView.Adapter adapterv2;
    private RecyclerView.LayoutManager layoutManager;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        layoutManager = new LinearLayoutManager(getContext());
        recycler = root.findViewById(R.id.meetings1);
        recyclerView = root.findViewById(R.id.meetings2);
        adapterv2 = new AppAdapter(MeetingTitle, RoomNo, Timev2);

        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapterv2);

        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterv2);
        recyclerView.setHasFixedSize(true);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }


}