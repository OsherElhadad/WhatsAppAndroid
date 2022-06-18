package com.example.whatsappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatsappandroid.Successable;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.repositories.ContactRepository;

import java.util.List;

public class ContactsViewModel extends ViewModel {
    private ContactRepository contactRepository;
    private LiveData<List<Contact>> contacts;
    public ContactsViewModel() {
        this.contactRepository = new ContactRepository();
        this.contacts = contactRepository.getContacts();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }

    public void setSuccessable(Successable successable) {
        contactRepository.setSuccessable(successable);
    }

    public void add(Contact contact) {
        contactRepository.add(contact);
    }

    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    public void reload() {
        contactRepository.reload();
    }
}
