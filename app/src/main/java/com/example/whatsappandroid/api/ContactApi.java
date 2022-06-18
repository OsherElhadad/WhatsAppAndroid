package com.example.whatsappandroid.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.Successable;
import com.example.whatsappandroid.db.ContactDao;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.Invitation;
import com.example.whatsappandroid.utilities.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactApi {
    private ContactDao contactDao;
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private Successable successable;
    public ContactApi(ContactDao contactDao) {
        Gson gson = new GsonBuilder().setLenient().create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Info.baseUrlServer +
                        Info.serverPort + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.webServiceAPI = this.retrofit.create(WebServiceAPI.class);
        this.contactDao = contactDao;
    }

    public void setSuccessable(Successable successable) {
        this.successable = successable;
    }

    public void getAllContacts(MutableLiveData<List<Contact>> contacts, String token) {
        Call<List<Contact>> call = webServiceAPI.getAllContacts(token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call,
                                   @NonNull Response<List<Contact>> response) {
                new Thread(() -> {
                    contactDao.clear();
                    if (response.body() == null) {
                        return;
                    }

                    // add the all contacts to the dao
                    for (Contact contact : response.body()) {
                        contactDao.insert(contact);
                    }
                    contacts.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
            }
        });
    }

    public void addContact(Contact contact, String username, String token,
                           MutableLiveData<List<Contact>> contacts) {
        Invitation invitation = new Invitation(username, contact.getId(), contact.getServer());
        Retrofit contactRetrofit = new Retrofit.Builder()
                .baseUrl(Info.context.getString(R.string.http_request) +
                        contact.getServer() + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceAPI contactServerApi = contactRetrofit.create(WebServiceAPI.class);
        Call<Void> call = contactServerApi.postInvitation(invitation);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {

                        // send after invitation, post for the contact
                        addToMyServer(contact, token, contacts);
                    } else if(successable != null){
                        successable.onFailedLogin();
                    }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if(successable != null) {
                    successable.onFailedLogin();
                }
            }
        });
    }

    private void addToMyServer(Contact contact, String userToken,
                               MutableLiveData<List<Contact>> contacts) {
        Call<Void> call = webServiceAPI.postContact(contact, userToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

                    if (response.isSuccessful()) {

                        // add the new contact to the list of contacts
                        successable.onSuccessfulLogin();
                        contactDao.insert(contact);
                        List<Contact> newContacts = contacts.getValue();
                        newContacts.add(contact);
                        contacts.postValue(newContacts);
                    } else if(successable != null) {
                        successable.onFailedLogin();
                    }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                if(successable != null) {
                    successable.onFailedLogin();
                }
            }
        });
    }
}
