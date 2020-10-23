package com.example.vmeet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ProductViewHolder> {
        Context context;
        List<RoomsModel> hwswList;

    public RoomsAdapter(Context context, List<RoomsModel> hwswList) {
        this.context = context;
        this.hwswList = hwswList;
    }

        @NonNull
        @Override
        public RoomsAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_row_item, parent, false);
        return new RoomsAdapter.ProductViewHolder(view);
    }

        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.imgtitle.setText(hwswList.get(position).getRoom_number());
        holder.verText.setText(hwswList.get(position).getRoom_type());
        holder.deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(context, /* delete product Class*/);
//
//                Bundle b = new Bundle();
//
//
//                String name = breakfastlist.get(position).getTitle();
//                String price = breakfastlist.get(position).getPrice();
////                String image = breakfastlist.get(position).getImageURL();
//
//
//                b.putString("Name", name);
//                b.putString("Price", price);
////                b.putString("Image", String.valueOf(image));
//
//
//                i.putExtras(b);
//                context.startActivity(i/*, activityOptions.toBundle()*/);

            }
        });
//        holder.versionText.setText(hwswList.get(position).getPrice() + " $");
    }


        public int getItemCount() {
        return hwswList.size();
    }

        public static final class ProductViewHolder extends RecyclerView.ViewHolder {
            //        ImageView prodImage;
            TextView imgtitle, verText;
            ImageButton deleteItemBtn;

            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                deleteItemBtn = itemView.findViewById(R.id.deleteItem);
                imgtitle = itemView.findViewById(R.id.imagetitle);
                verText = itemView.findViewById(R.id.versionText);

            }
        }
    }
