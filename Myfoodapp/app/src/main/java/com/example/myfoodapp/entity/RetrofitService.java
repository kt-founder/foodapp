package com.example.myfoodapp.entity;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
<<<<<<< HEAD
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.118:9000")
=======
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.239.1:9000")
>>>>>>> 03a29132a2ed1aad4f83fb3a93f89ca17189e54b
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
