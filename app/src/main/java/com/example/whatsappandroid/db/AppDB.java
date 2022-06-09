package com.example.whatsappandroid.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.whatsappandroid.db.ContactWithMessagesDao;
import com.example.whatsappandroid.db.MessageDao;
import com.example.whatsappandroid.entities.Contact;
import com.example.whatsappandroid.entities.Message;

@Database(entities = {Contact.class, Message.class}, version = 3)
public abstract class AppDB extends RoomDatabase{
    public abstract ContactWithMessagesDao contactDao();
    public abstract MessageDao messageDao();
}
