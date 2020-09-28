package com.example.vmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Editprofile extends AppCompatActivity {
    Button btn_save,btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btn_save=findViewById(R.id.btn_save);
        btn_cancel=findViewById(R.id.btn_cancel);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Profile_settings.class);
                startActivity(i);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Settings.class);
                startActivity(i);
            }
        });
    }
}