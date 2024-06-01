package com.example.myfoodapp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Food implements Serializable {
    private int id;
    private String name;
    private String detail;

    private String image;


    private String time;
    private String video;
    private String ingredient;
    private String guide;
    private String nutrition;
    private int idAut;
    private Set<TypeFood> typefoods = new HashSet<>();
    public Food() {
        super();
    }

    public Food(int id, String name, String detail, String image, String time, String video, String ingredient, String guide, String nutrition, int idAut, Set<TypeFood> typefoods) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.image = image;
        this.time = time;
        this.video = video;
        this.ingredient = ingredient;
        this.guide = guide;
        this.nutrition = nutrition;
        this.idAut = idAut;
        this.typefoods = typefoods;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getIngredient(String nguyenLieu) {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getNutrition() {
        return nutrition;
    }

    public Set<TypeFood> getTypefoods() {
        return typefoods;
    }

    public void setTypefoods(Set<TypeFood> typefoods) {
        this.typefoods = typefoods;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }
    public String getDetail() {
        return detail;
    }



    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getIdAut() {
        return idAut;
    }

    public void setIdAut(int idAut) {
        this.idAut = idAut;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", image='" + image + '\'' +
                ", time='" + time + '\'' +
                ", video='" + video + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", guide='" + guide + '\'' +
                ", nutrition='" + nutrition + '\'' +
                ", idAut=" + idAut +
                ", typefoods=" + typefoods +
                '}';
    }
}
