package com.example.myfoodapp.server;

import com.example.myfoodapp.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @POST("/register")
    Call<User> addUser(@Body User user);
    @POST("/login")
    Call<User> login(@Body User user);
}
