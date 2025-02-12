package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_forgotpassword extends AppCompatActivity {

    EditText enterUsername, newPassword, rePassword;
    Button resetBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);


        enterUsername = findViewById(R.id.enterusername);
        newPassword = findViewById(R.id.newpassword);
        rePassword = findViewById(R.id.repassword);
        resetBtn = findViewById(R.id.resetbtn);

        db = new DatabaseHelper(this);

        resetBtn.setOnClickListener(v -> {
            String username = enterUsername.getText().toString().trim();
            String newPass = newPassword.getText().toString().trim();
            String rePass = rePassword.getText().toString().trim();

            //  input
            if (username.isEmpty() || newPass.isEmpty() || rePass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(rePass)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if username exists
            boolean userExists = db.checkUsername(username);
            if (!userExists) {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update password in database
            boolean updateSuccess = db.updatePassword(username, newPass);
            if (updateSuccess) {
                Toast.makeText(this, "Password reset successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class)); // Redirect to login
                finish();
            } else {
                Toast.makeText(this, "Error updating password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
