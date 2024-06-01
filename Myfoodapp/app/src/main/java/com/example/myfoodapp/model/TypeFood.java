package com.example.myfoodapp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TypeFood implements Serializable {
    private int id ;

    private String name ;
    private String img;
    private Set<Food> foods = new HashSet<>();

    public TypeFood() {
    }

    public TypeFood(int id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
        //this.foods = foods;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Set<Food> getFoods() {
        return foods;
    }

    public void setFoods(Set<Food> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "TypeFood{" +
                "id=" + id +
                ", name='" + name + '\'' +

                ", foods=" + foods +
                '}';
    }
}
