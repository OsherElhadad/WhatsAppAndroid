package com.example.whatsappandroid.api;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.db.MessageDao;
import com.example.whatsappandroid.models.ApiMessage;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.Invitation;
import com.example.whatsappandroid.models.Message;
import com.example.whatsappandroid.models.Transfer;
import com.example.whatsappandroid.utilities.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageApi {
    private MessageDao messageDao;
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    public MessageApi(MessageDao messageDao) {
        Gson gson = new GsonBuilder().setLenient().create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Info.context.getString(R.string.basicServerUrl) +
                        Info.context.getString(R.string.myServerPort) + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.webServiceAPI = this.retrofit.create(WebServiceAPI.class);
        this.messageDao = messageDao;
    }

    public void getAllMessages(MutableLiveData<List<Message>> messages, String token,
                               String contactUsername) {
        Call<List<Message>> call = webServiceAPI.getMessages(contactUsername, token);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call,
                                   @NonNull Response<List<Message>> response) {
                new Thread(() -> {
                    messageDao.clear();
                    if (response.body() == null) {
                        return;
                    }

                    for (Message message : response.body()) {
                        messageDao.insert(message);
                    }
                    messages.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
                Log.e("E", t.getMessage());
            }
        });
    }

    public void addMessage(MutableLiveData<List<Message>> messages, String token, String username,
                           String contactServerPort, String contactUsername, Message message) {
        Transfer transfer = new Transfer(username, contactUsername, message.getContent());
        Retrofit contactRetrofit = new Retrofit.Builder()
                .baseUrl(Info.context.getString(R.string.basicServerUrl) +
                        contactServerPort + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI contactServerApi = contactRetrofit.create(WebServiceAPI.class);
        Call<Void> call = contactServerApi.transfer(transfer);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    addToMyServer(messages, message, token, contactUsername, username);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }
        });
    }

    private void addToMyServer(MutableLiveData<List<Message>> messages, Message message,
                               String userToken, String contactUsername, String username) {
        ApiMessage postMessage = new ApiMessage(message.getContent());
        Call<Void> call = webServiceAPI.postMessage(contactUsername, postMessage, userToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

                if (response.isSuccessful()) {
                    messageDao.insert(message);
                    List<Message> m = messages.getValue();
                    m.add(message);
                    messages.setValue(m);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }
        });
    }
}
