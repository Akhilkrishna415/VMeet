package com.example.vmeet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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


    public MyBookingAdapter(Context context,List<MyBookingModel> model)
    {
        this.context = context;
        this.recycleModels = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        layoutInflater = LayoutInflater.from(context);
//
//        View view = layoutInflater.inflate(R.layout.mybooking_model, parent, false);
//        return new MyBookingAdapter.ViewHolder(view);

        View view = LayoutInflater.from(context).inflate(R.layout.mybooking_model, parent, false);
        return new MyBookingAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingAdapter.ViewHolder holder,final int position) {

        holder.Title.setText(recycleModels.get(position).getTitle());
//        holder.viewMoreInfo.setText(recycleModels.get(position).getRoom());
        holder.date.setText(recycleModels.get(position).getDate());

        holder.viewMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(context, "Something are Empty!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, BookingDetails.class);

                Bundle b = new Bundle();
                String title = recycleModels.get(position).getTitle();
                String room = recycleModels.get(position).getRoom();
                String date = recycleModels.get(position).getDate();

                String getBookingDetail = recycleModels.get(position).toString();
                b.putString("getBookingDetail",getBookingDetail);
//                b.putString("event_Type", title);
//                b.putString("event_room_number", room);
//                b.putString("event_date", date);

                i.putExtras(b);
                context.startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        return recycleModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView date;
        ImageView viewMoreInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.Date);
            viewMoreInfo = itemView.findViewById(R.id.ViewMoreInfo);
        }

    }
}
