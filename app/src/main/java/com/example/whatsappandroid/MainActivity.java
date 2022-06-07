package com.example.whatsappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.LoginButton);

        loginBtn.setOnClickListener(v -> {
            Intent contactsListIntent = new Intent(this, ContactsListActivity.class);

            // pass username to the contacts list screen
            contactsListIntent.putExtra("username", "hardcodedUser");
            startActivity(contactsListIntent);
        });
    }
}