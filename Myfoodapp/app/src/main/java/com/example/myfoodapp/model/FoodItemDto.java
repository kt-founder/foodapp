package com.example.myfoodapp.model;

public class FoodItemDto {

    private final int id;
    private final String name;
    private final String imageBase64;

    public FoodItemDto(int id, String name, String imageBase64) {
        this.id = id;
        this.name = name;
        this.imageBase64 = imageBase64;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageBase64() {
        return imageBase64;
    }
}
