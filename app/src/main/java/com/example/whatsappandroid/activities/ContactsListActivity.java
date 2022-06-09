package com.example.whatsappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.whatsappandroid.ContactListAdapter;
import com.example.whatsappandroid.ContactWithMessages;
import com.example.whatsappandroid.R;
import com.example.whatsappandroid.db.AppDB;
import com.example.whatsappandroid.db.ContactWithMessagesDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ContactsListActivity extends AppCompatActivity {
    private ListView listView;
    private ContactListAdapter adapter;
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
        String currentUsername = (String) props.get("myUsername");

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
        adapter = new ContactListAdapter(getApplicationContext(), contacts);

        listView.setAdapter(adapter);
        listView.setClickable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.getContactsWithMessages());
        adapter.notifyDataSetChanged();
    }
}