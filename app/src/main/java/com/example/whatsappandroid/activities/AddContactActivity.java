package com.example.whatsappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.db.AppDB;
import com.example.whatsappandroid.db.ContactWithMessagesDao;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.utilities.Info;

public class AddContactActivity extends AppCompatActivity {
    private AppDB appDB;
    private ContactWithMessagesDao contactDao;
    private Button btnAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        appDB = Room.databaseBuilder(getApplicationContext(),
                AppDB.class, Info.loggedUser).allowMainThreadQueries().build();
        contactDao = appDB.contactDao();

        btnAddContact = findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(v -> {

            EditText contactUsernameET = findViewById(R.id.ContactUsername);
            String contactUsername = contactUsernameET.getText().toString();

            EditText contactNameET = findViewById(R.id.ContactNickname);
            String contactName = contactNameET.getText().toString();

            EditText contactServerET = findViewById(R.id.ContactServer);
            String contactServer = contactServerET.getText().toString();

            // todo - sent invite to web server and check if succeeded


            Contact newContact = new Contact(contactName, contactServer, null,
                    null, Info.loggedUser);

            contactDao.insert(newContact);

            finish();
        });
    }
}