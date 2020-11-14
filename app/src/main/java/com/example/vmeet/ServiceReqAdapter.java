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
/**
 * ServiceReqAdapter class is used to load list of items at ServiceReqStatus page
 */
public abstract class ServiceReqAdapter extends RecyclerView.Adapter<ServiceReqAdapter.requestViewHolder> {
    Context context;
    List<ServiceReqModel> ServiceReqlist;

    /**
     * to get the data into this activity
     *
     * @param context        : it refers the context
     * @param ServiceReqlist : it refers the serviceReq model
     */
    public ServiceReqAdapter(Context context, List<ServiceReqModel> ServiceReqlist) {
        this.context = context;
        this.ServiceReqlist = ServiceReqlist;
    }

    /**
     * this is to inflate the layout
     *
     * @param parent   : ViewGroup object
     * @param viewType : it refers current position
     * @return : it returns viewHolder object
     */
    @NonNull
    @Override
    public ServiceReqAdapter.requestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_row_item, parent, false);
        return new ServiceReqAdapter.requestViewHolder(view);
    }

    /**
     * it is to set the data into the adapter layout
     *
     * @param holder   : adapters viewholder object
     * @param position : it refers current position
     */
    @Override
    public void onBindViewHolder(@NonNull ServiceReqAdapter.requestViewHolder holder, final int position) {
        holder.Tvtitle.setText(ServiceReqlist.get(position).getRoomNumber());
        holder.TvDesc.setText(ServiceReqlist.get(position).getDescription());
        holder.statusprogress.setText(ServiceReqlist.get(position).getStatus());
        holder.IvReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ServiceReqStatus.class);

                Bundle b = new Bundle();
                String roomNumber = ServiceReqlist.get(position).getRoomNumber();
                String description = ServiceReqlist.get(position).getDescription();
                String status = ServiceReqlist.get(position).getStatus();
                String useremail = ServiceReqlist.get(position).getUserEmail();

                b.putString("Room Number", roomNumber);
                b.putString("Description", description);
                b.putString("Status", status);
                b.putString("User Email", useremail);
                b.putString("documentID", ServiceReqlist.get(position).getDocuId());

                i.putExtras(b);
                context.startActivity(i);


            }
        });
    }

    /**
     * get the item count
     *
     * @return : it returns the size of the serviceReq list
     */
    public int getItemCount() {
        return ServiceReqlist.size();
    }

    public class requestViewHolder extends RecyclerView.ViewHolder {

        TextView Tvtitle, TvDesc, statusprogress;
        ImageView IvReq;

        public requestViewHolder(@NonNull View itemView) {
            super(itemView);
            IvReq = itemView.findViewById(R.id.ReqImg);
            Tvtitle = itemView.findViewById(R.id.RoomNumber);
            TvDesc = itemView.findViewById(R.id.ReqDesc);
            statusprogress = itemView.findViewById(R.id.statusprogress);

        }
    }


}
