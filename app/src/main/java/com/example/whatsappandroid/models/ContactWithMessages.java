package com.example.whatsappandroid.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.Message;

import java.util.List;

public class ContactWithMessages {
    @Embedded
    public Contact contact;
    @Relation(
            parentColumn = "id",
            entityColumn = "msgId"
    )
    public List<Message> messages;
}
