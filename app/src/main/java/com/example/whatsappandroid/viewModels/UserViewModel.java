package com.example.whatsappandroid.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.whatsappandroid.models.User;
import com.example.whatsappandroid.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    public UserViewModel() {
        this.userRepository = new UserRepository();
    }

    public User getUser(String username) {
        return userRepository.getUser(username);
    }
}
