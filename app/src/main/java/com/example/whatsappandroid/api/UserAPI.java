package com.example.whatsappandroid.api;

import androidx.lifecycle.MutableLiveData;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.db.UserDao;
import com.example.whatsappandroid.models.User;
import com.example.whatsappandroid.utilities.Info;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private MutableLiveData<List<User>> userListData;
    private UserDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI(MutableLiveData<List<User>> userListData, UserDao dao) {
        this.userListData = userListData;
        this.dao = dao;

        retrofit = new Retrofit.Builder()
                .baseUrl(Info.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get() {
        Call<List<User>> call = webServiceAPI.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                new Thread(() -> {
                    dao.insert((User) response.body());
                    userListData.postValue(dao.index());
                }).start();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
