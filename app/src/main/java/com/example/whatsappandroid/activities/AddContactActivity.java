package com.example.whatsappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.example.whatsappandroid.R;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.ContactsViewModel;

public class AddContactActivity extends AppCompatActivity {

    private Button btnAddContact;
    private ContactsViewModel contactsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

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

            contactsViewModel = new ContactsViewModel();
            contactsViewModel.add(newContact);

            finish();
        });
    }
}
