package org.example.database_btl.Backend.model;

import javafx.fxml.FXML;

public class Table extends Section{


    public Table(int id){
        super("Table",id);
    }


    public void addProduct(Product product){
        products.add(product);

    }


}
