package com.example.whatsappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.adapters.ContactListAdapter;
import com.example.whatsappandroid.adapters.MessageListAdapter;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.MessagesViewModel;

public class ChatActivity extends AppCompatActivity {

    MessagesViewModel messagesViewModel;
    MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent currentIntent = getIntent();
        Bundle props = currentIntent.getExtras();

        TextView contactNicknameTV = findViewById(R.id.contact_nickname_chat);
        contactNicknameTV.setText(props.get("contactNickname").toString());

//        RecyclerView messagesListRV = findViewById(R.id.message_list_chat);
//
//        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        adapter = new MessageListAdapter(Info.context);
//
//        messagesViewModel.get().observe(this, messages -> {
//            adapter.setChatMessages(messages;
//        });
//        messagesListRV.setAdapter(adapter);
//        messagesListRV.setClickable(true);
    }
}