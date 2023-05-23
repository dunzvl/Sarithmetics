package com.example.sarithmetics;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare buttons for accessing different activities
    private Button vigorButton, safeguardButton, frenzyButton, mysteryButton;
    private TextView coins;
    private RelativeLayout overlayLayout;
    private PopupWindow popupWindow;
    private SessionManager sessionManager;
    private Set<String> selectedPowerups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        sessionManager = new SessionManager(this);

        // Find the buttons in the layout file and set their click listeners
        vigorButton = findViewById(R.id.vigor_button);
        safeguardButton = findViewById(R.id.safeguard_button);
        frenzyButton= findViewById(R.id.frenzy_button);
        mysteryButton = findViewById(R.id.mystery_button);
        overlayLayout = findViewById(R.id.overlayLayout2);
        coins = findViewById(R.id.txtCoins);
        vigorButton.setOnClickListener(this);
        safeguardButton.setOnClickListener(this);
        frenzyButton.setOnClickListener(this);
        mysteryButton.setOnClickListener(this);
        MyDatabaseHelper myDB = new MyDatabaseHelper(ShopActivity.this);
        int numCoins = myDB.displayCoins();
        coins.setText("" + numCoins);
        selectedPowerups = sessionManager.getPowerups();

        if(selectedPowerups.contains("btnBuyVigor")){
            vigorButton.setEnabled(false);
        }else{
            vigorButton.setEnabled(true);
        }

        if(selectedPowerups.contains("btnBuySafeguard")){
            safeguardButton.setEnabled(false);
        }else{
            safeguardButton.setEnabled(true);
        }

        if(selectedPowerups.contains("btnBuyFrenzy")){
            frenzyButton.setEnabled(false);
        }else{
            frenzyButton.setEnabled(true);
        }

        if(selectedPowerups.contains("btnBuyMystery")){
            mysteryButton.setEnabled(false);
        }else{
            mysteryButton.setEnabled(true);
        }

    }

    // Handle button clicks
    @Override
    public void onClick(View view) {
        Intent intent;
        try {
            if (view.getId() == R.id.vigor_button) {
                    showVigorPopup();
            } else if (view.getId() == R.id.safeguard_button) {
                    showSafeguardPopup();
            } else if (view.getId() == R.id.frenzy_button) {
                    showFrenzyPopup();
            } else if (view.getId() == R.id.mystery_button) {
                    showMysteryPopup();
            }
        } catch (Exception e) {
            // Log the exception and stack trace
            Log.e("PracticeActivity", "Error navigating to activity: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void showVigorPopup() {
        // Create a dialog object
        final Dialog popupDialog = new Dialog(this);
        final Dialog missingDialog = new Dialog(this);

        // Set the custom layout for the dialog
        popupDialog.setContentView(R.layout.vigor_popup);
        missingDialog.setContentView(R.layout.notenough_popup);

        // Find the close button inside the popup layout
        Button closeButton = popupDialog.findViewById(R.id.btnBuyVigor);

        // Create an instance of the SessionManager
        SessionManager sessionManager = new SessionManager(this);

        // Set a click listener for the close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/close the popup dialog
                MyDatabaseHelper myDB = new MyDatabaseHelper(ShopActivity.this);
                if (myDB.updateCoins(-50)) {
                    // Dismiss the popup dialog
                    popupDialog.dismiss();
                    String buttonId = getResources().getResourceEntryName(v.getId());
                    sessionManager.addPowerups(buttonId);
                    int numCoins = myDB.displayCoins();
                    coins.setText("" + numCoins);
                    vigorButton.setEnabled(false);
                } else {
                    // Show the missingDialog dialog
                    missingDialog.show();
                    // Delay dismissal of missingDialog dialog after 3 seconds
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            missingDialog.dismiss();
                        }
                    }, 3000);
                    // Dismiss the popup dialog
                    popupDialog.dismiss();
                }
            }
        });

        // Show the popup dialog
        popupDialog.show();
    }

    private void showSafeguardPopup() {
        // Create a dialog object
        final Dialog popupDialog = new Dialog(this);
        final Dialog missingDialog = new Dialog(this);

        // Set the custom layout for the dialog
        popupDialog.setContentView(R.layout.safeguard_popup);
        missingDialog.setContentView(R.layout.notenough_popup);

        // Find the close button inside the popup layout
        Button closeButton = popupDialog.findViewById(R.id.btnBuySafeguard);

        // Set a click listener for the close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/close the popup dialog
                MyDatabaseHelper myDB = new MyDatabaseHelper(ShopActivity.this);
                if (myDB.updateCoins(-50)) {
                    // Dismiss the popup dialog
                    popupDialog.dismiss();
                    String buttonId = getResources().getResourceEntryName(v.getId());
                    sessionManager.addPowerups(buttonId);
                    int numCoins = myDB.displayCoins();
                    coins.setText("" + numCoins);
                    safeguardButton.setEnabled(false);
                } else {
                    // Show the missingDialog dialog
                    missingDialog.show();

                    // Delay dismissal of missingDialog dialog after 3 seconds
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            missingDialog.dismiss();
                        }
                    }, 3000);
                    // Dismiss the popup dialog
                    popupDialog.dismiss();
                }
            }
        });

        // Show the popup dialog
        popupDialog.show();
    }

    private void showFrenzyPopup() {
        // Create a dialog object
        final Dialog popupDialog = new Dialog(this);
        final Dialog missingDialog = new Dialog(this);

        // Set the custom layout for the dialog
        popupDialog.setContentView(R.layout.frenzy_popup);
        missingDialog.setContentView(R.layout.notenough_popup);

        // Find the close button inside the popup layout
        Button closeButton = popupDialog.findViewById(R.id.btnBuyFrenzy);

        // Set a click listener for the close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/close the popup dialog
                MyDatabaseHelper myDB = new MyDatabaseHelper(ShopActivity.this);
                if (myDB.updateCoins(-50)) {
                    // Dismiss the popup dialog
                    popupDialog.dismiss();
                    String buttonId = getResources().getResourceEntryName(v.getId());
                    sessionManager.addPowerups(buttonId);
                    int numCoins = myDB.displayCoins();
                    coins.setText("" + numCoins);
                    frenzyButton.setEnabled(false);
                } else {
                    // Show the missingDialog dialog
                    missingDialog.show();

                    // Delay dismissal of missingDialog dialog after 3 seconds
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            missingDialog.dismiss();
                        }
                    }, 3000);
                    // Dismiss the popup dialog
                    popupDialog.dismiss();
                }
            }
        });

        // Show the popup dialog
        popupDialog.show();
    }

    private void showMysteryPopup() {
        // Create a dialog object
        final Dialog popupDialog = new Dialog(this);
        final Dialog missingDialog = new Dialog(this);

        // Set the custom layout for the dialog
        popupDialog.setContentView(R.layout.mystery_popup);
        missingDialog.setContentView(R.layout.notenough_popup);

        // Find the close button inside the popup layout
        Button closeButton = popupDialog.findViewById(R.id.btnBuyMystery);

        // Set a click listener for the close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/close the popup dialog
                MyDatabaseHelper myDB = new MyDatabaseHelper(ShopActivity.this);
                if (myDB.updateCoins(-50)) {
                    // Dismiss the popup dialog
                    String buttonId = getResources().getResourceEntryName(v.getId());
                    sessionManager.addPowerups(buttonId);
                    popupDialog.dismiss();
                    int numCoins = myDB.displayCoins();
                    coins.setText("" + numCoins);
                    mysteryButton.setEnabled(false);
                } else {
                    // Show the missingDialog dialog
                    missingDialog.show();

                    // Delay dismissal of missingDialog dialog after 3 seconds
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            missingDialog.dismiss();
                        }
                    }, 3000);
                    // Dismiss the popup dialog
                    popupDialog.dismiss();
                }
            }
        });

        // Show the popup dialog
        popupDialog.show();
    }
    public void onBackPressed() {
        startActivity(new Intent(this, PracticeActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}