package org.example.database_btl.Backend.model.menuArea.Menu;

import javafx.scene.image.Image;

public class Product {
    public String name;
    public int price;
    public int quantity;

    public Image image;


    public Product(String name, int price, int quantity, Image image) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

}
