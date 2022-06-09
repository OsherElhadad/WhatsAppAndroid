package com.example.whatsappandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.LoginButton);

        loginBtn.setOnClickListener(v -> {
            Intent contactsListIntent = new Intent(this, ContactsListActivity.class);

            EditText usernameET = findViewById(R.id.editTextTextPersonName);

            // pass username to the contacts list screen
            contactsListIntent.putExtra("myUsername", usernameET.getText().toString());
            startActivity(contactsListIntent);
        });
    }
}