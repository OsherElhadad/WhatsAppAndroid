package com.example.whatsappandroid;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.whatsappandroid.entities.Contact;
import com.example.whatsappandroid.entities.Message;

import java.util.List;

public class ContactWithMessages {
    @Embedded
    public Contact contact;
    @Relation(
            parentColumn = "contactId",
            entityColumn = "msgId"
    )
    public List<Message> messages;

    public int getContactId() {
        return contact.getContactId();
    }

    public String getName() {
        return contact.getName();
    }

    public String getServer() {
        return contact.getServer();
    }

    public int getPictureId() {
        return contact.getPictureId();
    }

    public String getLast() {
        return contact.getLast();
    }

    public String getLastdate() {
        return contact.getLastdate();
    }

    public String getUserId() {
        return contact.getUserId();
    }
}
