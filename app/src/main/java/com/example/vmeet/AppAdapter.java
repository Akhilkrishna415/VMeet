package com.example.vmeet;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmeet.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    Context context;
    List<RecycleModel> recycleModels;
    //String[] mTitle = {};
    //String [] mRoom ={};
    //String[] mTime = {};
    private LayoutInflater layoutInflater;
    public AppAdapter(List<RecycleModel> model)
    {
       /* mTitle = _data;
        mTime = _data2;
        mRoom = _data3;
        */
//        this.context = context;
        this.recycleModels = model;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.cardmodelhome,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     /*   String title = mTitle[position];
        holder.Title.setText(title);

        String room = mRoom[position];
        holder.Room.setText(room);


        String time = mTime[position];
        holder.Time.setText(time);

      */
        holder.Title.setText(recycleModels.get(position).getTitle());
        holder.Room.setText(recycleModels.get(position).getRoom());
        holder.Time.setText(recycleModels.get(position).getTime());
        if(!recycleModels.get(position).getImageURL().isEmpty()) {
            Picasso.get().load(recycleModels.get(position).getImageURL()).into(holder.Img);
        }



    }

    @Override
    public int getItemCount() {
        return recycleModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView Img;
        TextView Title;
        TextView Time;
        TextView Room;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = itemView.findViewById(R.id.ImagePreview);
            Title = itemView.findViewById(R.id.Title);
            Time = itemView.findViewById(R.id.Time);
            Room = itemView.findViewById(R.id.Room);
        }
    }
}
