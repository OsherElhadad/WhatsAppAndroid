package com.example.whatsappandroid;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class, Message.class}, version = 3)
public abstract class AppDB extends RoomDatabase{
    public abstract ContactWithMessagesDao contactDao();
    public abstract MessageDao messageDao();
}
