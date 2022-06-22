package com.example.whatsappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatsappandroid.repositories.RegisterRepository;
import com.example.whatsappandroid.successables.Successable;

public class RegisterViewModel extends ViewModel {
    private RegisterRepository registerRepository;

    public RegisterViewModel() {
        this.registerRepository = new RegisterRepository();
    }

    public void setSuccessable(Successable successable) {
        this.registerRepository.setSuccessable(successable);
    }

    public void registerUser(String username, String password, String nickname, byte[] picture) {
        registerRepository.addUser(username, password, nickname, picture);
    }
}
