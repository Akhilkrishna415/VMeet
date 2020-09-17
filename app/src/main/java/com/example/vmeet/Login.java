package com.example.vmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button btnlogin;
    TextView goToRegister, forgotPassword;
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.login);
        goToRegister = findViewById(R.id.gotoRegister);
        forgotPassword = findViewById(R.id.ForgetPass);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), Homepage.class);
                startActivity(i);
            }
        });


    }
}