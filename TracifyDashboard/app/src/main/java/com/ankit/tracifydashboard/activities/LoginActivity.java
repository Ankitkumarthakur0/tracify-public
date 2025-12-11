package com.ankit.tracifydashboard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ankit.tracifydashboard.R;

public class LoginActivity extends AppCompatActivity {

    EditText etUserId, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Updated IDs according to your XML
        etUserId = findViewById(R.id.userIdEditText);
        etPassword = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.loginButton);

        btnLogin.setOnClickListener(v -> {
            String id = etUserId.getText().toString();
            String pass = etPassword.getText().toString();

            if (id.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simple fixed login for testing
            if (id.equals("admin") && pass.equals("1234")) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish(); // Back press won't return to login
            } else {
                Toast.makeText(this, "Invalid ID or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
