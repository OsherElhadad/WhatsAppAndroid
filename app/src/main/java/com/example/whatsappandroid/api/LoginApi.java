package com.example.whatsappandroid.api;

import androidx.annotation.NonNull;

import com.example.whatsappandroid.Successable;
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
    Successable successable;

    public LoginApi(Successable s) {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Info.baseUrlServer +
                        Info.serverPort + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        successable = s;
    }

    public void loginToServer(String id, String password) {
        Info.loggedUser = id;
        Call<JsonPrimitive> call = webServiceAPI.logIn(new Login(id, password));
        call.enqueue(new Callback<JsonPrimitive>() {
            @Override
            public void onResponse(@NonNull Call<JsonPrimitive> call,
                                   @NonNull Response<JsonPrimitive> response) {
                if (response.code() == 200 && response.body() != null) {
                    String token = response.body().toString();
                    Info.loggerUserToken = token.substring(1, token.length() - 1);
                    successable.onSuccessfulLogin();
                } else {
                    Info.loggedUser = null;
                    Info.loggerUserToken = null;
                    successable.onFailedLogin();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonPrimitive> call, @NonNull Throwable t) {
                Info.loggedUser = null;
                Info.loggerUserToken = null;
            }
        });
    }
}
