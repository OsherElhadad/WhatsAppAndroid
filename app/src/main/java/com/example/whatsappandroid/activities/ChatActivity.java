package com.example.whatsappandroid.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.adapters.MessageListAdapter;
import com.example.whatsappandroid.models.Message;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.MessagesViewModel;
import com.example.whatsappandroid.viewModels.UserViewModel;

public class ChatActivity extends AppCompatActivity {

    MessagesViewModel messagesViewModel;
    UserViewModel userViewModel;
    MessageListAdapter adapter;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        messagesViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        bitmap = null;
        setNicknameHeader();
        setPictureHeader();
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

            // set the textbox to be null
            messageET.setText("");
            Message message = new Message(content, null, true, Info.contactId, Info.loggedUser);
            messagesViewModel.add(message);

        });

    }

    private void setMessageList() {
        RecyclerView messagesListRV = findViewById(R.id.message_list_chat);
        messagesListRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageListAdapter(Info.context, bitmap);

        messagesViewModel.get().observe(this, messages -> {
            adapter.setMessageList(messages);
        });

        messagesListRV.setAdapter(adapter);
    }

    private void setNicknameHeader() {
        Intent currentIntent = getIntent();
        Bundle props = currentIntent.getExtras();

        TextView contactNicknameTV = findViewById(R.id.contact_nickname_chat);
        contactNicknameTV.setText(props.get("contactNickname").toString());
    }

    private void setPictureHeader() {
        Intent currentIntent = getIntent();
        Bundle props = currentIntent.getExtras();

        // check if this contact is in our room db as a user and get his picture
        ImageView contactPictureTV = findViewById(R.id.profile_image_chat);
        if (userViewModel.getUser(props.get("contactName").toString()) != null) {
            byte[] picture = userViewModel.getUser(props.get("contactName").toString()).getPicture();
            if (picture != null) {
                bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                contactPictureTV.setImageBitmap(bitmap);
            }
        }
    }
}