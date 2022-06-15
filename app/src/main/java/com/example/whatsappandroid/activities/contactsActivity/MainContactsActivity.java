package com.example.whatsappandroid.activities.contactsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.viewModels.ContactsViewModel;

public class MainContactsActivity extends AppCompatActivity {
    private ContactsViewModel contactsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.addContactFragmentContainer, new ContactsListFragment())
                .commit();
    }
}