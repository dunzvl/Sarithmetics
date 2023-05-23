package com.example.sarithmetics;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PracticeSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare buttons for accessing different activities
    private Button plus, minus, cross, divide, addHeart, subHeart, addTime, subTime, playButton, pracSettingsButton;
    private ImageView plusSign, minusSign, crossSign, divideSign, heart1, heart2, heart3, heart4, heart5;
    private LinearLayout parent_layout;
    private TextView timer, textHighScore;
    private int hearts = 3, time = 30000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_settings);

        // Find the buttons in the layout file and set their click listeners
        plus = findViewById(R.id.plus_button);
        minus = findViewById(R.id.minus_button);
        cross = findViewById(R.id.cross_button);
        divide = findViewById(R.id.divide_button);
        addHeart = findViewById(R.id.addheart_button);
        subHeart = findViewById(R.id.subheart_button);
        addTime = findViewById(R.id.addtime_button);
        subTime = findViewById(R.id.subtime_button);
        plusSign = findViewById(R.id.plusSign);
        minusSign = findViewById(R.id.minusSign);
        crossSign = findViewById(R.id.crossSign);
        divideSign = findViewById(R.id.divideSign);
        playButton = findViewById(R.id.play_button);
        pracSettingsButton = findViewById(R.id.pracsettings_button);
        parent_layout = findViewById(R.id.sign_layout);
        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);
        heart4 = findViewById(R.id.heart4);
        heart5 = findViewById(R.id.heart5);
        timer = findViewById(R.id.txtTime);
        textHighScore = findViewById(R.id.textHighScore);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        cross.setOnClickListener(this);
        divide.setOnClickListener(this);
        addHeart.setOnClickListener(this);
        subHeart.setOnClickListener(this);
        addTime.setOnClickListener(this);
        subTime.setOnClickListener(this);
        playButton.setOnClickListener(this);
        pracSettingsButton.setOnClickListener(this);

        MyDatabaseHelper myDB = new MyDatabaseHelper(PracticeSettingsActivity.this);
        textHighScore.setText(String.valueOf(myDB.displayHighScore()));
    }


    // Handle button clicks
    @Override
    public void onClick(View view) {
        Intent intent;
        try {
            if (view.getId() == R.id.plus_button) {
                    toggleVisibility(plusSign);
            } else if (view.getId() == R.id.minus_button) {
                    toggleVisibility(minusSign);
            } else if (view.getId() == R.id.cross_button) {
                    toggleVisibility(crossSign);
            } else if (view.getId() == R.id.divide_button) {
                    toggleVisibility(divideSign);
            } else if (view.getId() == R.id.play_button) {
                    // Go to EndlessGame activity
                    intent = new Intent(this, EndlessGameActivity.class);
                    intent.putExtra("resetPrefs", "trueReset"); // Set the reset condition to true
                    intent.putExtra("hearts", hearts);
                    if(time==0){
                        time = 1;
                    }
                    intent.putExtra("time", time);
                    if (plusSign.getVisibility() == View.VISIBLE) {
                        intent.putExtra("isAddEnabled", true);
                    }
                    if (minusSign.getVisibility() == View.VISIBLE) {
                        intent.putExtra("isSubEnabled", true);
                    }
                    if (crossSign.getVisibility() == View.VISIBLE) {
                        intent.putExtra("isMulEnabled", true);
                    }
                    if (divideSign.getVisibility() == View.VISIBLE) {
                        intent.putExtra("isDivEnabled", true);
                    }
                    System.out.println(hearts);
                    System.out.println(time);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else if (view.getId() == R.id.pracsettings_button) {
                    toggleVisibility(plus);
                    toggleVisibility(minus);
                    toggleVisibility(cross);
                    toggleVisibility(divide);
                    toggleVisibility(addHeart);
                    toggleVisibility(subHeart);
                    toggleVisibility(addTime);
                    toggleVisibility(subTime);
            } else if (view.getId() == R.id.addheart_button) {
                    if (hearts < 5) {
                        hearts++;
                        toggleHeartVisibility(hearts, true);
                    }
            } else if (view.getId() == R.id.subheart_button) {
                    if (hearts > 1) {
                        hearts--;
                        toggleHeartVisibility(hearts, false);
                    }
            } else if (view.getId() == R.id.addtime_button) {
                    if (time >= 60000) {
                        time = 0;
                        timer.setText("∞");
                    } else if (time < 60000){
                        time += 5000;
                        timer.setText((time / 1000) + "s");
                    }
            } else if (view.getId() == R.id.subtime_button) {
                    if (time == 0) {
                        time = 60000;
                        timer.setText((time / 1000) + "s");
                    } else if (time > 0) {
                        time -= 5000;
                        if(time == 0){
                            timer.setText("∞");
                        } else {
                            timer.setText((time / 1000) + "s");
                        }
                    }
            }

            if (plusSign.getVisibility() == View.GONE) {
                plus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#50FF6B6B")));
            } else {
                plus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF6B6B")));
            }
            if (minusSign.getVisibility() == View.GONE) {
                minus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#50EE9A4D")));
            } else {
                minus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE9A4D")));
            }
            if (crossSign.getVisibility() == View.GONE) {
                cross.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5051D280")));
            } else {
                cross.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#51D280")));
            }
            if (divideSign.getVisibility() == View.GONE) {
                divide.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#506BB7FE")));
            } else {
                divide.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6BB7FE")));
            }

            if (plusSign.getVisibility() == View.GONE && minusSign.getVisibility() == View.GONE &&
                    crossSign.getVisibility() == View.GONE && divideSign.getVisibility() == View.GONE) {
                playButton.setEnabled(false);
                playButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#507B3F18")));
            } else {
                playButton.setEnabled(true);
                playButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7B3F18")));
            }

        } catch (Exception e) {
            // Log the exception and stack trace
            Log.e("PracticeActivity", "Error navigating to activity: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void toggleVisibility(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void toggleHeartVisibility(int maxHearts, boolean isAdd) {
        if(isAdd) {
            if (hearts == 2) {
                toggleVisibility(heart4);
            } else if(hearts == 3){
                toggleVisibility(heart3);
            } else if(hearts == 4){
                toggleVisibility(heart2);
            } else if(hearts == 5){
                toggleVisibility(heart1);
            }
        } else {
            if (hearts == 4) {
                toggleVisibility(heart1);
            } else if(hearts == 3){
                toggleVisibility(heart2);
            } else if(hearts == 2){
                toggleVisibility(heart3);
            } else if(hearts == 1){
                toggleVisibility(heart4);
            }
        }
    }


}