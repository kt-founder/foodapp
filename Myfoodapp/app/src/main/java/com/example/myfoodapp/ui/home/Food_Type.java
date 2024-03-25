package com.example.myfoodapp.ui.home;

import java.io.Serializable;

public class Food_Type implements Serializable {
    private int imag;
    private String name,time;

    public Food_Type(int imag, String name) {
        this.imag = imag;
        this.name = name;

    }

    public int getImag() {
        return imag;
    }

    public void setImag(int imag) {
        this.imag = imag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

