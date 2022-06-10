package com.example.whatsappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.whatsappandroid.listeners.OnItemClickListener;
import com.example.whatsappandroid.ContactWithMessages;
import com.example.whatsappandroid.R;
import com.example.whatsappandroid.adapters.ContactListAdapter2;
import com.example.whatsappandroid.db.AppDB;
import com.example.whatsappandroid.db.ContactWithMessagesDao;
import com.example.whatsappandroid.entities.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ContactsListActivity extends AppCompatActivity {
    private RecyclerView listView;
    private ContactListAdapter2 adapter;
    private FloatingActionButton btnAddContact;
    private AppDB appDB;
    private ContactWithMessagesDao contactDao;
    private List<ContactWithMessages> contacts;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        Intent currentIntent = getIntent();
        Bundle props = currentIntent.getExtras();
        String currentUsername = props.get("myUsername").toString();

        appDB = Room.databaseBuilder(getApplicationContext(),
                AppDB.class, currentUsername).allowMainThreadQueries().build();
        contactDao = appDB.contactDao();

        btnAddContact = findViewById(R.id.btnAddContact);

        btnAddContact.setOnClickListener(v -> {
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            addContactIntent.putExtra("myUsername", currentUsername);
            startActivity(addContactIntent);
        });

        contacts = contactDao.getContactsWithMessages();

        listView = findViewById(R.id.lvContacts);
        listView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ContactListAdapter2(getApplicationContext(), contact -> {
            Intent chatIntent = new Intent(getApplicationContext(), ChatActivity.class);
            chatIntent.putExtra("contactNickname", contact.contact.getName());
            startActivity(chatIntent);
        });
        adapter.setContactsList(contacts);
        listView.setAdapter(adapter);
        listView.setClickable(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.getContactsWithMessages());
        adapter.setContactsList(contacts);
        adapter.notifyDataSetChanged();
    }
}