package com.example.project1.Models;

public class DrinksCoffee {
    private int id;
    private String name;
    private String category;
    private int category_Id;
    private double price;
    private int price_Id;
    private boolean bestDrink;
    private String image_Url;
    private String description;
    private double star;

    private int numberInCart;


    //constructor
    public DrinksCoffee() {
    }
    //getter % setter

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(int category_Id) {
        this.category_Id = category_Id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPrice_Id() {
        return price_Id;
    }

    public void setPrice_Id(int price_Id) {
        this.price_Id = price_Id;
    }

    public boolean isBestDrink() {
        return bestDrink;
    }

    public void setBestDrink(boolean bestDrink) {
        this.bestDrink = bestDrink;
    }

    public String getImage_Url() {
        return image_Url;
    }

    public void setImage_Url(String image_Url) {
        this.image_Url = image_Url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }



    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

}
