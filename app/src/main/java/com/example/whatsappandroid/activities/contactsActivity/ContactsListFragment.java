package com.example.whatsappandroid.activities.contactsActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.activities.ChatActivity;
import com.example.whatsappandroid.adapters.ContactListAdapter;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.ContactsViewModel;
import com.example.whatsappandroid.viewModels.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactsListFragment extends Fragment {
    private RecyclerView listView;
    private ContactListAdapter adapter;
    private FloatingActionButton btnAddContact;
    private ContactsViewModel contactsViewModel;
    private UserViewModel userViewModel;
    private View _view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_contacts_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _view = view;
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        contactsViewModel = new ViewModelProvider(requireActivity()).get(ContactsViewModel.class);
        setNicknameHeader(view);
        setPictureHeader(view);
        setAddContactBtn(view);
        setAdapter();
        setContactList(view);

        IntentFilter intentFilter = new IntentFilter("notifyContact");
        LocalBroadcastManager.getInstance(Info.context).registerReceiver(handleNotifyNewContact, intentFilter);
    }

    private final BroadcastReceiver handleNotifyNewContact = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setContactList(_view);
        }
    };

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
            chatIntent.putExtra("contactName", contact.getId());
            startActivity(chatIntent);
        });
    }

    private void setNicknameHeader(View view) {
        TextView userNicknameTV = view.findViewById(R.id.user_nickname);
        if (userViewModel.getUser(Info.loggedUser) == null ||
                userViewModel.getUser(Info.loggedUser).getNickname() == null) {
            userNicknameTV.setText(Info.loggedUser);
        } else {
            userNicknameTV.setText(userViewModel.getUser(Info.loggedUser).getNickname());
        }
    }

    private void setPictureHeader(View view) {

        // check if this user is in our room db as a user and get his picture
        ImageView userPictureTV = view.findViewById(R.id.profile_image_user);
        if (userViewModel.getUser(Info.loggedUser) != null) {
            byte[] picture = userViewModel.getUser(Info.loggedUser).getPicture();
            if (picture != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                userPictureTV.setImageBitmap(bitmap);
            }
        }
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