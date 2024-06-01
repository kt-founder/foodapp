package com.example.myfoodapp.server;

import com.example.myfoodapp.model.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FoodApi {

    @GET("/food/get-all")
    Call<List<Food>> getAllFood();

    @POST("/food/addFood")
    Call<Food> addFoods(@Body Food food);

    @PUT("/food/update/{id}")
    Call<String> UpdateFood(@Path(value = "id" ) int id, @Body Food food);

    @POST("food/delete/{id}")
    Call<Void> deleteFood(@Path("id") int id);
}
