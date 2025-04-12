package com.genuinecoder.springserver.dto;

public class StatisticResponseDto {

    private long totalUsers;
    private long totalFoods;
    private long totalFavorites;
    private long totalRecipesUploaded;
    private String mostFavoriteFood; // Tên món ăn yêu thích nhất
    private long mostFavoriteFoodCount; // Số lượt yêu thích món ăn yêu thích nhất
    private long totalTypeFoods;

    // Getters and setters
    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalFoods() {
        return totalFoods;
    }

    public void setTotalFoods(long totalFoods) {
        this.totalFoods = totalFoods;
    }

    public long getTotalFavorites() {
        return totalFavorites;
    }

    public void setTotalFavorites(long totalFavorites) {
        this.totalFavorites = totalFavorites;
    }

    public long getTotalRecipesUploaded() {
        return totalRecipesUploaded;
    }

    public void setTotalRecipesUploaded(long totalRecipesUploaded) {
        this.totalRecipesUploaded = totalRecipesUploaded;
    }

    public String getMostFavoriteFood() {
        return mostFavoriteFood;
    }

    public void setMostFavoriteFood(String mostFavoriteFood) {
        this.mostFavoriteFood = mostFavoriteFood;
    }

    public long getMostFavoriteFoodCount() {
        return mostFavoriteFoodCount;
    }

    public void setMostFavoriteFoodCount(long mostFavoriteFoodCount) {
        this.mostFavoriteFoodCount = mostFavoriteFoodCount;
    }

    public long getTotalTypeFoods() {
        return totalTypeFoods;
    }

    public void setTotalTypeFoods(long totalTypeFoods) {
        this.totalTypeFoods = totalTypeFoods;
    }
}
