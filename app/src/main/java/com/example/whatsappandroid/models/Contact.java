package com.example.whatsappandroid.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;
    private String name;
    private String server;
    private int pictureId;
    private String last;
    private String lastdate;
    private String userId;

    public void setId(String contactId) {
        this.id = contactId;
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
        this.id = null;
        this.name = null;
        this.server = null;
        this.last = null;
        this.lastdate = null;
        this.userId = null;
    }

    public Contact(String id, String name, int pictureId,
                   String last, String lastMassageSendingTime) {
        this.id = id;
        this.name = name;
        this.pictureId = pictureId;
        this.last = last;
        this.lastdate = lastMassageSendingTime;
    }

    public Contact(String id, String name, String server,
                   String last, String lastMassageSendingTime, String userId) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastMassageSendingTime;
        this.userId = userId;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getServer() {
        return this.server;
    }

    public String getServerPort() {
        return this.server.substring(this.server.lastIndexOf(":") + 1);
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
