package com.example.whatsappandroid.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.db.MessageDao;
import com.example.whatsappandroid.models.ApiMessage;
import com.example.whatsappandroid.models.Message;
import com.example.whatsappandroid.models.Transfer;
import com.example.whatsappandroid.utilities.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
                .baseUrl(Info.baseUrlServer +
                        Info.serverPort + "/")
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

                    // add the all messages to the dao
                    for (Message message : response.body()) {
                        messageDao.insert(message);
                    }
                    messages.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
            }
        });
    }

    public void addMessage(MutableLiveData<List<Message>> messages, String token, String username,
                           String contactServerPort, String contactUsername, Message message) {
        Transfer transfer = new Transfer(username, contactUsername, message.getContent());
        Retrofit contactRetrofit = new Retrofit.Builder()
                .baseUrl(Info.context.getString(R.string.http_request) + "10.0.2.2" + ":" +
                        contactServerPort + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI contactServerApi = contactRetrofit.create(WebServiceAPI.class);
        Call<Void> call = contactServerApi.transfer(transfer);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {

                    // send after transfer, post for the message
                    addToMyServer(messages, message, token, contactUsername);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
            }
        });
    }

    private void addToMyServer(MutableLiveData<List<Message>> messages, Message message,
                               String userToken, String contactUsername) {
        ApiMessage postMessage = new ApiMessage(message.getContent());
        Call<Void> call = webServiceAPI.postMessage(contactUsername, postMessage, userToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {

                    // add the new message to the list of messages
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = sdf.format(c.getTime());
                    message.setCreated(strDate);
                    messageDao.insert(message);
                    List<Message> newMessageList = messages.getValue();
                    assert newMessageList != null;
                    newMessageList.add(message);
                    messages.postValue(newMessageList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
            }
        });
    }
}
