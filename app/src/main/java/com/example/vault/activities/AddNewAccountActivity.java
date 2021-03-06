package com.example.vault.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.vault.R;
import com.example.vault.data.Model;
import com.example.vault.data.UserAccount;


public class AddNewAccountActivity extends Activity {

    private Model model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.getInstance(this);
        setUpLayout();
    }

    private void setUpLayout() {
        setContentView(R.layout.add_account_popup_window);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .6), (int) (height * .6));
        createViews();
    }

    private void createViews() {
        EditText usernameET = findViewById(R.id.create_username_et);
        EditText passwordET = findViewById(R.id.create_password_et);
        EditText accountTypeET = findViewById(R.id.account_type_et);
        Button createAccountButton = findViewById(R.id.create_account_button);
        createAccountButton.setOnClickListener(v -> {
            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            String accountType = accountTypeET.getText().toString();
            //hash password here
            UserAccount userAccount = new UserAccount(username, password, accountType);
            model.addNewUser(userAccount, getApplicationContext());
            Intent restartHomeActivityIntent = new Intent(AddNewAccountActivity.this, MainActivity.class);
            startActivity(restartHomeActivityIntent);
            finish();
        });
    }

}
