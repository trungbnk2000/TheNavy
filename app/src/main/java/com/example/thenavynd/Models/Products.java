package com.example.thenavynd.Models;

public class Products {
    private int id;
    private int categoryId;
    private int image;
    private String name;
    private String price;
    private String moTa;
    private int soLuong;

    public Products(int id, int categoryId, int image, String name, String price, String moTa, int soLuong) {
        this.id = id;
        this.categoryId = categoryId;
        this.image = image;
        this.name = name;
        this.price = price;
        this.moTa = moTa;
        this.soLuong = soLuong;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
