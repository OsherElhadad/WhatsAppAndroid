package com.example.whatsappandroid.activities.contactsActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.ContactsViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class AddContactFragment extends Fragment {
    private Button btnAddContact;
    private ContactsViewModel contactsViewModel;
    private TextInputLayout contactUsernameTIL;
    private String contactUsername;
    private TextInputLayout contactNicknameTIL;
    private String contactNickname;
    private TextInputLayout contactServerTIL;
    private String contactServer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        contactsViewModel = new ViewModelProvider(requireActivity()).get(ContactsViewModel.class);
        setListeners();
    }

    private void setListeners() {
        contactUsernameTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            contactUsername = this.contactUsernameTIL.getEditText().getText().toString().trim();
            validateContactUsername();
            return false;
        });

        contactNicknameTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            contactNickname = this.contactNicknameTIL.getEditText().getText().toString().trim();
            validateNickname();
            return false;
        });

        contactServerTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            contactServer = contactServerTIL.getEditText().getText().toString().trim();
            validateServer();
            return false;
        });

        btnAddContact.setOnClickListener(v -> {
            if(confirmInput() == 1) {
                Contact newContact = new Contact(contactUsername, contactNickname, contactServer,
                        null, null, Info.loggedUser);

                contactsViewModel.add(newContact);

                String input = "Contact " + contactNickname + " added successfully!";
                Toast.makeText(getActivity(), input, Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    private void findViews(View view) {
        contactUsernameTIL = view.findViewById(R.id.ContactUsername);
        contactNicknameTIL = view.findViewById(R.id.ContactNickname);
        contactServerTIL = view.findViewById(R.id.ContactServer);
        btnAddContact = view.findViewById(R.id.btnAddContact);
    }

    private boolean validateContactUsername() {
        if (contactUsername == null || contactUsername.length() == 0) {
            contactUsernameTIL.setError("Username can not be empty");
            return false;
        } else {
            contactUsernameTIL.setError(null);
            return true;
        }
    }

    private boolean validateNickname() {
        if (contactNickname == null || contactNickname.length() == 0) {
            contactNicknameTIL.setError("Nickname can not be empty");
            return false;
        } else {
            contactNicknameTIL.setError(null);
            return true;
        }
    }

    private boolean validateServer() {
        if (contactServer == null || contactServer.length() == 0) {
            contactServerTIL.setError("Server can not be empty");
            return false;
        } else {
            contactServerTIL.setError(null);
            return true;
        }
    }

    public int confirmInput() {
        boolean valid = validateContactUsername() || validateNickname() || validateServer();
        if (!valid) {
            return 0;
        }
        return 1;
    }
}
