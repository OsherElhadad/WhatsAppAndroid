package com.example.whatsappandroid.repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatsappandroid.db.AppDB;
import com.example.whatsappandroid.db.ContactDao;
import com.example.whatsappandroid.db.ContactWithMessagesDao;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.ContactWithMessages;
import com.example.whatsappandroid.utilities.Info;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {

    private ContactWithMessagesDao contactDao;
    private ContactListData contactListData;

    public ContactRepository() {
        AppDB appDB = Room.databaseBuilder(Info.context, AppDB.class, Info.loggedUser)
                     .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        this.contactDao = appDB.contactDao();
        this.contactListData = new ContactListData();
    }

    class ContactListData extends MutableLiveData<List<ContactWithMessages>> {
        public ContactListData() {
            super();
            setValue(contactDao.getContactsWithMessages());
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(()-> {
                contactListData.postValue(contactDao.getContactsWithMessages());
            }).start();
        }
    }

    public LiveData<List<ContactWithMessages>> get() {
        return contactListData;
    }

    public void add(Contact contact) {
        this.contactDao.insert(contact);
    }

    public void delete(ContactWithMessages contact) {

    }

    public void reload() {
        new GetContactsTask(contactListData, contactDao).execute();
    }

    public class GetContactsTask extends AsyncTask<Void, Void, Void> {
        private MutableLiveData<List<ContactWithMessages>> contactsListData;
        private ContactWithMessagesDao contactDao;

        public GetContactsTask(MutableLiveData<List<ContactWithMessages>> contactsListData,
                               ContactWithMessagesDao contactDao) {
            this.contactDao = contactDao;
            this.contactsListData = contactsListData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}

