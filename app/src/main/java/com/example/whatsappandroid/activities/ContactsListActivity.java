package com.example.whatsappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        setAddContactBtn();
        setAdapter();
        setContactList();
    }

    private void setAddContactBtn() {
        btnAddContact = findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(v -> {
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            startActivity(addContactIntent);
        });
    }

    private void setAdapter() {
        adapter = new ContactListAdapter(getApplicationContext(), contact -> {
            Intent chatIntent = new Intent(Info.context, ChatActivity.class);
            Info.contactId = contact.getContactId();
            chatIntent.putExtra("contactNickname", contact.getName());
            startActivity(chatIntent);
        });
    }

    private void setContactList() {
        listView = findViewById(R.id.lvContacts);
        listView.setLayoutManager(new LinearLayoutManager(this));
        contactsViewModel.get().observe(this, contacts -> {
            adapter.setContactsList(contacts);
        });
        listView.setAdapter(adapter);
        listView.setClickable(true);
    }


}