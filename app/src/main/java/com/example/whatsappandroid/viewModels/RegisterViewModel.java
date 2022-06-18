package com.example.whatsappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatsappandroid.repositories.RegisterRepository;

public class RegisterViewModel extends ViewModel {
    private RegisterRepository registerRepository;
    private LiveData<Boolean> isSucceeded;

    public RegisterViewModel() {
        this.registerRepository = new RegisterRepository();
        this.isSucceeded = registerRepository.get();
    }

    public LiveData<Boolean> get() {
        return isSucceeded;
    }

    public void registerUser(String username, String password, String nickname, byte[] picture) {
        registerRepository.addUser(username, password, nickname, picture);
    }
}
