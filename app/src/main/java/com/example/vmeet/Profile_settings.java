package com.example.vmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profile_settings extends AppCompatActivity {
    Button btn_editprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        btn_editprofile= findViewById(R.id.edit_profile);

        btn_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile_settings.this,Editprofile.class));
            }
        });


    }
}