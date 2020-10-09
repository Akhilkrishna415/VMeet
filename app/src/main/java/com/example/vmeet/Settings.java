package com.example.vmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    TextView tv_profilesettings;
    TextView tv_contactus;
    TextView tv_faqs;
    TextView tv_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        tv_profilesettings=findViewById(R.id.profile_settings);
        tv_profilesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Profile_settings.class);
                startActivity(i);

            }
        });
        tv_faqs=findViewById(R.id.faqs);
        tv_faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Faqs.class);
                startActivity(i);

            }
        });
        tv_contactus=findViewById(R.id.contactus);
        tv_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Contactus.class);
                startActivity(i);

            }
        });
        tv_feedback=findViewById(R.id.feedback);
        tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Feedback.class);
                startActivity(i);

            }
        });



    }
}