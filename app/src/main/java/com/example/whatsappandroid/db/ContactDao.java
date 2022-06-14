package com.example.whatsappandroid.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.ContactWithMessages;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    List<Contact> getAllContacts();

    @Transaction
    @Query("SELECT * FROM contact")
    List<ContactWithMessages> getAllContactsWithMessages();

    @Query("SELECT * FROM contact WHERE contactId = :id")
    Contact get(int id);

    @Insert
    void insert(Contact... Contacts);

    @Update
    void update(Contact... Contacts);

    @Delete
    void delete(Contact... Contacts);

    @Query("DELETE FROM contact")
    void clear();
}
