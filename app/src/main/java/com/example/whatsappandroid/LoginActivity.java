package com.example.whatsappandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    private ImageView register;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.LoginButtonLogin);

        loginBtn.setOnClickListener(v -> {
            Intent contactsListIntent = new Intent(this, ContactsListActivity.class);

            EditText usernameET = findViewById(R.id.editTextTextUserNameLogin);

            // pass username to the contacts list screen
            contactsListIntent.putExtra("myUsername", usernameET.getText().toString());
            startActivity(contactsListIntent);
        });

        register = findViewById(R.id.imageViewRegister);

        register.setOnClickListener(v -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }
}