package com.example.sarithmetics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class PauseActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare buttons for accessing different activities
    private Button resumeButton;
    private Button stopButton;
    private int maxLives, remainingLives;
    private SessionManager sessionManager;
    private Set<String> selectedPowerups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pauseact);

        // Find the buttons in the layout file and set their click listeners
        resumeButton = findViewById(R.id.resume_button);
        stopButton = findViewById(R.id.stop_button);
        resumeButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        sessionManager = new SessionManager(this);
        selectedPowerups = sessionManager.getPowerups();

        Intent intent = getIntent();
        maxLives = intent.getIntExtra("hearts", 0); // Retrieve the hearts value
        remainingLives = intent.getIntExtra("lives", 0); // Retrieve the hearts value
    }

    // Handle button clicks
    @Override
    public void onClick(View view) {
        Intent intent;
        try {
            if (view.getId() == R.id.resume_button) {
                    // Go to Shop activity
                    intent = new Intent(this, EndlessGameActivity.class);
                    intent.putExtra("hearts", maxLives);
                    intent.putExtra("lives", remainingLives);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else if (view.getId() == R.id.stop_button) {
                    // Go to Shop activity
                    selectedPowerups = sessionManager.getPowerups();
                    selectedPowerups.clear();
                    intent = new Intent(this, PracticeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        } catch (Exception e) {
            // Log the exception and stack trace
            Log.e("PauseActivity", "Error navigating to activity: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(this, EndlessGameActivity.class));
    }
}