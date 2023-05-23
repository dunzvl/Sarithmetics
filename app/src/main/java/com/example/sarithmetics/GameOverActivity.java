package com.example.sarithmetics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare buttons for accessing different activities
    private Button playagainButton, gobackButton;
    private ImageView settings;
    private TextView txtScore, txtHighScore;
    private int lastQuestion, maxLives, maxDuration;
    private boolean isAdditionEnabled, isSubtractionEnabled, isMultiplicationEnabled, isDivisionEnabled;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        // Find the buttons in the layout file and set their click listeners
        playagainButton = findViewById(R.id.playagain_button);
        gobackButton = findViewById(R.id.goback_button);
        txtScore = findViewById(R.id.txtScore);
        txtHighScore = findViewById(R.id.txtHighScore);
        playagainButton.setOnClickListener(this);
        gobackButton.setOnClickListener(this);

        MyDatabaseHelper myDB = new MyDatabaseHelper(GameOverActivity.this);

        lastQuestion = getIntent().getIntExtra("lastQuestion", 0);
        txtScore.setText(String.valueOf(lastQuestion));
        score = lastQuestion;
        if(score > myDB.displayHighScore()){
            txtHighScore.setText(String.valueOf(lastQuestion));
            myDB.updateHighScore(score);
        }else{
            txtHighScore.setText(String.valueOf(myDB.displayHighScore()));
        }

        Intent intent = getIntent();
        isAdditionEnabled = intent.getBooleanExtra("isAddEnabled", false);
        isSubtractionEnabled = intent.getBooleanExtra("isSubEnabled", false);
        isMultiplicationEnabled = intent.getBooleanExtra("isMulEnabled", false);
        isDivisionEnabled = intent.getBooleanExtra("isDivEnabled", false);
        maxLives = intent.getIntExtra("hearts", 0); // Retrieve the hearts value
        maxDuration = intent.getIntExtra("time", 0); // Retrieve the time value

        myDB.updateCoins(score);
    }

    // Handle button clicks
    @Override
    public void onClick(View view) {
        Intent intent;
        try {
            if (view.getId() == R.id.playagain_button) {
                    // Go to Shop activity
                    intent = new Intent(this, EndlessGameActivity.class);
                    intent.putExtra("resetPrefs", "trueReset"); // Set the reset condition to true
                    intent.putExtra("isAddEnabled", isAdditionEnabled);
                    intent.putExtra("isSubEnabled", isSubtractionEnabled);
                    intent.putExtra("isMulEnabled", isMultiplicationEnabled);
                    intent.putExtra("isDivEnabled", isDivisionEnabled);
                    intent.putExtra("hearts", maxLives);
                    intent.putExtra("time", maxDuration);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else if (view.getId() == R.id.goback_button) {
                    // Go to Shop activity
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
        startActivity(new Intent(this, PracticeActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}