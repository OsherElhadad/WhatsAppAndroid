package com.example.whatsappandroid.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.ContactsViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class AddContactActivity extends AppCompatActivity {

    private Button btnAddContact;
    private ContactsViewModel contactsViewModel;
    private TextInputLayout contactName;
    private TextInputLayout nickname;
    private TextInputLayout server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        contactName = findViewById(R.id.ContactUsername);
        contactName.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validateContactName();
            return false;
        });
        nickname = findViewById(R.id.ContactNickname);
        nickname.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validateNickname();
            return false;
        });
        server = findViewById(R.id.ContactServer);
        server.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validateServer();
            return false;
        });
        btnAddContact = findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(v -> {
            confirmInput();
        });
    }

    private boolean validateContactName() {
        String contactNameInput = contactName.getEditText().getText().toString().trim();
        if (contactNameInput == null || contactNameInput.length() == 0) {
            contactName.setError("Username can not be empty");
            return false;
        } else {
            contactName.setError(null);
            return true;
        }
    }

    private boolean validateNickname() {
        String nicknameInput = nickname.getEditText().getText().toString().trim();
        if (nicknameInput == null || nicknameInput.length() == 0) {
            nickname.setError("Nickname can not be empty");
            return false;
        } else {
            nickname.setError(null);
            return true;
        }
    }

    private boolean validateServer() {
        String serverInput = server.getEditText().getText().toString().trim();
        if (serverInput == null || serverInput.length() == 0) {
            server.setError("Server can not be empty");
            return false;
        } else {
            server.setError(null);
            return true;
        }
    }

    public void confirmInput() {
        boolean valid = validateContactName() || validateNickname() || validateServer();
        if (!valid) {
            return;
        }
        String contactNameStr = contactName.getEditText().getText().toString().trim();
        String contactServerStr = server.getEditText().getText().toString().trim();

        // todo - sent invite to web server and check if succeeded


        Contact newContact = new Contact(contactNameStr, contactServerStr, null,
                null, Info.loggedUser);

        contactsViewModel = new ContactsViewModel();
        contactsViewModel.add(newContact);

        String input = "Contact " + contactName.getEditText().getText().toString().trim()
                + " added successfully!";
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        finish();
    }
}
