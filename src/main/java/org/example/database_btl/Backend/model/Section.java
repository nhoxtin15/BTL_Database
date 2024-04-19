package org.example.database_btl.Backend.model;

import java.util.ArrayList;

public class Section {
    public ArrayList<Product> products;
    public String name;
    public int id;



    public Section(String name, int id) {
        this.name = name;
        this.id = id;
        this.products = new ArrayList<>();
    }



}
