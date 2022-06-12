package com.example.whatsappandroid.models;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey()
    private String id;
    private String password;
    private String nickname;
    private Bitmap picture;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public User(String id, String password) {
        this.id = id;
        this.password = password;
        this.nickname = null;
        this.picture = null;
    }

    public User(String id, String password, String nickname, Bitmap picture) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
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

    public Bitmap getPicture() {
        return picture;
    }
}
