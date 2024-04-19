package com.example.myfoodapp.model;

public class Food {
    private  int id;
    private String name;

    private int img;
    private String time;

    public Food(int img, String name, String time) {
        this.img = img;
        this.name = name;
        this.time = time;

    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
