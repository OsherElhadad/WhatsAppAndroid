package com.example.whatsappandroid.activities.contactsActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.activities.ChatActivity;
import com.example.whatsappandroid.adapters.ContactListAdapter;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactsListFragment extends Fragment {
    private RecyclerView listView;
    private ContactListAdapter adapter;
    private FloatingActionButton btnAddContact;
    private ContactsViewModel contactsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_contacts_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactsViewModel = new ViewModelProvider(requireActivity()).get(ContactsViewModel.class);
        setAddContactBtn(view);
        setAdapter();
        setContactList(view);
    }

    private void setAddContactBtn(View view) {
        btnAddContact = view.findViewById(R.id.btnAddContact);
        btnAddContact.setOnClickListener(v -> {

            Fragment fragment = new AddContactFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.addContactFragmentContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    private void setAdapter() {
        adapter = new ContactListAdapter(Info.context, contact -> {
            Intent chatIntent = new Intent(Info.context, ChatActivity.class);
            Info.contactId = contact.getId();
            chatIntent.putExtra("contactNickname", contact.getName());
            startActivity(chatIntent);
        });
    }

    private void setContactList(View view) {
        listView = view.findViewById(R.id.lvContacts);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactsViewModel.getContacts().observe(getViewLifecycleOwner(), contacts -> {
            adapter.setContactsList(contacts);
        });
        listView.setAdapter(adapter);
        listView.setClickable(true);
    }
}