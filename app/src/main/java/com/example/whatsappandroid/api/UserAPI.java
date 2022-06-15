package com.example.whatsappandroid.api;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.db.UserDao;
import com.example.whatsappandroid.models.User;
import com.example.whatsappandroid.utilities.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private UserDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Info.context.getString(R.string.basicServerUrl) +
                        Info.context.getString(R.string.myServerPort) + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void addUser(String username, String password,
                        MutableLiveData<Boolean> isSucceededData) {
        User user = new User(username, password);
        Call<JsonPrimitive> call = webServiceAPI.postUser(user);
        call.enqueue(new Callback<JsonPrimitive>() {
            @Override
            public void onResponse(@NonNull Call<JsonPrimitive> call,
                                   @NonNull Response<JsonPrimitive> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    String token = response.body().toString();
                    Info.loggerUserToken = token.substring(1, token.length() - 1);
                    isSucceededData.setValue(false);
                    isSucceededData.setValue(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonPrimitive> call, @NonNull Throwable t) {
                isSucceededData.setValue(false);
                Log.e("onFailure: ", t.getMessage());
            }
        });
    }
}
