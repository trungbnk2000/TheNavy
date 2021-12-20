package com.example.thenavynd.Models;

public class Categories {
    int id;
    String name;
    String image;

    public Categories(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Categories(){

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
