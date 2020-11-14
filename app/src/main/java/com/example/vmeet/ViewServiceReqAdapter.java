package com.example.vmeet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * this adapter class is used get the information from the service requests from the user and pass information to the layout
 */

public abstract class ViewServiceReqAdapter extends RecyclerView.Adapter<ViewServiceReqAdapter.serviceViewHolder> {
    Context context;
    List<ViewServiceReqmodel> ServiceReqlist;

    public ViewServiceReqAdapter(Context context, List<ViewServiceReqmodel> ServiceReqlist) {
        this.context = context;
        this.ServiceReqlist = ServiceReqlist;
    }

    @NonNull
    @Override
    public ViewServiceReqAdapter.serviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewservicereq, parent, false);
        return new ViewServiceReqAdapter.serviceViewHolder(view);
    }


    public void onBindViewHolder(@NonNull ViewServiceReqAdapter.serviceViewHolder holder, final int position) {
        holder.Tvtitle.setText(ServiceReqlist.get(position).getRoomNumber());
        holder.TvDesc.setText(ServiceReqlist.get(position).getDescription());
        holder.statusprogress.setText(ServiceReqlist.get(position).getStatus());
        Bundle b = new Bundle();
        String roomNumber = ServiceReqlist.get(position).getRoomNumber();
        String description = ServiceReqlist.get(position).getDescription();
        String status = ServiceReqlist.get(position).getStatus();
        String useremail = ServiceReqlist.get(position).getUserEmail();

        b.putString("Room Number", roomNumber);
        b.putString("Description", description);
        b.putString("Status", status);
        b.putString("User Email", useremail);
        b.putString("documentID",ServiceReqlist.get(position).getDocuId())
//        holder.statusprogress.setTextColor(Col);
//       Picasso.get().load(ServiceReqlist.get(position).getUrl()).into(holder.IvReq);
//
//        holder.IvReq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, MyServiceRequests.class);
//
        ;
//
//                i.putExtras(b);
//                context.startActivity(i);
//
//
//            }
//        });
    }

    public int getItemCount() {
        return ServiceReqlist.size();
    }

//    public class requestViewHolder extends RecyclerView.ViewHolder {

//        TextView Tvtitle, TvDesc,statusprogress;
    // ImageView IvReq;

//        public requestViewHolder(@NonNull View itemView) {
//            super(itemView);
//          //  IvReq = itemView.findViewById(R.id.ReqImg);
//            Tvtitle = itemView.findViewById(R.id.RoomNumber);
//            TvDesc = itemView.findViewById(R.id.ReqDesc);
//            statusprogress = itemView.findViewById(R.id.statusprogress);
//
//        }
//    }


    public class serviceViewHolder extends RecyclerView.ViewHolder {

        TextView Tvtitle, TvDesc, statusprogress;

        public serviceViewHolder(View view) {
            super(view);
            Tvtitle = itemView.findViewById(R.id.RoomNumber);
            TvDesc = itemView.findViewById(R.id.ReqDesc);
            statusprogress = itemView.findViewById(R.id.statusprogress);
        }
    }
}
