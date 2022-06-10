package com.example.whatsappandroid.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.whatsappandroid.models.ContactWithMessages;
import com.example.whatsappandroid.models.Contact;

import java.util.List;

@Dao
public interface ContactWithMessagesDao {
    @Transaction
    @Query("SELECT * FROM Contact")
    public List<ContactWithMessages> getContactsWithMessages();

    @Query("SELECT * FROM contact WHERE contactId = :id")
    Contact get(int id);

    @Insert
    void insert(Contact... Contacts);

    @Update
    void update(Contact... Contacts);

    @Delete
    void delete(Contact... Contacts);
}
