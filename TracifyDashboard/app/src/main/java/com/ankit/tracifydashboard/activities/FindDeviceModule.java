package com.ankit.tracifydashboard.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.ankit.tracifydashboard.R;

public class FindDeviceModule extends AppCompatActivity {

    private TextView tvLocationStatus, tvCoordinates;
    private Button btnOpenMaps;
    private ProgressBar progressBar;

    // TARGET: Amity University Patna Coordinates
    private final double TARGET_LAT = 25.5941;
    private final double TARGET_LNG = 85.0365;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_device_module);

        // UI Initialize
        tvLocationStatus = findViewById(R.id.tvLocationStatus);
        tvCoordinates = findViewById(R.id.tvCoordinates);
        btnOpenMaps = findViewById(R.id.btnOpenMaps);
        progressBar = findViewById(R.id.progressBar);

        // Start Simulation
        startDemoLocating();

        // Button Click Logic
        btnOpenMaps.setOnClickListener(v -> openNavigation(TARGET_LAT, TARGET_LNG));
    }

    private void startDemoLocating() {
        // Step 1: Searching Effect
        tvLocationStatus.setText("Acquiring Target Signal");
        progressBar.setVisibility(View.VISIBLE);
        btnOpenMaps.setEnabled(false);
        btnOpenMaps.setText("Calculating Route");

        // Step 2: 3 Second Delay
        new Handler().postDelayed(() -> {

            // Step 3: Found!
            progressBar.setVisibility(View.GONE);
            tvLocationStatus.setText("Target Found: Amity University");
            tvCoordinates.setText(TARGET_LAT + ", " + TARGET_LNG);

            // Enable Button for Navigation
            btnOpenMaps.setEnabled(true);
            btnOpenMaps.setText("NAVIGATE TO DEVICE");

            Toast.makeText(FindDeviceModule.this, "Route Calculated!", Toast.LENGTH_SHORT).show();

        }, 2500); // 2.5 seconds delay
    }

    //Function: Rasta (Direction) dikhane ke liye
    private void openNavigation(double lat, double lng) {
        try {
            // "google.navigation:q=" wala URI use karenge
            // Ye automatically current location se target tak ka rasta dikhata hai
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng + "&mode=d");

            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Fallback agar app installed nahi hai
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lng)));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
