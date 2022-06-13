package com.example.whatsappandroid.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.loggable;
import com.example.whatsappandroid.models.Login;
import com.example.whatsappandroid.utilities.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginApi {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    loggable loggable;

    public LoginApi(loggable s) {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Info.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        loggable = s;
    }

    public void loginToServer(String id, String password) {
        Info.loggedUser = id;
        Call<JsonPrimitive> call = webServiceAPI.logIn(new Login(id, password));
        call.enqueue(new Callback<JsonPrimitive>() {
            @Override
            public void onResponse(@NonNull Call<JsonPrimitive> call,
                                   @NonNull Response<JsonPrimitive> response) {
                if (response.code() == 200 && response.body() != null) {
                    Info.loggerUserToken = response.body().toString();
                    loggable.onSuccessfulLogin();
                } else {
                    Info.loggedUser = null;
                    Info.loggerUserToken = null;
                    loggable.onFailedLogin();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonPrimitive> call, @NonNull Throwable t) {
                Log.e("E",t.getMessage());
                Info.loggedUser = null;
                Info.loggerUserToken = null;
            }
        });
    }
}
