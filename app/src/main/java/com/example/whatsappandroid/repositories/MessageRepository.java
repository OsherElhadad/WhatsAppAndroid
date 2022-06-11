package com.example.whatsappandroid.repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatsappandroid.db.AppDB;
import com.example.whatsappandroid.db.MessageDao;
import com.example.whatsappandroid.models.Message;
import com.example.whatsappandroid.utilities.Info;

import java.util.List;

public class MessageRepository {
    private MessageDao messageDao;
    private ContactListData messagesListData;

    public MessageRepository() {
        AppDB appDB = Room.databaseBuilder(Info.context, AppDB.class, Info.loggedUser)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        this.messageDao = appDB.messageDao();
        this.messagesListData = new ContactListData();
    }

    class ContactListData extends MutableLiveData<List<Message>> {
        public ContactListData() {
            super();
            setValue(messageDao.getChatMessages(Info.loggedUser, Info.contactId));
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                messagesListData.postValue(messageDao
                        .getChatMessages(Info.loggedUser, Info.contactId));
            }).start();
        }
    }

    public LiveData<List<Message>> get() {
        return messagesListData;
    }

    public void add(Message message) {
        this.messageDao.insert(message);
    }

    public void delete(Message message) {

    }

    public void reload() {
        new GetMessagesTask(messagesListData, messageDao).execute();
    }

    public class GetMessagesTask extends AsyncTask<Void, Void, Void> {
        private MutableLiveData<List<Message>> messagesListData;
        private MessageDao messageDao;

        public GetMessagesTask(MutableLiveData<List<Message>> messagesListData,
                               MessageDao messageDao) {
            this.messageDao = messageDao;
            this.messagesListData = messagesListData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
