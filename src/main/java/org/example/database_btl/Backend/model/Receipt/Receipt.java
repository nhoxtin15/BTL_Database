package org.example.database_btl.Backend.model.Receipt;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import org.example.database_btl.Backend.model.controller.ReceiptController;

import java.util.ArrayList;

/**
 * Description: Store each Receipt$
 * Author: nhoxtin15$
 * Date: 27/04/2024$
 */

public class Receipt {

    public String name;
    public ArrayList<ProductReceipt> productReceipts ;
    public Tab receiptContainer;
    public ReceiptController receiptController;



    public Receipt(String name){
        this.name = name;
        productReceipts = new ArrayList<>();
    }

    public void initReceipt(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
        try{
            receiptContainer = loader.load();
            receiptController = loader.getController();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addProduct(int ID, String name, int price){
        ProductReceipt productReceipt = new ProductReceipt(ID, name, price);
        productReceipts.add(productReceipt);
        this.receiptController.addProduct(productReceipt);
    }




}
