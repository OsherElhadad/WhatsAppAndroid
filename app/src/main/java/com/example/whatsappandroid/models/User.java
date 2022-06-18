package com.example.whatsappandroid.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey()
    @NonNull
    private String id;
    private String password;
    private String nickname;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] picture;

    @Ignore
    public User() {
        this.id = null;
        this.password = null;
        this.nickname = null;
        this.picture = null;
    }

    @Ignore
    public User(String id, String password) {
        this.id = id;
        this.password = password;
        this.nickname = null;
        this.picture = null;
    }

    public User(String id, String password, String nickname, byte[] picture) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.picture = picture;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public byte[] getPicture() {
        return picture;
    }
}
