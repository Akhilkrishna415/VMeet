package com.example.vmeet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyBookingAdapter  extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {
/*
    String[] mTitle = {};
    String[] mRoom = {};
    String[] mTime = {};

 */

    Context context;
    List<MyBookingModel> recycleModels;
    private LayoutInflater layoutInflater;


    public MyBookingAdapter(List<MyBookingModel> model)
    {
        this.recycleModels = model;
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

        holder.Title.setText(recycleModels.get(position).getTitle());
        holder.Room.setText(recycleModels.get(position).getRoom());
        holder.Time.setText(recycleModels.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return recycleModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Img;
        TextView Title;
        TextView Time;
        TextView Room;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = itemView.findViewById(R.id.ImagePreview);
            Title = itemView.findViewById(R.id.title);
            Time = itemView.findViewById(R.id.time);
            Room = itemView.findViewById(R.id.room);
        }

    }
}
