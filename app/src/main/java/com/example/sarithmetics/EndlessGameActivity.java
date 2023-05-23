package com.example.sarithmetics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class EndlessGameActivity extends AppCompatActivity implements View.OnClickListener{
    // Declare buttons for accessing different activities
    private TextView txtQuestionNum, txtQuestion, txtCurrentScore;
    private RelativeLayout overlayLayout;
    private ProgressBar pbTime;
    private Button btn1, btn2, btn3, btn4;
    private ImageView imgResult, imgCountdown, pause;
    private int correctAnswer;
    private boolean isTimerRunning;
    private CountDownTimer timer;
    private int questionNum = 1;
    private ImageView heart1, heart2, heart3, heart4, heart5;
    private int remainingLives = 3;
    private int maxLives = remainingLives;
    private int score = 0;
    private boolean infiCheck = false;
    private int maxProgress = 100; // Maximum progress value
    private int duration = 30000; // Maximum duration in milliseconds
    private int maxDuration = duration;
    private int prevFirstNum = -1, prevSecondNum = -1;
    private boolean isAdditionEnabled, isSubtractionEnabled, isMultiplicationEnabled, isDivisionEnabled;
    private SessionManager sessionManager;
    private boolean isVigor, isSafeguard, isFrenzy, isMystery;
    private boolean safeguardEnable;
    private int btnIndex;
    private ImageView imgShield;
    private Set<String> selectedPowerups;
    private String hiddenAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endless);

        // Find the buttons in the layout file and set their click listeners
        overlayLayout = findViewById(R.id.overlayLayout);
        txtQuestionNum = findViewById(R.id.txtQuestionNum);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtCurrentScore = findViewById(R.id.txtCurrentScore);
        pbTime = findViewById(R.id.pbTime);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        pause = findViewById(R.id.pause_button);
        imgResult = findViewById(R.id.imgResult);
        imgCountdown = findViewById(R.id.imgCountdown);
        imgShield = findViewById(R.id.imgShield);
        heart1 = findViewById(R.id.heart_1);
        heart2 = findViewById(R.id.heart_2);
        heart3 = findViewById(R.id.heart_3);
        heart4 = findViewById(R.id.heart_4);
        heart5 = findViewById(R.id.heart_5);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        pause.setOnClickListener(this);
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        pause.setEnabled(false);

        sessionManager = new SessionManager(this);

        selectedPowerups = sessionManager.getPowerups();
        isVigor = selectedPowerups.contains("btnBuyVigor");
        isSafeguard = selectedPowerups.contains("btnBuySafeguard");
        isFrenzy = selectedPowerups.contains("btnBuyFrenzy");
        isMystery = selectedPowerups.contains("btnBuyMystery");

        SharedPreferences sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        Intent intent = getIntent();
            if (intent != null && intent.hasExtra("resetPrefs")) {
                String resetPrefs = intent.getStringExtra("resetPrefs");
                if (resetPrefs.equals("trueReset")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear(); // Clear all saved preferences
                    editor.apply();
                }
            }
            isAdditionEnabled = intent.getBooleanExtra("isAddEnabled", false);
            isSubtractionEnabled = intent.getBooleanExtra("isSubEnabled", false);
            isMultiplicationEnabled = intent.getBooleanExtra("isMulEnabled", false);
            isDivisionEnabled = intent.getBooleanExtra("isDivEnabled", false);
            maxLives = intent.getIntExtra("hearts", 0); // Retrieve the hearts value
            int lives = 0;
            if(intent.hasExtra("lives")) {
                lives = intent.getIntExtra("lives", 0);
            }
                heart5.setVisibility(View.GONE);
                heart4.setVisibility(View.GONE);
                heart3.setVisibility(View.GONE);
                heart2.setVisibility(View.GONE);
                heart1.setVisibility(View.GONE);

                for (int i = 0; i < maxLives; i++) {
                    switch (i) {
                        case 0:
                            heart1.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            heart2.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            heart3.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            heart4.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            heart5.setVisibility(View.VISIBLE);
                            break;
                    }
                }
                if(intent.hasExtra("lives")) {
                    switch (maxLives) {
                        case 5:
                            if (lives == 5) {
                                heart5.setImageResource(R.drawable.heart);
                            } else {
                                heart5.setImageResource(R.drawable.heart_broken);
                            }
                            if (lives >= 4) {
                                heart4.setImageResource(R.drawable.heart);
                            } else {
                                heart4.setImageResource(R.drawable.heart_broken);
                            }
                            // fall-through
                        case 4:
                            if (lives >= 4) {
                                heart4.setImageResource(R.drawable.heart);
                            } else {
                                heart4.setImageResource(R.drawable.heart_broken);
                            }
                            if (lives >= 3) {
                                heart3.setImageResource(R.drawable.heart);
                            } else {
                                heart3.setImageResource(R.drawable.heart_broken);
                            }
                            // fall-through
                        case 3:
                            if (lives >= 3) {
                                heart3.setImageResource(R.drawable.heart);
                            } else {
                                heart3.setImageResource(R.drawable.heart_broken);
                            }
                            if (lives >= 2) {
                                heart2.setImageResource(R.drawable.heart);
                            } else {
                                heart2.setImageResource(R.drawable.heart_broken);
                            }
                            break;
                        case 2:
                            if (lives >= 2) {
                                heart2.setImageResource(R.drawable.heart);
                            } else {
                                heart2.setImageResource(R.drawable.heart_broken);
                            }
                            if (lives >= 1) {
                                heart1.setImageResource(R.drawable.heart);
                            }
                            break;
                        case 1:
                            heart1.setImageResource(R.drawable.heart);
                            break;
                    }
                }
                System.out.println("check");
                remainingLives = maxLives;
                duration = intent.getIntExtra("time", 0); // Retrieve the time value
                if(duration == 1){
                    infiCheck = true;
                }
                maxDuration = duration;
        startCountdownAnimation();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onPause();
        Intent intent = new Intent();
        intent.putExtra("hearts", maxLives);
        startActivity(new Intent(this, PauseActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void saveGameState() {
        SharedPreferences sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("resetPrefs")) {
            String resetPrefs = intent.getStringExtra("resetPrefs");
            if (resetPrefs.equals("trueReset")) {
                editor.clear(); // Clear previous game state if the game is reset
            }
        }
        editor.putInt("questionNum", questionNum);
        editor.putInt("correctAnswer", correctAnswer);
        editor.putString("question", txtQuestion.getText().toString());
        editor.putInt("progress", pbTime.getProgress());
        editor.putInt("lives", remainingLives);
        editor.putInt("hearts", maxLives);
        editor.putInt("score", score);
        editor.putString("btn1Text", btn1.getText().toString());
        editor.putString("btn2Text", btn2.getText().toString());
        editor.putString("btn3Text", btn3.getText().toString());
        editor.putString("btn4Text", btn4.getText().toString());
        editor.putBoolean("btn1Enabled", btn1.isEnabled());
        editor.putBoolean("btn2Enabled", btn2.isEnabled());
        editor.putBoolean("btn3Enabled", btn3.isEnabled());
        editor.putBoolean("btn4Enabled", btn4.isEnabled());
        editor.putInt("duration", duration);
        editor.putInt("maxDuration", maxDuration);
        editor.putBoolean("isAdd", isAdditionEnabled);
        editor.putBoolean("isSub", isSubtractionEnabled);
        editor.putBoolean("isMul", isMultiplicationEnabled);
        editor.putBoolean("isDiv", isDivisionEnabled);
        editor.putBoolean("isInfinite", infiCheck);
        editor.putBoolean("isVigor", isVigor);
        editor.putBoolean("isSafeguard", isSafeguard);
        editor.putBoolean("isMystery", isMystery);
        editor.putString("hiddenAnswer", hiddenAnswer);
        // Save any other relevant game data

        // Commit the changes
        editor.apply();
    }

    private void loadSaveState(){
        // Retrieve the saved game state
        SharedPreferences sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);
            if (sharedPreferences.contains("questionNum")) {
                // Game state exists, resume the game
                questionNum = sharedPreferences.getInt("questionNum", 1);
                correctAnswer = sharedPreferences.getInt("correctAnswer", 0);
                // Retrieve any other relevant game data
                String question = sharedPreferences.getString("question", "");
                int progress = sharedPreferences.getInt("progress", 0);
                int lives = sharedPreferences.getInt("lives", 0);
                int hearts = sharedPreferences.getInt("hearts", 0);
                String btn1Text = sharedPreferences.getString("btn1Text", "");
                String btn2Text = sharedPreferences.getString("btn2Text", "");
                String btn3Text = sharedPreferences.getString("btn3Text", "");
                String btn4Text = sharedPreferences.getString("btn4Text", "");


                // Restore the game states
                score = sharedPreferences.getInt("score", 0);
                txtCurrentScore.setText("" + score);
                maxDuration = sharedPreferences.getInt("maxDuration", 30000);
                infiCheck = sharedPreferences.getBoolean("isInfinite", false);
                isVigor = sharedPreferences.getBoolean("isVigor", false);
                isSafeguard = sharedPreferences.getBoolean("isSafeguard", false);
                isMystery = sharedPreferences.getBoolean("isMystery", false);
                hiddenAnswer = sharedPreferences.getString("hiddenAnswer", "0");
                isAdditionEnabled = sharedPreferences.getBoolean("isAdd", false);
                isSubtractionEnabled = sharedPreferences.getBoolean("isSub", false);
                isMultiplicationEnabled = sharedPreferences.getBoolean("isMul", false);
                isDivisionEnabled = sharedPreferences.getBoolean("isDiv", false);
                duration = sharedPreferences.getInt("duration", 30000);
                txtQuestionNum.setText("#" + questionNum);
                txtQuestion.setText(question);
                pbTime.setProgress(progress);
                btn1.setText(btn1Text);
                btn2.setText(btn2Text);
                btn3.setText(btn3Text);
                btn4.setText(btn4Text);
                maxLives = hearts;
                remainingLives = lives;
                System.out.println("Making the visibilities");
                for (int i = 0; i < maxLives; i++) {
                    switch (i) {
                        case 0:
                            heart1.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            heart2.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            heart3.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            heart4.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            heart5.setVisibility(View.VISIBLE);
                            break;
                    }
                }
                switch (maxLives) {
                    case 5:
                        if (lives == 5) {
                            heart5.setImageResource(R.drawable.heart);
                        } else {
                            heart5.setImageResource(R.drawable.heart_broken);
                        }
                        if (lives >= 4) {
                            heart4.setImageResource(R.drawable.heart);
                        } else {
                            heart4.setImageResource(R.drawable.heart_broken);
                        }
                        // fall-through
                    case 4:
                        if (lives >= 4) {
                            heart4.setImageResource(R.drawable.heart);
                        } else {
                            heart4.setImageResource(R.drawable.heart_broken);
                        }
                        if (lives >= 3) {
                            heart3.setImageResource(R.drawable.heart);
                        } else {
                            heart3.setImageResource(R.drawable.heart_broken);
                        }
                        // fall-through
                    case 3:
                        if (lives >= 3) {
                            heart3.setImageResource(R.drawable.heart);
                        } else {
                            heart3.setImageResource(R.drawable.heart_broken);
                        }
                        if (lives >= 2) {
                            heart2.setImageResource(R.drawable.heart);
                        } else {
                            heart2.setImageResource(R.drawable.heart_broken);
                        }
                        break;
                    case 2:
                        if (lives >= 2) {
                            heart2.setImageResource(R.drawable.heart);
                        } else {
                            heart2.setImageResource(R.drawable.heart_broken);
                        }
                        if (lives >= 1) {
                            heart1.setImageResource(R.drawable.heart);
                        }
                        break;
                    case 1:
                        heart1.setImageResource(R.drawable.heart);
                        break;
                }

                // Resume the game from where it left off
                resumeGame();
            } else {
                // No saved game state, start a new game
                startNewGame();
            }
        }

    private void startCountdownAnimation() {
        overlayLayout.setVisibility(View.VISIBLE);
        overlayLayout.setBackgroundColor(Color.parseColor("#80000000")); // Black overlay with 50% opacity

        imgCountdown.setVisibility(View.VISIBLE);
        imgCountdown.setImageResource(R.drawable.countdown_3);
        imgCountdown.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        int fadeInDuration = 1000; // Duration for each fade-in animation
        int fadeOutDuration = 500; // Duration for each fade-out animation

        // Array of countdown drawable resources
        final int[] countdownDrawables = {
                R.drawable.countdown_3,
                R.drawable.countdown_2,
                R.drawable.countdown_1,
                R.drawable.countdown_start
        };

        // Start the countdown animation
        animateCountdownImage(imgCountdown, countdownDrawables, 0, fadeInDuration, fadeOutDuration, new Runnable() {
            @Override
            public void run() {
                // Animation complete
                overlayLayout.setVisibility(View.GONE);
                imgCountdown.setVisibility(View.GONE);
                loadSaveState();
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
                btn4.setEnabled(true);
                pause.setEnabled(true);
            }
        });
    }

    private void animateCountdownImage(final ImageView imageView, final int[] countdownDrawables,
                                       final int index, final int fadeInDuration, final int fadeOutDuration,
                                       final Runnable onAnimationComplete) {
        if (index >= countdownDrawables.length) {
            // All countdown animations complete
            if (onAnimationComplete != null) {
                onAnimationComplete.run();
            }
            return;
        }

        final boolean isLastImage = (index == countdownDrawables.length - 1);

        // Set the next countdown image
        imageView.setImageResource(countdownDrawables[index]);

        // Fade-in animation
        imageView.setAlpha(0.0f);
        imageView.animate().alpha(1.0f).setDuration(fadeInDuration).withEndAction(new Runnable() {
            @Override
            public void run() {
                // Fade-out animation
                if (isLastImage) {
                    // Last image, wait for the specified duration and then execute the onAnimationComplete runnable
                    imageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView.animate().alpha(0.0f).setDuration(fadeOutDuration).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    if (onAnimationComplete != null) {
                                        onAnimationComplete.run();
                                    }
                                }
                            }).start();
                        }
                    }, fadeOutDuration);
                } else {
                    imageView.animate().alpha(0.0f).setDuration(fadeOutDuration).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            // Animate the next countdown image recursively
                            animateCountdownImage(imageView, countdownDrawables, index + 1, fadeInDuration, fadeOutDuration, onAnimationComplete);
                        }
                    }).start();
                }
            }
        }).start();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pause_button) {
            // Pause the game and start the PauseActivity
            onPause();
            Intent intent = new Intent(this, PauseActivity.class);
            intent.putExtra("hearts", maxLives);
            intent.putExtra("lives", remainingLives);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            int selectedAnswer = 0;
            if (view.getId() == R.id.btn1) {
                String buttonText = btn1.getText().toString();
                if (buttonText.equals("?")) {
                    selectedAnswer = Integer.parseInt(hiddenAnswer);
                } else {
                    selectedAnswer = Integer.parseInt(buttonText);
                }
                btnIndex = 1;
            } else if (view.getId() == R.id.btn2) {
                String buttonText = btn2.getText().toString();
                if (buttonText.equals("?")) {
                    selectedAnswer = Integer.parseInt(hiddenAnswer);
                } else {
                    selectedAnswer = Integer.parseInt(buttonText);
                }
                btnIndex = 2;
            } else if (view.getId() == R.id.btn3) {
                String buttonText = btn3.getText().toString();
                if (buttonText.equals("?")) {
                    selectedAnswer = Integer.parseInt(hiddenAnswer);
                } else {
                    selectedAnswer = Integer.parseInt(buttonText);
                }
                btnIndex = 3;
            } else if (view.getId() == R.id.btn4) {
                String buttonText = btn4.getText().toString();
                if (buttonText.equals("?")) {
                    selectedAnswer = Integer.parseInt(hiddenAnswer);
                } else {
                    selectedAnswer = Integer.parseInt(buttonText);
                }
                btnIndex = 4;
            }

            if (safeguardEnable && isMystery) {
                checkAnswerWithSafeguard(selectedAnswer);
            } else if (safeguardEnable) {
                    checkAnswerWithSafeguard(selectedAnswer);
            } else {
                checkAnswer(selectedAnswer);
            }

        }
    }

    private void nextQuestion() {
        questionNum++;
        txtQuestionNum.setText("#" + questionNum);
        if (duration > 500) {
            duration -= 200;
        }

        if(isVigor && (questionNum % 10 == 0)){
            vigorEffect();
        }

        if(isSafeguard){
            SecureRandom random = new SecureRandom();
            int chance = random.nextInt(100);
            if((maxLives == 5 && remainingLives == 5) || (maxLives == 4 && remainingLives == 4) || (maxLives == 3 && remainingLives == 3)){
                if(chance > 97){
                    safeguardEnable = true;
                }else{
                    safeguardEnable = false;
                }
            } else if((maxLives == 5 && remainingLives == 4) || (maxLives == 4 && remainingLives == 3) || (maxLives == 3 && remainingLives == 2)){
                if(chance > 85){
                    safeguardEnable = true;
                }else{
                    safeguardEnable = false;
                }
            } else if((maxLives == 5 && remainingLives == 3) || (maxLives == 4 && remainingLives == 2) || (maxLives == 2 && remainingLives == 2)){
                if(chance > 73){
                    safeguardEnable = true;
                }else{
                    safeguardEnable = false;
                }
            } else {
                if(chance > 60){
                    safeguardEnable = true;
                }else{
                    safeguardEnable = false;
                }
            }
            imgShield.setVisibility(View.GONE);
            if(safeguardEnable){
                imgShield.setVisibility(View.VISIBLE);
            }
        }

        String text = txtQuestion.getText().toString();

        // Remove any whitespace from the text
        text = text.replaceAll("\\s", "");

        // Extract the numbers using StringTokenizer
        StringTokenizer tokenizer = new StringTokenizer(text, "+-×÷");
        String number1String = tokenizer.nextToken();
        String number2String = tokenizer.nextToken();

        // Convert the strings to integers
        prevFirstNum = Integer.parseInt(number1String);
        prevSecondNum = Integer.parseInt(number2String);
        System.out.println("first:" + prevFirstNum + " second: " + prevSecondNum);
    }


    private void generateQuestion() {
        pbTime.setProgress(100);

        SecureRandom random = new SecureRandom();
        int number1, number2, operation;

        List<Integer> availableOperations = new ArrayList<>();
        if (isAdditionEnabled) availableOperations.add(1);
        if (isSubtractionEnabled) availableOperations.add(2);
        if (isMultiplicationEnabled) availableOperations.add(3);
        if (isDivisionEnabled) availableOperations.add(4);

        if (availableOperations.isEmpty()) {
            // No enabled operations, do something or show an error message
            return;
        }

        operation = availableOperations.get(random.nextInt(availableOperations.size()));

        if (operation == 1) {
            // Addition
            do {
                number1 = random.nextInt(5 + (int) Math.ceil(questionNum / 5)) + (int) Math.ceil(questionNum / 5);
                number2 = random.nextInt(5 + (int) Math.ceil(questionNum / 5)) + (int) Math.ceil(questionNum / 10);
                correctAnswer = number1 + number2;
            } while (correctAnswer > 999);

            txtQuestion.setText(number1 + " + " + number2);
        } else if (operation == 2) {
            // Subtraction
            do {
                number2 = random.nextInt(5 + (int) Math.ceil(questionNum / 5)) + (int) Math.ceil(questionNum / 5);
                number1 = random.nextInt(5 + (int) Math.ceil(questionNum / 5)) + number2; // Ensure first number is greater than or equal to the second number
                correctAnswer = number1 - number2;
            } while (correctAnswer > 999);

            txtQuestion.setText(number1 + " - " + number2);
        } else if (operation == 3) {
            // Multiplication
            do {
                number1 = random.nextInt(5 + (int) Math.ceil(questionNum / 10)) + (int) Math.ceil(questionNum / 20);
                number2 = random.nextInt(10) + (int) Math.ceil(questionNum / 25);
                correctAnswer = number1 * number2;
            } while (correctAnswer > 999);

            txtQuestion.setText(number1 + " × " + number2);
        } else {
            // Division
            do {
                number2 = random.nextInt(10) + (int) Math.ceil(questionNum / 25) + 1;
                int divisor = random.nextInt(5 + (int) Math.ceil(questionNum / 10)) + (int) Math.ceil(questionNum / 20) + 1;
                number1 = number2 * divisor; // Ensure division results in an integer quotient
                correctAnswer = number1 / number2;
            } while (correctAnswer > 999);

            txtQuestion.setText(number1 + " ÷ " + number2);
        }

        if(number1 == prevFirstNum || number2 == prevSecondNum || number1 == prevSecondNum || number2 == prevFirstNum){
            generateQuestion(); // Ensure that previous numbers are not the same
        }

        int[] answerOptions = generateAnswerOptions(correctAnswer);
        btn1.setText(String.valueOf(answerOptions[0]));
        btn2.setText(String.valueOf(answerOptions[1]));
        btn3.setText(String.valueOf(answerOptions[2]));
        btn4.setText(String.valueOf(answerOptions[3]));
        if (isMystery) {
            int toHide = random.nextInt(4) + 1; // Generate a random number between 1 and 4 (inclusive) to determine which button to hide

            switch (toHide) {
                case 1:
                    hiddenAnswer = String.valueOf(answerOptions[0]); // Save the original answer for later use
                    btn1.setText("?");
                    break;
                case 2:
                    hiddenAnswer = String.valueOf(answerOptions[1]);
                    btn2.setText("?");
                    break;
                case 3:
                    hiddenAnswer = String.valueOf(answerOptions[2]);
                    btn3.setText("?");
                    break;
                case 4:
                    hiddenAnswer = String.valueOf(answerOptions[3]);
                    btn4.setText("?");
                    break;
            }
        }


        imgResult.setVisibility(View.INVISIBLE);
    }


    private int[] generateAnswerOptions(int correctAnswer) {
        SecureRandom random = new SecureRandom();
        int[] options = new int[4];
        options[0] = correctAnswer;

        Set<Integer> usedNumbers = new HashSet<>();
        usedNumbers.add(correctAnswer);

        int range = 10; // range based on how close the answer options to be

        for (int i = 1; i < options.length; i++) {
            int wrongAnswer = correctAnswer;

            while (usedNumbers.contains(wrongAnswer) || wrongAnswer == correctAnswer) {
                int offset = random.nextInt(range) + 1; // Generate a random offset within the specified range

                // Decide whether to add or subtract the offset from the correct answer
                if (random.nextBoolean()) {
                    wrongAnswer = correctAnswer + offset;
                } else {
                    wrongAnswer = correctAnswer - offset;
                }

                // Ensure the wrong answer is within a valid range (e.g., not negative)
                int highOffset = correctAnswer + 10;
                int initialLowOffset = correctAnswer - 10;
                int finalLowOffset = initialLowOffset < 0 ? 0 : initialLowOffset;
                if (wrongAnswer < finalLowOffset || wrongAnswer > highOffset) {
                    wrongAnswer = random.nextInt(highOffset) + finalLowOffset; // Generate a random wrong answer if the offset exceeds the valid range
                }
            }

            options[i] = wrongAnswer;
            usedNumbers.add(wrongAnswer);
        }

        shuffleArray(options);
        return options;
    }


    private void shuffleArray(int[] array) {
        SecureRandom random = new SecureRandom();

        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private void checkAnswer(int selectedAnswer) {
        resetTimer();
        if (selectedAnswer == correctAnswer) {
            imgResult.setImageResource(R.drawable.check);
            if(!isFrenzy && !isMystery) {
                score += 10;
            } else if (isFrenzy && isMystery) {
                score += 40;
            } else if (isFrenzy || isMystery){
                score += 20;
            }
            txtCurrentScore.setText("" + score);
        } else {
            imgResult.setImageResource(R.drawable.wrong);
            wrongAnswer();
        }

        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        pause.setEnabled(false);

        imgResult.setVisibility(View.VISIBLE);

        if(remainingLives != 0) {
            // Wait for a few seconds and then generate the next question
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextQuestion();
                    generateQuestion();
                    startTimer();
                    btn1.setEnabled(true);
                    btn2.setEnabled(true);
                    btn3.setEnabled(true);
                    btn4.setEnabled(true);
                    pause.setEnabled(true);
                }
            }, 1000); // 2 seconds delay

            // Reset the timer on answer selection
            resetTimer();
        }
    }

    private void checkAnswerWithSafeguard(int selectedAnswer) {
        if (selectedAnswer == correctAnswer) {
            checkAnswer(selectedAnswer);
        } else {
            if(btnIndex == 1){
                btn1.setEnabled(false);
            } else if(btnIndex == 2){
                btn2.setEnabled(false);
            } else if(btnIndex == 3){
                btn3.setEnabled(false);
            } else {
                btn4.setEnabled(false);
            }
            safeguardEnable = false;
            imgShield.setVisibility(View.GONE);
        }
    }

    private void wrongAnswer() {
        remainingLives--;

        ImageView[] heartImages = {heart1, heart2, heart3, heart4, heart5};

        if (remainingLives >= 0 && remainingLives < maxLives) {
            heartImages[remainingLives].setImageResource(R.drawable.heart_broken);
        }

        if (remainingLives == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // No more lives remaining, start GameOver activity
                    selectedPowerups = sessionManager.getPowerups();
                    selectedPowerups.clear();
                    Intent intent = new Intent(EndlessGameActivity.this, GameOverActivity.class);
                    intent.putExtra("isAddEnabled", isAdditionEnabled);
                    intent.putExtra("isSubEnabled", isSubtractionEnabled);
                    intent.putExtra("isMulEnabled", isMultiplicationEnabled);
                    intent.putExtra("isDivEnabled", isDivisionEnabled);
                    intent.putExtra("lastQuestion", score);
                    intent.putExtra("hearts", maxLives);
                    intent.putExtra("time", maxDuration);
                    startActivity(intent);
                    finish(); // Finish current activity to prevent returning back
                }
            }, 1000); // 2 seconds delay
        }
    }

    private void vigorEffect() {

        if(remainingLives == 1){
            heart2.setImageResource(R.drawable.heart);
        } else if(remainingLives == 2){
            heart3.setImageResource(R.drawable.heart);
        } else if(remainingLives == 3){
            heart4.setImageResource(R.drawable.heart);
        } else if(remainingLives == 4){
            heart5.setImageResource(R.drawable.heart);
        }
        remainingLives++;
    }

    private void startTimer() {
        if(infiCheck == true){
            return;
        }

        if (isTimerRunning) {
            timer.cancel();
        }

        final int interval = 100; // Interval for progress update in milliseconds

        int remainingTime = duration;
        if (pbTime.getProgress() == 0) {
            wrongAnswer();
        } else {
            remainingTime = (pbTime.getProgress() * duration) / maxProgress;
        }

        pbTime.setMax(maxProgress); // Set the maximum progress value

        timer = new CountDownTimer(remainingTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Calculate the progress value based on the remaining time
                int progress = (int) ((millisUntilFinished * maxProgress) / duration);
                pbTime.setProgress(progress);
            }

            @Override
            public void onFinish() {
                // Timer finished, show the x image and proceed to the next question
                imgResult.setImageResource(R.drawable.wrong);
                imgResult.setVisibility(View.VISIBLE);
                wrongAnswer();
                // Check if the progress bar has reached zero
                    // Wait for a few seconds and then generate the next question
                if(remainingLives != 0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nextQuestion();
                            generateQuestion();
                            startTimer();
                        }
                    }, 1000); // 2 seconds delay
                }
            }
        };

        timer.start();
        isTimerRunning = true;
    }

    private void resetTimer() {
        if (isTimerRunning) {
            timer.cancel();
            isTimerRunning = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveGameState();
        if(!infiCheck) {
            timer.cancel();
        }
    }

    private void resumeGame() {
        startTimer();
    }

    private void startNewGame() {
        if(!isFrenzy) {
            questionNum = 1;
        }else{
            questionNum = 100;
            while (duration >= 0){
                duration-=200;
            }
            if (duration < 5000){
                duration = 5000;
            }
        }
        txtQuestionNum.setText("#" + questionNum);
        txtCurrentScore.setText("0");
        pbTime.setProgress(100);
        startTimer();
        generateQuestion();
    }
}

