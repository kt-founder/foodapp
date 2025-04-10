package com.example.myfoodapp.server;

import com.example.myfoodapp.model.Favorites;
import com.example.myfoodapp.model.FavoritesDto;
import com.example.myfoodapp.model.Food;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FavoriteApi {
    @GET("/api/favorites/user/{userId}")
    Call<List<FavoritesDto>> getFavoritesByUserId(@Path("userId") int userId);

    @POST("/api/favorites")
    Call<ResponseBody> addFavorite(@Body Favorites favorites);

    @DELETE("/api/favorites/{foodId}")
    Call<ResponseBody> deleteFavorite(@Path("foodId") int foodId);


}
