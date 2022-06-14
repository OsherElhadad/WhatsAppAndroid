package com.example.whatsappandroid.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.models.UserFBToken;
import com.example.whatsappandroid.utilities.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectToFirebaseApi {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public ConnectToFirebaseApi() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Info.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void connectToFB(String username, String token) {
        Call<Void> call = webServiceAPI.ConnectToFirebase(new UserFBToken(username, token));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,
                                   @NonNull Response<Void> response) {
                if (response.code() == 200) {
                    Log.e("Success", "created new token at the server");
                } else {
                    Log.e("Error", "error at the server while creating new token at the server");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("Failure", "failure at the server while creating new token at the server");
            }
        });
    }
}
