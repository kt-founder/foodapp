package com.example.myfoodapp.server;

import com.example.myfoodapp.model.TypeFood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TypeFoodApi {
    @GET("/typefood/get-all")
    Call<List<TypeFood>> getAllTypeFood() ;

    @POST("/typefood/save")
    Call<List<TypeFood>> save(@Body TypeFood typeFood);
}
