package com.example.vault.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.vault.R;
import com.example.vault.data.Encrypter;

public class CreatePinActivity extends VaultAppActivity {

    final String VALID_PIN_PATTERN = "\\d\\d\\d\\d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpLayout();
    }

    private void setUpLayout() {
        EditText enterPIN = findViewById(R.id.cp_enter_pin_et);
        EditText confirmPIN = findViewById(R.id.cp_confirm_pin_et);

        Button createButton = findViewById(R.id.cp_create_button);
        createButton.setOnClickListener(v -> {
            String pin1 = enterPIN.getText().toString();
            String pin2 = confirmPIN.getText().toString();

            if (isValidPIN(pin1, pin2)) {
                savePIN(pin1);
                restartApp();
            }
        });
    }

    private boolean isValidPIN(String pin1, String pin2) {
        TextView feedbackTV = findViewById(R.id.user_feedback_tv);

        // eliminate white space
        pin1 = pin1.trim();
        pin2 = pin2.trim();

        if (!pin1.equals(pin2)) {
            feedbackTV.setTextColor(Color.RED);
            feedbackTV.setText(R.string.create_pin_no_match);
            return false;
        }

        if (!pin1.matches(VALID_PIN_PATTERN)) {
            feedbackTV.setTextColor(Color.RED);
            feedbackTV.setText(R.string.create_pin_invalid_pattern);
            return false;
        }
        return true;
    }

    private void savePIN(String pin) {
        String encryptedPIN =  Integer.toString(Encrypter.encryptPIN(pin));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.user_pin_key), encryptedPIN);
        editor.apply();
    }

    private void restartApp() {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }

}