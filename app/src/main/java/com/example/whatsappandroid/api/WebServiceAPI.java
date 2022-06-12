package com.example.whatsappandroid.api;

import com.example.whatsappandroid.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("api/Users")
    Call<List<User>> getUsers();

    @POST("api/Users")
    Call<Void> createUser(@Body User contact);

    @DELETE("api/Users/{id}")
    Call<Void> deleteUser(@Path("id") int id);
}
