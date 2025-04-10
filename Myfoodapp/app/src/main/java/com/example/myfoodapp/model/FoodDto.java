package com.example.myfoodapp.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class FoodDto implements Serializable {
    private int id;
    private String name;
    private String detail;
    private String time;
    private String video;
    private String guide;
    private String ingredient;
    private String nutrition;
    private String imageBase64;  // Base64 image string
    private int idAut;
    private Set<TypeFood> typefoods;

    // Getters and setters


    public void setIdAut(int idAut) {
        this.idAut = idAut;
    }

    public Set<TypeFood> getTypefoods() {
        return typefoods;
    }

    public void setTypefoods(Set<TypeFood> typefoods) {
        this.typefoods = typefoods;
    }

    public int getIdAut() {
        return idAut;
    }

    public void setIdAuth(int idAuth) {
        this.idAut = idAuth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
    public Food toFood() {
        Food food = new Food();
        food.setId(id);
        food.setName(name);
        food.setDetail(detail);
        food.setIngredient(ingredient);
        food.setGuide(guide);
        food.setNutrition(nutrition);
        food.setTime(time);
        food.setVideo(video);
        food.setImage(imageBase64);;
        food.setTypefoods(typefoods);
        return food;
    }
}
