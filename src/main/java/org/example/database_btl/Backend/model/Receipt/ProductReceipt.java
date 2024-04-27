package org.example.database_btl.Backend.model.Receipt;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.example.database_btl.Backend.model.controller.ReceiptProductController;

/**
 * Description: Store the product in the receipt$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */
public class ProductReceipt{
    public int ID;
    public int quantity;
    public String name;
    public int price;

    public AnchorPane productReceiptContainer;
    public ReceiptProductController productReceiptController;


    public ProductReceipt(int ID, String name, int price){
        this.ID = ID;
        this.name = name;
        this.price = price;
        quantity = 0;
    }


    public void initReceiptProduct(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceiptProduct.fxml"));
        try{
            productReceiptContainer = loader.load();
            productReceiptController = loader.getController();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void increaseQuantity(){
        quantity++;
        productReceiptController.quantity.setText(String.valueOf(quantity));

    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
        productReceiptController.quantity.setText(String.valueOf(quantity));
    }





}

