package com.example.whatsappandroid.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.ContactsViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class AddContactActivity extends AppCompatActivity {
    private Button btnAddContact;
    private ContactsViewModel contactsViewModel;
    private TextInputLayout contactUsernameTIL;
    private TextInputLayout contactNicknameTIL;
    private TextInputLayout contactServerTIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        contactUsernameTIL = findViewById(R.id.ContactUsername);
        contactUsernameTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validateContactUsername();
            return false;
        });
        contactNicknameTIL = findViewById(R.id.ContactNickname);
        contactNicknameTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validateNickname();
            return false;
        });
        contactServerTIL = findViewById(R.id.ContactServer);
        contactServerTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validateServer();
            return false;
        });
        btnAddContact = findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(v -> {
            confirmInput();
        });
    }

    private boolean validateContactUsername() {
        String contactNameInput = contactUsernameTIL.getEditText().getText().toString().trim();
        if (contactNameInput == null || contactNameInput.length() == 0) {
            contactUsernameTIL.setError("Username can not be empty");
            return false;
        } else {
            contactUsernameTIL.setError(null);
            return true;
        }
    }

    private boolean validateNickname() {
        String nicknameInput = contactNicknameTIL.getEditText().getText().toString().trim();
        if (nicknameInput == null || nicknameInput.length() == 0) {
            contactNicknameTIL.setError("Nickname can not be empty");
            return false;
        } else {
            contactNicknameTIL.setError(null);
            return true;
        }
    }

    private boolean validateServer() {
        String serverInput = contactServerTIL.getEditText().getText().toString().trim();
        if (serverInput == null || serverInput.length() == 0) {
            contactServerTIL.setError("Server can not be empty");
            return false;
        } else {
            contactServerTIL.setError(null);
            return true;
        }
    }

    public void confirmInput() {
        boolean valid = validateContactUsername() || validateNickname() || validateServer();
        if (!valid) {
            return;
        }
        String contactUsername = this.contactUsernameTIL.getEditText().getText().toString().trim();
        String contactNickname = this.contactNicknameTIL.getEditText().getText().toString().trim();
        String contactServerStr = contactServerTIL.getEditText().getText().toString().trim();

        Contact newContact = new Contact(contactUsername, contactNickname, contactServerStr, null,
                null, Info.loggedUser);

        contactsViewModel.add(newContact);

        String input = "Contact " + this.contactUsernameTIL.getEditText().getText().toString().trim()
                + " added successfully!";
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        finish();
    }
}
