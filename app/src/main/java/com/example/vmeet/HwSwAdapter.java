package com.example.vmeet;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.example.vmeet.Login.TAG;

public class HwSwAdapter extends RecyclerView.Adapter<HwSwAdapter.ProductViewHolder> {
    Context context;
    List<HwSwModel> hwswList;
    FirebaseFirestore db;


    public HwSwAdapter(Context context, List<HwSwModel> hwswList) {
        this.context = context;
        this.hwswList = hwswList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_row_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        holder.imgtitle.setText(hwswList.get(position).getTitle());
        holder.verText.setText(hwswList.get(position).getVersion());
        holder.deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseFirestore.getInstance();
                db.collection("Equipment").document(hwswList.get(position).getDocumentID())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Hardware/ Software has been deleted successfully. Redirecting to Homepage", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Room has been successfully deleted!");
                                Intent i = new Intent(context, AdminHome.class);
                                context.startActivity(i);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
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
