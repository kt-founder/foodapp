package com.example.myfoodapp.model;

import com.google.gson.annotations.SerializedName;

public class FavoritesDto {

    @SerializedName("id")
    private int id;

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("foodId")
    private int foodId;

    @SerializedName("foodName")
    private String foodName;

    @SerializedName("foodImageBase64")
    private String foodImageBase64;

    // Constructor không tham số
    public FavoritesDto() {}

    // Constructor với tất cả các trường
    public FavoritesDto(int id, int idUser, int foodId, String foodName, String foodImageBase64) {
        this.id = id;
        this.idUser = idUser;
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodImageBase64 = foodImageBase64;
    }

    // Getter và Setter cho các trường

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImageBase64() {
        return foodImageBase64;
    }

    public void setFoodImageBase64(String foodImageBase64) {
        this.foodImageBase64 = foodImageBase64;
    }
}
