package com.example.whatsappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.adapters.ContactListAdapter;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactsListActivity extends AppCompatActivity {
    private RecyclerView listView;
    private ContactListAdapter adapter;
    private FloatingActionButton btnAddContact;
    private ContactsViewModel contactsViewModel;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        contactsViewModel = new ContactsViewModel();
        btnAddContact = findViewById(R.id.btnAddContact);
        listView = findViewById(R.id.lvContacts);

        btnAddContact.setOnClickListener(v -> {
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            startActivity(addContactIntent);
        });

        listView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ContactListAdapter(getApplicationContext(), contact -> {
            Intent chatIntent = new Intent(Info.context, ChatActivity.class);
            chatIntent.putExtra("contactNickname", contact.contact.getName());
            startActivity(chatIntent);
        });

        contactsViewModel.get().observe(this, contacts -> {
            adapter.setContactsList(contacts);
        });
        listView.setAdapter(adapter);
        listView.setClickable(true);
    }
}