package com.example.whatsappandroid.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.utilities.Info;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private FloatingActionButton register;
    private Button loginBtn;
    private TextInputLayout username;
    private TextInputLayout password;
    private FloatingActionButton settings;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameLogin);
        username.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validateUsername();
            return false;
        });
        password = findViewById(R.id.passwordLogin);
        password.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validatePassword();
            return false;
        });
        loginBtn = findViewById(R.id.LoginButtonLogin);
        loginBtn.setOnClickListener(v -> {
            confirmInput();
        });

        settings = findViewById(R.id.btnToSettingsLogin);
        settings.setOnClickListener(v ->{
            Intent i = new Intent(LoginActivity.this, SettingsActivity.class);
            startActivity(i);
        });

        register = findViewById(R.id.btnToRegister);

        register.setOnClickListener(v -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }

    private boolean validateUsername() {
        String usernameInput = username.getEditText().getText().toString().trim();
        if (usernameInput == null || usernameInput.length() == 0) {
            username.setError("Username can not be empty");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();
        if (passwordInput == null || passwordInput.length() == 0) {
            password.setError("Password can not be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    public void confirmInput() {
        boolean valid = validateUsername() || validatePassword();
        if (!valid) {
            return;
        }
        String input = "Welcome again " + username.getEditText().getText().toString().trim() + "!";
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        Intent contactsListIntent = new Intent(this, ContactsListActivity.class);

        // pass username to the contacts list screen
        Info.loggedUser = username.getEditText().getText().toString().trim();
        startActivity(contactsListIntent);
    }
}