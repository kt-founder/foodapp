package com.example.myfoodapp.server;

import com.example.myfoodapp.model.ChangePasswordRequest;
import com.example.myfoodapp.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @POST("/api/users/register")
    Call<User> addUser(@Body User user);
    @POST("/api/users/login")
    Call<User> login(@Body User user);
    @PUT("/api/users/{id}/password")
    Call<ResponseBody> updatePassword(
            @Path("id") int userId,
            @Body ChangePasswordRequest request);

}
