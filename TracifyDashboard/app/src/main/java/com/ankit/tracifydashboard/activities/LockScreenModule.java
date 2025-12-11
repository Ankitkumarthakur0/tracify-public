package com.ankit.tracifydashboard.activities;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.ankit.tracifydashboard.R;

public class LockScreenModule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Status Bar aur Navigation Bar chupane ke liye (Full Screen Lock Feel)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 2. Layout set karein
        setContentView(R.layout.activity_lock_screen_module);

        // 3. Unlock Button Logic
        Button btnUnlock = findViewById(R.id.btnUnlock);
        btnUnlock.setOnClickListener(v -> {
            Toast.makeText(this, "Device Unlocked", Toast.LENGTH_SHORT).show();
            finish(); // Activity band kar dega
        });
    }

    // Back button disable karne ke liye (Taaki lock easily na hate)
    @Override
    public void onBackPressed() {
        // Super call nahi karenge, taaki back button kaam na kare
        Toast.makeText(this, "Device is Locked!", Toast.LENGTH_SHORT).show();
    }
}
