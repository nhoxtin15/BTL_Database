package database_btl.Employee.Receipt;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import database_btl.Employee.Receipt.Controller.ReceiptProductController;

import java.util.ArrayList;

/**
 * Description: Store the product in the receipt$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */

public class ProductReceipt {
    public int ID;
    public int quantity;
    public String name;
    public int price;

    public String tableName;

    public AnchorPane productReceiptContainer;
    public ReceiptProductController productReceiptController;


    public ProductReceipt(int ID, String name, int price, ArrayList<String> tables){
        this.ID = ID;
        this.name = name;
        this.price = price;
        quantity = 1;
        initReceiptProduct();

        productReceiptController.tables.getItems().addAll(tables);



        //listener
        productReceiptController.tables.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            tableName = newValue;
        });
    }


    public void initReceiptProduct(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceiptProduct.fxml"));
        try{
            productReceiptContainer = loader.load();
            productReceiptController = loader.getController();

            productReceiptController.iD.setText(String.valueOf(ID));
            productReceiptController.nameProduct.setText(name);
            productReceiptController.priceProduct.setText(String.valueOf(price));
            productReceiptController.quantity.setText(String.valueOf(quantity));
            productReceiptController.totalPriceProduct.setText(String.valueOf(price*quantity));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void increaseQuantity(int quantity){
        this.quantity+=quantity;
        productReceiptController.quantity.setText(String.valueOf(this.quantity));

    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
        productReceiptController.quantity.setText(String.valueOf(this.quantity));
    }
    public void setID(int ID){
        this.ID = ID;
        this.productReceiptController.iD.setText(String.valueOf(ID));
    }

    public boolean equals(ProductReceipt productReceipt){
        return this.name.equals(productReceipt.name);
    }
    public boolean equals(String name,int price){
        return this.name.equals(name) && this.price == price;
    }





}

