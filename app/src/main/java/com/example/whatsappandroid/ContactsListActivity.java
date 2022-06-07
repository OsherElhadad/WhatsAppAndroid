package com.example.whatsappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactsListActivity extends AppCompatActivity {
    private ListView listView;
    private ContactListAdapter adapter;
    private FloatingActionButton btnAddContact;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        btnAddContact = findViewById(R.id.btnAddContact);

        btnAddContact.setOnClickListener(v -> {
            Intent addContactIntent = new Intent(this, AddContactActivity.class);
            startActivity(addContactIntent);
        });

        ArrayList<Contact> contacts = new ArrayList<>();

        Contact c1 = new Contact("YossiUsername", "YossiLastMsg", "YossiLastTime");
        contacts.add(c1);
        Contact c2 = new Contact("OsherUsername", "OsherLastMsg", "OsherLastTime");
        contacts.add(c2);

        listView = findViewById(R.id.lvContacts);
        adapter = new ContactListAdapter(getApplicationContext(), contacts);

        listView.setAdapter(adapter);
        listView.setClickable(true);
    }
}