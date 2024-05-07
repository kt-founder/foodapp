package com.example.myfoodapp.entity;

import com.example.myfoodapp.model.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FoodApi {

    @GET("/food/get-all")
    Call<List<Food>> getAllFood();

    @POST("/food/save")
    Call<List<Food>> save(@Body Food food);
}
