package com.example.whatsappandroid.db;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.User;

import java.util.List;

public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> index();

    @Query("SELECT * FROM user WHERE id = :id")
    Contact get(int id);

    @Insert
    void insert(User... Users);

    @Update
    void update(User... Users);

    @Delete
    void delete(User... Users);
}
