package com.example.whatsappandroid.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.activities.contactsActivity.MainContactsActivity;
import com.example.whatsappandroid.api.ConnectToFirebaseApi;
import com.example.whatsappandroid.api.LoginApi;
import com.example.whatsappandroid.Successable;
import com.example.whatsappandroid.utilities.Info;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity implements Successable {
    private FloatingActionButton register;
    private Button loginBtn;
    private TextInputLayout usernameTIL;
    private TextInputLayout passwordTIL;
    private String username;
    private String password;
    private FloatingActionButton settings;
    private String fbToken;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findActivityViews();
        setActivityListeners();
    }

    private void setActivityListeners() {
        usernameTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            username = usernameTIL.getEditText().getText().toString().trim();
            validateUsername();
            return false;
        });
        passwordTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            password = passwordTIL.getEditText().getText().toString().trim();
            validatePassword();
            return false;
        });
        loginBtn.setOnClickListener(v -> {
            if (confirmInput() == 1) {
                login();
            }
        });
        settings.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, SettingsActivity.class);
            startActivity(i);
        });
        register.setOnClickListener(v -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }

    private void findActivityViews() {
        usernameTIL = findViewById(R.id.usernameLogin);
        passwordTIL = findViewById(R.id.passwordLogin);
        loginBtn = findViewById(R.id.LoginButtonLogin);
        settings = findViewById(R.id.btnToSettingsLogin);
        register = findViewById(R.id.btnToRegister);
    }

    private boolean validateUsername() {
        if (username == null || username.length() == 0) {
            usernameTIL.setError("Username can not be empty");
            return false;
        } else {
            usernameTIL.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        if (password == null || password.length() == 0) {
            passwordTIL.setError("Password can not be empty");
            return false;
        } else {
            passwordTIL.setError(null);
            return true;
        }
    }

    public int confirmInput() {
        boolean valid = validateUsername() || validatePassword();
        if (!valid) {
            return 0;
        }
        return 1;
    }

    public void login() {
        LoginApi loginApi = new LoginApi(this);
        loginApi.loginToServer(username, password);
    }

    public void onSuccessfulLogin() {
        Toast.makeText(this, "Welcome again " + Info.loggedUser + "!",
                Toast.LENGTH_SHORT).show();

        // if logged in successfully then we send to the server his token.
        ConnectToFirebaseApi connectToFirebaseApi = new ConnectToFirebaseApi();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this,
                instanceIdResult -> {
                    fbToken = instanceIdResult.getToken();
                });
        fbToken = FirebaseInstanceId.getInstance().getToken();
        connectToFirebaseApi.connectToFB(Info.loggedUser, fbToken);
        Intent contactsListIntent = new Intent(this, MainContactsActivity.class);
        startActivity(contactsListIntent);
    }

    @Override
    public void onFailedLogin() {
        Toast.makeText(Info.context, "Something went wrong :(", Toast.LENGTH_SHORT).show();
    }
}