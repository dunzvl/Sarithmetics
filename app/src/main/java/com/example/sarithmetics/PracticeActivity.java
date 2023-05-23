package com.example.sarithmetics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class PracticeActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare buttons for accessing different activities
    private Button pracSettingsButton, playButton, shopButton;
    private ImageView settings;
    private SessionManager sessionManager;
    private int hearts = 3, time = 30000;
    private Set<String> selectedPowerups;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice);

        // Find the buttons in the layout file and set their click listeners
        pracSettingsButton = findViewById(R.id.pracsettings_button);
        playButton = findViewById(R.id.play_button);
        shopButton = findViewById(R.id.shop_button);
        pracSettingsButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
//        MyDatabaseHelper myDB = new MyDatabaseHelper(PracticeActivity.this);
//
//        myDB.registerAccount("Noah", "Rodriguez", "1234");
    }

    // Handle button clicks
    @Override
    public void onClick(View view) {
        Intent intent;
        try {
            if (view.getId() == R.id.pracsettings_button) {
                    // Go to Practice Settings
                    intent = new Intent(this, PracticeSettingsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android .R.anim.fade_out);
            } else if (view.getId() == R.id.play_button) {
                    // Go to EndlessGame activity
                    intent = new Intent(this, EndlessGameActivity.class);
                    intent.putExtra("resetPrefs", "trueReset"); // Set the reset condition to true
                    intent.putExtra("hearts", hearts);
                    intent.putExtra("time", time);
                    intent.putExtra("isAddEnabled", true);
                    intent.putExtra("isSubEnabled", true);
                    intent.putExtra("isMulEnabled", true);
                    intent.putExtra("isDivEnabled", true);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else if (view.getId() == R.id.shop_button) {
                    // Go to Settings activity
                    intent = new Intent(this, ShopActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        } catch (Exception e) {
            // Log the exception and stack trace
            Log.e("PracticeActivity", "Error navigating to activity: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}