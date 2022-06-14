package com.example.whatsappandroid.api;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.db.ContactDao;
import com.example.whatsappandroid.db.ContactWithMessagesDao;
import com.example.whatsappandroid.loggable;
import com.example.whatsappandroid.models.Contact;
import com.example.whatsappandroid.models.ContactWithMessages;
import com.example.whatsappandroid.models.Login;
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

public class ContactApi {
    private ContactDao contactDao;
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;

    public ContactApi(ContactDao contactDao) {
        Gson gson = new GsonBuilder().setLenient().create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Info.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.webServiceAPI = this.retrofit.create(WebServiceAPI.class);
        this.contactDao = contactDao;
    }

    public void getAllContacts(MutableLiveData<List<Contact>> contacts, String token) {
        Call<List<Contact>> call = webServiceAPI.getAllContacts(token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {
                new Thread(() -> {
                    contactDao.clear();
                    assert response.body() != null;

                    for (Contact contact: response.body()) {
                        contactDao.insert(contact);
                    }
                    contacts.postValue(response.body());
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
                Log.e("E", t.getMessage());
            }
        });
    }

}
