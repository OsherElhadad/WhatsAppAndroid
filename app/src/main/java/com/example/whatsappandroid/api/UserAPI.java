package com.example.whatsappandroid.api;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsappandroid.db.UserDao;
import com.example.whatsappandroid.models.User;
import com.example.whatsappandroid.successables.Successable;
import com.example.whatsappandroid.utilities.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private UserDao userDao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    Successable successable;

    public UserAPI() {
        Gson gson = new GsonBuilder().setLenient().create();
        userDao = Info.usersDB.userDao();
        retrofit = new Retrofit.Builder()
                .baseUrl(Info.baseUrlServer +
                        Info.serverPort + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void setSuccessable(Successable successable) {
        this.successable = successable;
    }

    public void addUser(String username, String password, String nickname, byte[] picture) {
        User user = new User(username, password);
        Call<JsonPrimitive> call = webServiceAPI.postUser(user);
        call.enqueue(new Callback<JsonPrimitive>() {
            @Override
            public void onResponse(@NonNull Call<JsonPrimitive> call,
                                   @NonNull Response<JsonPrimitive> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {

                    // add new user to the room
                    String token = response.body().toString();
                    Info.loggerUserToken = token.substring(1, token.length() - 1);
                    User user = new User(username, password, nickname, picture);
                    userDao.insert(user);
                    successable.onSuccess();
                } else {
                    successable.onFail();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonPrimitive> call, @NonNull Throwable t) {
                successable.onFail();
            }
        });
    }

    public User getUser(String username) {
        RoomUsers roomUsers = new RoomUsers(userDao, username);
        User user;
        try {
            user = roomUsers.execute().get();
        }
        catch (Exception exception) {
            user = null;
        }
        return user;
    }

    // get user from room async
    private class RoomUsers extends AsyncTask<Void, Void, User> {
        private UserDao userDao;
        private String userId;

        public RoomUsers(UserDao userDao, String userId) {
            this.userDao = userDao;
            this.userId = userId;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return userDao.get(userId);
        }
    }
}
