package com.example.whatsappandroid.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.whatsappandroid.models.User;

@Database(entities = {User.class}, version = 8, exportSchema = false)
public abstract class UsersDB extends RoomDatabase {
    public abstract UserDao userDao();
}
