package com.example.myfoodapp.server;

import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.FoodDto;
import com.example.myfoodapp.model.FoodItemDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FoodApi {

    @GET("/food/get-all")
    Call<List<Food>> getAllFood();
    @GET("/api/food")
    Call<List<FoodDto>> getAllFood1();
    @POST("/api/food")
    Call<Food> addFoods(@Body Food food);

    @PATCH("/api/food/{id}")
    Call<ResponseBody> updateFood(@Path("id") int id, @Body Food updatedFood);
    @GET("/api/food/auth/{id}")
    Call<List<FoodDto>> getAllFoodByAuthId(@Path("id") int authId);

    @DELETE("/api/food/{id}")
    Call<ResponseBody> deleteFood(@Path("id") int id);

    @GET("api/food/{id}")
    Call<FoodDto> getFoodById(@Path("id") int id);
    // Gọi món ăn theo loại
    @GET("api/food/type/{typeFoodId}")
    Call<List<FoodItemDto>> getFoodByType(@Path("typeFoodId") int typeFoodId);
}
