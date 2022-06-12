package com.example.whatsappandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.utilities.Info;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    private FloatingActionButton register;
    private Button loginBtn;
    private TextInputLayout username;
    private TextInputLayout password;

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

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this,
                instanceIdResult -> {
                    String token = instanceIdResult.getToken();
                });

        String input = "Welcome again " + username.getEditText().getText().toString().trim() + "!";
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        Intent contactsListIntent = new Intent(this, ContactsListActivity.class);

        // pass username to the contacts list screen
        Info.loggedUser = username.getEditText().getText().toString().trim();
        startActivity(contactsListIntent);
    }
}