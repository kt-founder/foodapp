package com.example.myfoodapp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TypeFood implements Serializable {
    private int id ;

    private String name ;
    private byte[] img;
    private Set<Food> foods = new HashSet<>();

    public TypeFood() {
    }

    public TypeFood(int id, String name, byte[] img) {
        this.id = id;
        this.name = name;
        this.img = img;
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

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
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
                ", img=" + Arrays.toString(img) +
                ", foods=" + foods +
                '}';
    }
}
