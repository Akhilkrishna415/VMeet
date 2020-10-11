package com.example.vmeet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyBookingAdapter  extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {

    String[] mTitle = {};
    String[] mRoom = {};
    String[] mTime = {};

    private LayoutInflater layoutInflater;

    public MyBookingAdapter(String[] _data, String[] _data2, String[] _data3) {
        mTitle = _data;
        mTime = _data2;
        mRoom = _data3;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.mybooking_model, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = mTitle[position];
        holder.Title.setText(title);

        String room = mRoom[position];
        holder.Room.setText(room);

        String time = mTime[position];
        holder.Time.setText(time);

    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView Time;
        TextView Room;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            Time = itemView.findViewById(R.id.time);
            Room = itemView.findViewById(R.id.room);
        }

    }
}
