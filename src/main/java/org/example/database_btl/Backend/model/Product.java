package org.example.database_btl.Backend.model;

import javafx.scene.image.Image;

public class Product {
    private String name;
    private int price;
    private int quantity;

    private Image image;

    public Product(String name, int price, int quantity, Image image) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

}
