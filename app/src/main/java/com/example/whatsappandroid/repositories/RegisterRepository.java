package com.example.whatsappandroid.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsappandroid.api.UserAPI;

public class RegisterRepository {
    private UserAPI userAPI;
    private isSucceededData isSucceededData;

    public RegisterRepository() {
        this.isSucceededData = new isSucceededData();
        this.userAPI = new UserAPI();
    }

    class isSucceededData extends MutableLiveData<Boolean> {
        public isSucceededData() {
            super();
            setValue(false);
        }
    }

    public void addUser(String username, String password, String nickname, byte[] picture) {
        userAPI.addUser(username, password, nickname, picture, this.isSucceededData);
    }

    public LiveData<Boolean> get() {
        return isSucceededData;
    }
}
