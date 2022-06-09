package com.example.whatsappandroid.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int contactId;
    private String name;
    private String server;
    private int pictureId;
    private String last;
    private String lastdate;
    private String userId;

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Contact() {
        this.name = null;
        this.server = null;
        this.last = null;
        this.lastdate = null;
        this.userId = null;
    }

    public Contact(String name, int pictureId, String last, String lastMassageSendingTime) {
        this.name = name;
        this.pictureId = pictureId;
        this.last = last;
        this.lastdate = lastMassageSendingTime;
    }

    public Contact(String name, String server, String last, String lastMassageSendingTime, String userId) {
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastMassageSendingTime;
        this.userId = userId;
    }

    public int getContactId() {
        return this.contactId;
    }

    public String getName() {
        return this.name;
    }

    public String getServer() {
        return this.server;
    }

    public int getPictureId() {
        return this.pictureId;
    }

    public String getLast() {
        return this.last;
    }

    public String getLastdate() {
        return this.lastdate;
    }

    public String getUserId() {
        return this.userId;
    }


}
