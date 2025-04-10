package com.example.myfoodapp.server;

import com.example.myfoodapp.model.TypeFood;
import com.example.myfoodapp.model.TypeFoodResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TypeFoodApi {
    @GET("/typefood/get-all")
    Call<List<TypeFood>> getAllTypeFood() ;
    @GET("/api/typefood/")
    Call<List<TypeFoodResponseDto>> getAllTypeFood1() ;
    @GET("/api/typefood/{id}")
    Call<TypeFoodResponseDto> getTypeFoodById(@Path("id") int id);
    @POST("/typefood/save")
    Call<List<TypeFood>> save(@Body TypeFood typeFood);
}
