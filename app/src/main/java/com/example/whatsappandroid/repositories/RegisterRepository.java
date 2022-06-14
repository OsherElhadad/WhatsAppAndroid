package com.example.whatsappandroid.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatsappandroid.api.UserAPI;
import com.example.whatsappandroid.db.AppDB;
import com.example.whatsappandroid.db.MessageDao;
import com.example.whatsappandroid.models.Message;
import com.example.whatsappandroid.utilities.Info;

import java.util.List;

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

    public void addUser(String username, String password) {
        userAPI.addUser(username, password, this.isSucceededData);
    }

    public LiveData<Boolean> get() {
        return isSucceededData;
    }
}
