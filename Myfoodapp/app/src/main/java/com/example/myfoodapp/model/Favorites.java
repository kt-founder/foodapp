package com.example.myfoodapp.model;

public class Favorites {
    private int id;
    private int idUser;
    private Food food;

    public Favorites() {
    }

    public Favorites(int id, int idUser, Food food) {
        this.id = id;
        this.idUser = idUser;
        this.food = food;
    }

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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
