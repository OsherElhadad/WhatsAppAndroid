package com.example.whatsappandroid.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatsappandroid.models.Message;
import com.example.whatsappandroid.repositories.MessageRepository;

import java.util.List;

public class MessagesViewModel extends ViewModel{
    private MessageRepository messageRepository;
    private LiveData<List<Message>> messages;

    public MessagesViewModel() {
        this.messageRepository = new MessageRepository();
        this.messages = messageRepository.get();
    }

    public LiveData<List<Message>> get() {
        return messages;
    }

    public void add(Message message) {
        messageRepository.add(message);
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }

    public void reload() {
        messageRepository.reload();
    }
}

