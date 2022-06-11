package com.example.whatsappandroid.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.Message;

@Database(entities = {Contact.class, Message.class}, version = 5)
public abstract class AppDB extends RoomDatabase{
    public abstract ContactWithMessagesDao contactDao();
    public abstract MessageDao messageDao();
}
