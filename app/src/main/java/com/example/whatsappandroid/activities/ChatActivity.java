package com.example.whatsappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.whatsappandroid.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent currentIntent = getIntent();
        Bundle props = currentIntent.getExtras();

        TextView userMameChat = findViewById(R.id.user_name_chat);
        userMameChat.setText(props.get("contactNickname").toString());
    }
}