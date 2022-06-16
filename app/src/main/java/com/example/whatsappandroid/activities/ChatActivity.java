package com.example.whatsappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.whatsappandroid.R;
import com.example.whatsappandroid.adapters.MessageListAdapter;
import com.example.whatsappandroid.models.Message;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.MessagesViewModel;

public class ChatActivity extends AppCompatActivity {

    MessagesViewModel messagesViewModel;
    MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messagesViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        setNicknameHeader();
        setMessageList();
        setMessageBar();
    }

    private void setMessageBar() {
        ImageButton sendBtn = findViewById(R.id.send_msg_btn_chat);
        sendBtn.setOnClickListener(v -> {
            TextView messageET = findViewById(R.id.et_send_msg_chat);
            String content = messageET.getText().toString();

            if (content.equals("")) {
                return;
            }

            messageET.setText("");

            String time = "Time";

            Message message = new Message(content, time, true, Info.contactId, Info.loggedUser);

            messagesViewModel.add(message);

        });

    }

    private void setMessageList() {
        RecyclerView messagesListRV = findViewById(R.id.message_list_chat);
        messagesListRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageListAdapter(Info.context);

        messagesViewModel.get().observe(this, messages -> {
            adapter.setMessageList(messages);
//            adapter.notifyDataSetChanged();
        });

        messagesListRV.setAdapter(adapter);
    }

    private void setNicknameHeader() {
        Intent currentIntent = getIntent();
        Bundle props = currentIntent.getExtras();

        TextView contactNicknameTV = findViewById(R.id.contact_nickname_chat);
        contactNicknameTV.setText(props.get("contactNickname").toString());
    }
}