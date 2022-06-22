package com.example.whatsappandroid.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsappandroid.api.UserAPI;
import com.example.whatsappandroid.successables.Successable;

public class RegisterRepository {
    private UserAPI userAPI;

    public RegisterRepository() {
        this.userAPI = new UserAPI();
    }


    public void setSuccessable(Successable successable) {
        this.userAPI.setSuccessable(successable);
    }

    public void addUser(String username, String password, String nickname, byte[] picture) {
        userAPI.addUser(username, password, nickname, picture);
    }

}
