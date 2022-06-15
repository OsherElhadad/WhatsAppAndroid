package com.example.whatsappandroid.api;

import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.Invitation;
import com.example.whatsappandroid.models.Login;
import com.example.whatsappandroid.models.User;
import com.example.whatsappandroid.models.UserFBToken;
import com.google.gson.JsonPrimitive;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WebServiceAPI {

    @POST("api/Users")
    Call<JsonPrimitive> postUser(@Body User user);

    @POST("api/LogIn")
    Call<JsonPrimitive> logIn(@Body Login login);

    @POST("api/ConnectToFirebase")
    Call<Void> ConnectToFirebase(@Body UserFBToken userFBToken);

    @GET("api/Contacts")
    Call<List<Contact>> getAllContacts(@Header("authorization") String auth);

    @POST("api/Contacts")
    Call<Void> postContact(@Body Contact contact , @Header("authorization") String auth);

    @POST("api/Invitations")
    Call<Void> postInvitation(@Body Invitation invitation);
}
