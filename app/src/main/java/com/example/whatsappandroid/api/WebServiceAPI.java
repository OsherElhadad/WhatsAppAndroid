package com.example.whatsappandroid.api;

import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.Login;
import com.example.whatsappandroid.models.User;
import com.example.whatsappandroid.models.UserFBToken;
import com.google.gson.JsonPrimitive;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("api/Users")
    Call<List<User>> getUsers();

    @POST("api/Users")
    Call<Void> createUser(@Body User contact);

    @DELETE("api/Users/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    @POST("api/LogIn")
    Call<JsonPrimitive> logIn(@Body Login login);

    @POST("api/ConnectToFirebase")
    Call<Void> ConnectToFirebase(@Body UserFBToken userFBToken);

    @GET("api/Contacts")
    Call<List<Contact>> getAllContacts(@Header("authorization") String auth);
}
