package com.genuinecoder.springserver.model.employee;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class TypeFood {
    @Id
    private int id ;

    private String name ;
    @Lob
    private byte[] img;
    
    @ManyToMany(mappedBy = "typefoods",cascade = CascadeType.ALL)
    @JsonIgnore
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
