package com.example.whatsappandroid.repositories;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatsappandroid.successables.Successable;
import com.example.whatsappandroid.api.ContactApi;
import com.example.whatsappandroid.db.AppDB;
import com.example.whatsappandroid.db.ContactDao;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.utilities.Info;

import java.util.List;

public class ContactRepository {
    private ContactApi contactApi;
    private ContactDao contactDao;
    private ContactListData contactListData;

    public ContactRepository() {
        AppDB appDB = Room.databaseBuilder(Info.context, AppDB.class, Info.loggedUser)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        this.contactDao = appDB.contactDao();
        this.contactListData = new ContactListData();

        this.contactApi = new ContactApi(this.contactDao);
    }

    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
            setValue(contactDao.getAllContacts());
        }

        @Override
        protected void onActive() {
            super.onActive();

            // load contacts from server API
            setContactListDataWithServerAPIContacts();
        }
    }

    public void setContactListDataWithServerAPIContacts() {
        this.contactApi.getAllContacts(this.contactListData,
                "Bearer " + Info.loggerUserToken);
    }


    protected void setContactListDataWithDbContacts() {
        contactListData.setValue(contactDao.getAllContacts());
    }

    public void setSuccessable(Successable successable) {
        contactApi.setSuccessable(successable);
    }

    public MutableLiveData<List<Contact>> getContacts() {
        return contactListData;
    }

    public void add(Contact contact) {

        this.contactApi.addContact(contact, Info.loggedUser,
                "Bearer " + Info.loggerUserToken, this.contactListData);
    }

    public void delete(Contact contact) {
        contactDao.delete(contact);
        setContactListDataWithDbContacts();
    }

    public void reload() {
        new GetContactsTask(contactListData, contactDao).execute();
    }

    public class GetContactsTask extends AsyncTask<Void, Void, Void> {
        private MutableLiveData<List<Contact>> contactsListData;
        private ContactDao contactDao;

        public GetContactsTask(MutableLiveData<List<Contact>> contactsListData,
                               ContactDao contactDao) {
            this.contactDao = contactDao;
            this.contactsListData = contactsListData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}

