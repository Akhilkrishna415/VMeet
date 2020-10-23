package com.example.vmeet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminAddHardwareSoftware extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button btnaddHwSw;
    //    TextView goToRegister, forgotPassword;
    EditText hwswtitle, versionVal;
    CheckBox active;
    FirebaseFirestore fstore;
    String userID;
    private FirebaseAuth mAuth;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_hardware_software);

        spinner = (Spinner) findViewById(R.id.selectHwSw);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.adminHardwareSoftware, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        btnaddHwSw = findViewById(R.id.addhwSw);
        hwswtitle = findViewById(R.id.hwswtitle);
        versionVal = findViewById(R.id.versionVal);
        active = findViewById(R.id.active);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        btnaddHwSw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleOfNewHWSW, HWSWVersionValue;
                boolean isActive;
                titleOfNewHWSW = hwswtitle.getText().toString();
                HWSWVersionValue = versionVal.getText().toString();
                String text = spinner.getSelectedItem().toString();
                String dbTable="";
                isActive = active.isChecked();
                if (titleOfNewHWSW.isEmpty() && HWSWVersionValue.isEmpty()) {
                    Toast.makeText(AdminAddHardwareSoftware.this, "Fields are Empty!", Toast.LENGTH_SHORT).show();
                } else {

                    userID = mAuth.getCurrentUser().getUid();
                    if(text.equalsIgnoreCase("hardware")){
                        dbTable = "Hardware";
                    }if(text.equalsIgnoreCase("software")){
                        dbTable = "Software";
                    }

                    DocumentReference documentReference = fstore.collection("Equipment").document(userID);
                    Map<String, Object> room = new HashMap<>();
                    room.put("Request Type",dbTable);
                    room.put("Title", titleOfNewHWSW);
                    room.put("Version", HWSWVersionValue);
                    room.put("isActive", isActive);
                    documentReference.set(room).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AdminAddHardwareSoftware.this, "New Hardware has been added to the database..!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess: New Hardware has been added to the database..!" + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding Hardware. Please try again later", e);
                        }
                    });
                }
            }
        });
    }


}