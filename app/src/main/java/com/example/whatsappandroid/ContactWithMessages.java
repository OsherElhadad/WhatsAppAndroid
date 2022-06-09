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
}
