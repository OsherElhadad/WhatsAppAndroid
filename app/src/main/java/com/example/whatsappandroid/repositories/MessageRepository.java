package com.example.whatsappandroid.repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatsappandroid.api.MessageApi;
import com.example.whatsappandroid.db.AppDB;
import com.example.whatsappandroid.db.ContactDao;
import com.example.whatsappandroid.db.MessageDao;
import com.example.whatsappandroid.models.Message;
import com.example.whatsappandroid.utilities.Info;

import java.util.List;

public class MessageRepository {
    private MessageApi messageApi;
    private ContactDao contactDao;
    private MessageDao messageDao;
    private MessageListData messagesListData;

    public MessageRepository() {
        AppDB appDB = Room.databaseBuilder(Info.context, AppDB.class, Info.loggedUser)
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        this.messageDao = appDB.messageDao();
        this.contactDao = appDB.contactDao();
        this.messageApi = new MessageApi(this.messageDao);
        this.messagesListData = new MessageListData();
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
            setValue(messageDao.getChatMessages(Info.loggedUser, Info.contactId));
        }

        @Override
        protected void onActive() {
            super.onActive();
            setMessagesListDataWithApiServerMessages();
        }
    }

    public void setMessagesListDataWithApiServerMessages() {
        this.messageApi.getAllMessages(this.messagesListData,
                "Bearer " + Info.loggerUserToken, Info.contactId);
    }

    protected void setMessagesListDataWithDbMessages() {
        new Thread(() -> {
            messagesListData.postValue(messageDao
                    .getChatMessages(Info.loggedUser, Info.contactId));
        }).start();
    }

    public LiveData<List<Message>> get() {
        return messagesListData;
    }

    public void add(Message message) {
        messageApi.addMessage(this.messagesListData, "Bearer " + Info.loggerUserToken,
                Info.loggedUser,  this.contactDao.get(Info.contactId).getServerPort(),
                Info.contactId, message);
    }

    public void delete(Message message) {
        this.messageDao.delete(message);
        setMessagesListDataWithDbMessages();
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
