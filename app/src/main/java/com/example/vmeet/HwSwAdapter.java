package com.example.vmeet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

/**
 * this adapter class is used to bind the information for remove HW/SW class
 */

public class HwSwAdapter extends RecyclerView.Adapter<HwSwAdapter.ProductViewHolder> {
    Context context;
    List<HwSwModel> hwswList;
    FirebaseFirestore db;

    /**
     * this constructor is used intialize the the HW/SW adapter objects
     *
     * @param context
     * @param hwswList
     */

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


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                db = FirebaseFirestore.getInstance();
                                db.collection("Equipment").document(hwswList.get(position).getDocumentID())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "Hardware/ Software has been deleted successfully. Redirecting to Homepage", Toast.LENGTH_LONG).show();
                                                Log.d(TAG, "Room has been successfully deleted from the database!");
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
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                /**
                 * this is used to prompt the alert dialog box when you click the delete image button in the page
                 */

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this equipment?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


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
