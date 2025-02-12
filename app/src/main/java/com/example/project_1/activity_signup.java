package com.example.project_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class activity_signup extends AppCompatActivity {

    EditText username, password;
    Button register;
    DatabaseHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        db = new DatabaseHelper(this);

        register.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(activity_signup.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean success = db.registerUser(user, pass);

                if (success) {
                    Toast.makeText(activity_signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(activity_signup.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(activity_signup.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
