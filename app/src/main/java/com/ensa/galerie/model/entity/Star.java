package com.ensa.galerie.model.entity;

public class Star {
    private int id;
    private String name;
    private float rating;

    private String img;

    private static int cntr = 0;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static int getCntr() {
        return cntr;
    }

    public static void setCntr(int cntr) {
        Star.cntr = cntr;
    }

    public Star(String name, String img, float rating){
        this.id = ++cntr;
        this.name = name;
        this.img = img;
        this.rating = rating;

    }

    public int getId() {
        return id;
    }
}
