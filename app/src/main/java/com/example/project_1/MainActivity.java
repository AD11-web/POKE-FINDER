package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    EditText etusername, etpassword;
    Button loginbtn, signupbtn;
    TextView forgotPassword; // Added Forgot Password TextView
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etpassword);
        loginbtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.signupbtn);
        forgotPassword = findViewById(R.id.forgotpass); // Initialize Forgot Password TextView

        db = new DatabaseHelper(this);

        // Login button click listener
        loginbtn.setOnClickListener(v -> {
            String user = etusername.getText().toString().trim();
            String pass = etpassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean validuser = db.checkUser(user, pass);

                if (validuser) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    // Open the Pokefinder activity
                    Intent intent = new Intent(MainActivity.this, Pokefinder.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Signup button click listener
        signupbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, activity_signup.class);
            startActivity(intent);
        });

        // Forgot Password click listener
        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, activity_forgotpassword.class);
            startActivity(intent);
        });
    }
}
