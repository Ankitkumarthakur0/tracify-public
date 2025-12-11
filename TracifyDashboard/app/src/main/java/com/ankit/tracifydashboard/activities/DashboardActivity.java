package com.ankit.tracifydashboard.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ankit.tracifydashboard.R;

public class DashboardActivity extends AppCompatActivity {

    // UI Elements
    private Button btnLocate;
    private Switch switchGuest, switchAirplane, switchCamera;
    private CardView cardInstantLock;

    // Map Elements
    private WebView mapWebView;
    private TextView btnFullScreen;
    private boolean isMapVisible = false;

    //Data Save karne ke liye (SharedPreferences)
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "TracifyPrefs";
    private static final String KEY_GUEST = "guest_mode";
    private static final String KEY_AIRPLANE = "airplane_mode";
    private static final String KEY_CAMERA = "camera_access";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_deshboard);

        // Initialize Views
        btnLocate = findViewById(R.id.btnLocate);
        switchGuest = findViewById(R.id.switchGuest);
        switchAirplane = findViewById(R.id.switchAirplane);
        switchCamera = findViewById(R.id.switchCamera);
        cardInstantLock = findViewById(R.id.cardInstantLock);

        mapWebView = findViewById(R.id.mapWebView);
        btnFullScreen = findViewById(R.id.btnFullScreen);

        //Initialize SharedPreferences (Memory)
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Restore Previous State (App khulte hi purana status check karega)
        switchGuest.setChecked(sharedPreferences.getBoolean(KEY_GUEST, false));
        switchAirplane.setChecked(sharedPreferences.getBoolean(KEY_AIRPLANE, false));
        switchCamera.setChecked(sharedPreferences.getBoolean(KEY_CAMERA, false)); // Default false/blocked

        //Map Setup
        WebSettings webSettings = mapWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mapWebView.setWebViewClient(new WebViewClient());

        // 1. GUEST MODE SWITCH (Save State + Action)
        switchGuest.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save new state
            sharedPreferences.edit().putBoolean(KEY_GUEST, isChecked).apply();

            if (isChecked) {
                Toast.makeText(this, "Guest Mode: ACTIVATED", Toast.LENGTH_SHORT).show();
                // Yaha aap chahein to GuestModeActivity khol sakte hain,
                // lekin agar sirf ON rakhna hai to ye code hata dein:
                /*
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(DashboardActivity.this, GuestModeActivity.class));
                }, 1000);
                */
            } else {
                Toast.makeText(this, "Guest Mode: DEACTIVATED", Toast.LENGTH_SHORT).show();
            }
        });

        // 2. AIRPLANE MODE SWITCH (Save State + Action)
        switchAirplane.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save new state
            sharedPreferences.edit().putBoolean(KEY_AIRPLANE, isChecked).apply();

            if (isChecked) {
                Toast.makeText(this, "Fake Airplane Mode: ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Fake Airplane Mode: OFF", Toast.LENGTH_SHORT).show();
            }
        });

        // 3. CAMERA ACCESS SWITCH (Save State + Action)

        switchCamera.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save new state
            sharedPreferences.edit().putBoolean(KEY_CAMERA, isChecked).apply();

            if (isChecked) {
                Toast.makeText(this, "Camera Access: GRANTED ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera Access: BLOCKED ", Toast.LENGTH_SHORT).show();
            }
        });

        // 4. INSTANT LOCK (Ye hamesha click par hi kaam karega)
        cardInstantLock.setOnClickListener(v -> {
            Toast.makeText(this, "Locking Device...", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(DashboardActivity.this, LockScreenModule.class);
                startActivity(intent);
            }, 800);
        });

        // 5. MAP LOCATE LOGIC
        btnLocate.setOnClickListener(v -> {
            if (isMapVisible) {
                mapWebView.setVisibility(View.GONE);
                btnFullScreen.setVisibility(View.GONE);
                btnLocate.setText("Locate Now  >");
                isMapVisible = false;
            } else {
                mapWebView.setVisibility(View.VISIBLE);
                btnFullScreen.setVisibility(View.VISIBLE);
                String mapUrl = "https://www.google.com/maps/search/?api=1&query=Amity+University+Patna";
                mapWebView.loadUrl(mapUrl);
                btnLocate.setText("Close Map");
                isMapVisible = true;
                Toast.makeText(DashboardActivity.this, "Locating Target...", Toast.LENGTH_SHORT).show();
            }
        });

        btnFullScreen.setOnClickListener(v -> {
            try {
                startActivity(new Intent(DashboardActivity.this, FindDeviceModule.class));
            } catch (Exception e) {
                Toast.makeText(this, "Module Missing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // IMPORTANT: onResume() se wo "Reset Code" hata diya hai
    // Kyunki ab hume Switch ko ON hi rakhna hai agar user ne ON chhoda tha.
}
