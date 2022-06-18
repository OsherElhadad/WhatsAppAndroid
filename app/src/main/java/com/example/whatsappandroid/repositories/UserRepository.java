package com.example.whatsappandroid.repositories;

import com.example.whatsappandroid.api.UserAPI;
import com.example.whatsappandroid.models.User;

public class UserRepository {
    private UserAPI userAPI;

    public UserRepository() {
        userAPI = new UserAPI();
    }

    public User getUser(String username) {
        return userAPI.getUser(username);
    }
}
