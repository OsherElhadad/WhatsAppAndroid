package com.example.whatsappandroid.api;

import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.ContactWithMessages;
import com.example.whatsappandroid.models.Invitation;
import com.example.whatsappandroid.models.Login;
import com.example.whatsappandroid.models.User;
import com.google.gson.JsonObject;
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
    @POST("api/Users")
    Call<Void> postUser(@Body User user);

    @POST("api/LogIn")
    Call<JsonPrimitive> logIn(@Body Login login);

    @GET("api/Contacts")
    Call<List<Contact>> getAllContacts(@Header("authorization") String auth);

    @POST("api/Contacts")
    Call<Void> postContact(@Body Contact contact , @Header("authorization") String auth);

    @POST("api/Invitations")
    Call<Void> postInvitation(@Body Invitation invitation);
}
