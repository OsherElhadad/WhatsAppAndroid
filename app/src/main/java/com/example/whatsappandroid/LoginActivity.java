package com.example.whatsappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    ImageView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.LoginButtonLogin);

        loginBtn.setOnClickListener(v -> {
            Intent contactsListIntent = new Intent(this, ContactsListActivity.class);

            // pass username to the contacts list screen
            contactsListIntent.putExtra("username", "hardcodedUser");
            startActivity(contactsListIntent);
        });

        register = findViewById(R.id.imageViewRegister);

        register.setOnClickListener(v -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }
}