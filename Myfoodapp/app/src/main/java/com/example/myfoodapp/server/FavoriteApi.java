package com.example.myfoodapp.server;

import com.example.myfoodapp.model.Favorites;
import com.example.myfoodapp.model.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FavoriteApi {
    @GET("/getFavorite/")
    Call<List<Favorites>> getAllRecipes(@Query("id") int id);
    @POST("/addFavorite")
    Call <Favorites> addFavorite(@Body Favorites favorites);
    @POST("/Favorite/")
    Call<Favorites> deleteFavorite(@Body Favorites favorites);
}
