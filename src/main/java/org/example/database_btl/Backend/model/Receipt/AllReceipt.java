package org.example.database_btl.Backend.model.Receipt;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.example.database_btl.Backend.model.controller.AllReceiptController;

import java.util.ArrayList;

public class AllReceipt {
    public ArrayList<Receipt> allReceipts;

    public VBox allReceiptContainer;
    public AllReceiptController allReceiptController;

    AllReceipt(){
        allReceipts = new ArrayList<>();
    }

    void initAllReceipt(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AllReceipt.fxml"));
        try{
            allReceiptContainer = loader.load();
            allReceiptController = loader.getController();

        }
        catch (Exception e){

        }
    }



    public static void addReceipt(Receipt receipt){

    }








}
